package model.restaurant;

import java.util.Objects;

import lombok.Getter;
import model.Entity;

public class Meal implements Entity
{
    @Getter
    private final Restaurant restaurant;

    @Getter
    private final String name;

    @Getter
    private final Double price;

    private final Type type;

    Meal(Restaurant restaurant, String name, Double price, Type type)
    {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Boolean isVegetarian()
    {
        return type == Type.VEGETARIAN;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Meal meal = (Meal) o;
        return Objects.equals(name, meal.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    public enum Type {
        VEGETARIAN,
        OTHER
    }
}