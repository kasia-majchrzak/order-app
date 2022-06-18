package com.example.orderapp.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @NotEmpty
    private String Name;

    private String Description;

    @DecimalMin("0.00")
    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal Price;

    @OneToMany
    private List<PizzaComponent> Components;

    public Pizza(@NotEmpty @NotNull String name,
                 String description,
                 @DecimalMin("0.00") @NotNull @Digits(integer = 10, fraction = 2) BigDecimal price) {
        this.Name = Objects.requireNonNull(name);
        this.Description = description;
        this.Price = Objects.requireNonNull(price);
    }

    public Pizza() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public List<PizzaComponent> getComponents() {
        return Components;
    }

    public void setComponents(List<PizzaComponent> components) {
        Components = components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;
        Pizza pizza = (Pizza) o;
        return Objects.equals(Name, pizza.Name) && Objects.equals(Description, pizza.Description) && Objects.equals(Price, pizza.Price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, Name, Description, Price);
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", Price=" + Price +
                '}';
    }
}
