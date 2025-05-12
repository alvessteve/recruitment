package model.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import model.Entity;

import static java.time.LocalDate.now;

public class Meal implements Entity
{
    @Getter
    private final Restaurant restaurant;

    @Getter
    private final String name;

    @Getter
    private Double price;

    @Getter
    private final List<MealPriceEvolution> priceEvolutions;

    private final Type type;

    Meal(Restaurant restaurant, String name, Double price, Type type)
    {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
        this.type = type;
        this.priceEvolutions = new ArrayList<>();
    }

    public Boolean isVegetarian()
    {
        return type == Type.VEGETARIAN;
    }

    public void changePrice(Double newPrice) {
        if (newPrice < 0)
            throw new IncorrectPriceException("Price cannot be negative");
        if (newPrice.equals(price))
            throw new IncorrectPriceException("Price is the same as before");

        addPriceEvolutionEvent(this.price, newPrice);
        this.price = newPrice;
    }

    private void addPriceEvolutionEvent(Double oldPrice, Double newPrice) {
        priceEvolutions.add(new MealPriceEvolution(now(), oldPrice, newPrice));
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