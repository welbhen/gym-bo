package com.welberth.gymboapi.services;

import com.welberth.gymboapi.exceptions.ApiException;
import com.welberth.gymboapi.models.Plan;
import com.welberth.gymboapi.models.User;
import com.welberth.gymboapi.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Contains business logic and orchestrates the flow of data between the Presentation
 * and Data Access layer (UserRepository) for the User model.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanService planService;

    /**
     * Finds a user on the database by its id.
     *
     * @param id for the user
     * @return user corresponding to the id passed
     * @throws ApiException when user is not found
     */
    public User findById(Long id) throws ApiException {
        Optional<User> user = this.userRepository.findById(id); // Optional -> means that if no User is found, it returns and empty obj instead of null

        return user.orElseThrow(() -> new ApiException("Could not find a User with id = " + id));
    }

    /**
     * Finds a user on the database by its username.
     *
     * @param username for the user
     * @return user corresponding to the id passed
     * @throws ApiException when user is not found
     */
    public User findByUsername(String username) throws ApiException {
        Optional<User> user = this.userRepository.findByUsername(username);

        return user.orElseThrow(() -> new ApiException("Could not find a User with username = " + username));
    }

    /**
     * Finds a list of Users subscribed to a Plan.
     *
     * @param planId plan id
     * @return list of users that are subscribe to the Plan
     */
    public List<User> findByPlanId(Long planId) {
        return this.userRepository.findByPlan_Id(planId);
    }

    /**
     * Creates a new user on the database.
     *
     * @param newUser new user to be created
     * @return created user
     */
    @Transactional // Useful for persistence of data (create, update).
    public User createUser(User newUser) {
        newUser.setId(null); // make sure nobody tries to update a user with this method
        newUser = this.userRepository.save(newUser);

        return newUser;
    }

    /**
     * Updates a user on the database.
     *
     * @param user with updates
     * @return updated user
     * @throws ApiException if user doesn't exist
     */
    @Transactional
    public User updateUser(User user) throws ApiException {
        User updatedUser = findById(user.getId());

        updatedUser.setPassword(user.getPassword());
        updatedUser.setEmail(user.getEmail());

        return this.userRepository.save(updatedUser);
    }

    /**
     * Deletes a user on the database by its id.
     *
     * @param id of the user to be deleted.
     * @throws ApiException if user doesn't exist or user has related entities on the database
     */
    public void deleteUser(Long id) throws ApiException {
        findById(id); // makes sure the user exists

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApiException("The user with id = " + id + " could not be deleted.");
        }
    }

    /**
     * Finds the Plan that a User is subscribed to.
     *
     * @param userId user id
     * @return the plan this user is subscribed to
     * @throws ApiException if user is not subscribed to any plan
     */
    public Plan findPlan(Long userId) throws ApiException {
        User user = findById(userId);

        try {
            return user.getPlan();
        } catch (Exception e) {
            throw new ApiException("No Plan subscription found for the user " + user.getUsername() + ".");
        }
    }

    /**
     * Checks if the User is up-to-date with Plan Subscription payment.
     *
     * @param userId user id
     * @return true if user is up-to-date with payment, false otherwise
     */
    public boolean isPaymentUpToDate(Long userId) {
        findPlan(userId); // Checks if user has an Active plan, if not an exception will be thrown
        User user = findById(userId);

        LocalDate paidUntil = user.getPaidUntil();
        LocalDate today = LocalDate.now();

        return paidUntil.isAfter(today);
    }

    /**
     * Subscribes a User to a Plan.
     *
     * @param userId id for the user
     * @param planId the plan this user wants to subscribe to
     * @param paidUntil until what date this user is subscribed to this plan
     */
    public void subscribeToPlan(Long userId, Long planId, LocalDate paidUntil) {
        User user = findById(userId);
        Plan plan = this.planService.findById(planId);

        user.setPlan(plan);
        user.setPaidUntil(paidUntil);

        updateUser(user);
    }

    /**
     * Unsubscribes a User to a Plan.
     *
     * @param userId id for the user
     */
    public void unsubscribeToPlan(Long userId) {
        User user = findById(userId);

        user.setPlan(null);
        user.setPaidUntil(null);

        updateUser(user);
    }
}
