package com.welberth.gymboapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser { }

    public interface UpdateUser { }

    public interface DeleteUser { }

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = {CreateUser.class, DeleteUser.class})
    @NotEmpty(groups = {CreateUser.class, DeleteUser.class})
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 50, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 6, max = 50)
    private String password;

    @Column(name = "email", length = 100, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 3, max = 100)
    private String email;

    @ManyToOne // Many Users to One Plan
    @JoinColumn(name = "active_plan_id", nullable = false)
    private Plan activePlan;

    @Column(name = "paid_until")
    private LocalDate paidUntil;

    public User() { }

    public User(Long id, String username, String password, String email, Plan activePlan, LocalDate paidUntil) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.activePlan = activePlan;
        this.paidUntil = paidUntil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Plan getActivePlan() {
        return activePlan;
    }

    public void setActivePlan(Plan activePlan) {
        this.activePlan = activePlan;
    }

    public LocalDate getPaidUntil() {
        return paidUntil;
    }

    public void setPaidUntil(LocalDate paidUntil) {
        this.paidUntil = paidUntil;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null) return false;

        if (!(obj instanceof User)) return false;

        User other = (User) obj;

        if (this.id == null) {
            if (other.id != null) return false;
        }

        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.username, other.username) &&
                Objects.equals(this.password, other.password) &&
                Objects.equals(this.email, other.email) &&
                Objects.equals(this.activePlan, other.activePlan) &&
                Objects.equals(this.paidUntil, other.paidUntil);
    }
}
