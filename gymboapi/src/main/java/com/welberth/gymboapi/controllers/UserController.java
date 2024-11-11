package com.welberth.gymboapi.controllers;

import com.welberth.gymboapi.exceptions.ApiException;
import com.welberth.gymboapi.models.Plan;
import com.welberth.gymboapi.models.User;
import com.welberth.gymboapi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired // DI - could also be done on the constructor of UserController class
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = this.userService.findById(id);
            return ResponseEntity.ok().body(user);
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = this.userService.findByUsername(username);
            return ResponseEntity.ok().body(user);
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/list/{planId}")
    public ResponseEntity<List<User>> getUsersByPlanId(@PathVariable Long planId) {
        try {
            List<User> users = this.userService.findByPlanId(planId);
            return ResponseEntity.ok().body(users);
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/plan/{id}")
    public ResponseEntity<Plan> getActivePlanById(@PathVariable Long id) {
        try {
            Plan plan = this.userService.findPlan(id);
            return ResponseEntity.ok().body(plan);
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<Boolean> getPaymentStatus(@PathVariable Long id) {
        try {
            boolean status = this.userService.isPaymentUpToDate(id);
            return ResponseEntity.ok().body(status);
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @Validated(User.CreateUser.class)
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
        try {
            user = this.userService.createUser(user);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).build(); // status 201
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody User user, @PathVariable Long id) {
        try {
            user.setId(id);
            user = this.userService.updateUser(user);
            return ResponseEntity.noContent().build();
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/plan/subscribe/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> subscribeToPlan(@Valid @RequestBody User user, @PathVariable Long id) {
        try {
            user.setId(id);
            this.userService.subscribeToPlan(user.getId(), user.getPlan().getId(), user.getPaidUntil());
            return ResponseEntity.noContent().build();
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/plan/unsubscribe/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> unsubscribeToPlan(@PathVariable Long id) {
        try {
            this.userService.unsubscribeToPlan(id);
            return ResponseEntity.noContent().build();
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    @Validated(User.DeleteUser.class)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            this.userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
