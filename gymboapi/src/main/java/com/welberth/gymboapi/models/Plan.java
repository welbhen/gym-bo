package com.welberth.gymboapi.models;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = Plan.TABLE_NAME)
public class Plan {
    public interface CreatePlan { }

    public interface UpdatePlan { }

    public interface DeletePlan { }

    public static final String TABLE_NAME = "plan";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "title", length = 50, nullable = false, unique = true)
    @NotNull(groups = CreatePlan.class)
    @NotEmpty(groups = CreatePlan.class)
    @Size(groups = CreatePlan.class, min = 2, max = 50)
    private String title;

    @Column(name = "description", length = 300, nullable = false, unique = true)
    @NotNull(groups = CreatePlan.class)
    @NotEmpty(groups = CreatePlan.class)
    @Size(groups = CreatePlan.class, min = 2, max = 300)
    private String description;

    @Column(name = "monthly_price", nullable = false)
    @NotNull(groups = {CreatePlan.class, UpdatePlan.class})
    @NotEmpty(groups = {CreatePlan.class, UpdatePlan.class})
    @Size(groups = {CreatePlan.class, UpdatePlan.class}, min = 1)
    private Float monthlyPrice;

    @OneToMany(mappedBy = "plan") // User class property that maps which User has a said Plan
    private List<User> users = new ArrayList<User>();

    public Plan() { }

    public Plan(Long id, String title, String description, Float monthlyPrice) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.monthlyPrice = monthlyPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Float monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null) return false;

        if (!(obj instanceof Plan)) return false;

        Plan other = (Plan) obj;

        if (this.id == null) {
            if (other.id != null) return false;
        }

        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.title, other.title) &&
                Objects.equals(this.description, other.description) &&
                Objects.equals(this.monthlyPrice, other.monthlyPrice);
    }
}
