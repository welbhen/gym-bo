package com.welberth.gymboapi.services;

import com.welberth.gymboapi.exceptions.ApiException;
import com.welberth.gymboapi.models.Plan;
import com.welberth.gymboapi.repositories.PlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Contains business logic and orchestrates the flow of data between the Presentation
 * and Data Access layer (PlanRepository) for the Plan model.
 */
@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    /**
     * Finds a Plan on the database by its id.
     *
     * @param id for the plan
     * @return plan corresponding to the id passed
     * @throws ApiException when plan is not found
     */
    public Plan findById(Long id) throws ApiException {
        Optional<Plan> plan = this.planRepository.findById(id);

        return plan.orElseThrow(() -> new ApiException("Could not find a Plan with id = " + id));
    }

    /**
     * Finds a user on the database by its id.
     *
     * @param title for the plan
     * @return plan corresponding to the title passed
     * @throws ApiException when plan is not found
     */
    public Plan findByTitle(String title) throws ApiException {
        Optional<Plan> plan = this.planRepository.findByTitle(title);

        return plan.orElseThrow(() -> new ApiException("Could not find a Plan with title = " + title));
    }

    /**
     * Creates a new plan on the database.
     *
     * @param newPlan new plan to be created
     * @return created plan
     */
    @Transactional
    public Plan createPlan(Plan newPlan) {
        newPlan.setId(null);
        newPlan = this.planRepository.save(newPlan);

        return newPlan;
    }

    /**
     * Updates a plan on the database.
     *
     * @param plan with updates
     * @return updated plan
     * @throws ApiException if plan doesn't exist
     */
    @Transactional
    public Plan updatePlan(Plan plan) throws ApiException {
        Plan updatedPlan = findById(plan.getId());

        updatedPlan.setTitle(plan.getTitle());
        updatedPlan.setDescription(plan.getDescription());
        updatedPlan.setMonthlyPrice(plan.getMonthlyPrice());

        return this.planRepository.save(updatedPlan);
    }

    /**
     * Deletes a plan on the database by its id.
     *
     * @param id of the plan to be deleted.
     * @throws ApiException if plan doesn't exist or plan has related entities on the database
     */
    public void deletePlan(Long id) throws ApiException {
        findById(id);

        try {
            this.planRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApiException("The plan with id = " + id + " could not be deleted.");
        }
    }

}
