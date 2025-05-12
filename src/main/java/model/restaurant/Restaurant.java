package model.restaurant;

import lombok.Getter;
import model.Entity;
import model.user.Order;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Restaurant implements Entity
{
    @Getter
    private final String name;

    @Getter
    private final List<Meal> meals;

    @Getter
    private final List<Order> orders;

    @Getter
    private Type type;

    public Restaurant(String name)
    {
        this.name = name;
        this.meals = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.type = Type.OTHER;
    }

    public void addMeal(String mealName, Double price, Meal.Type mealType)
    {
        boolean mealAlreadyExists = meals.stream().map(Meal::getName).anyMatch(n -> n.equals(name));
        if (mealAlreadyExists)
            throw new IllegalArgumentException(format("Meal %s already exists in %s", mealName, name));

        meals.add(new Meal(this, mealName, price, mealType));
        updateRestaurantTypeAccordingMealType();
    }

    private void updateRestaurantTypeAccordingMealType() {
        boolean areAllMealsVegetarian = meals.stream().allMatch(Meal::isVegetarian);
        if (areAllMealsVegetarian)
            type = Type.VEGETARIAN;
        else
            type = Type.OTHER;
    }

    public Restaurant withReceivedOrder(Order order)
    {
        orders.add(order);
        return this;
    }

    public Meal getMealByName(String mealName)
    {
        return meals.stream()
                    .filter(meal -> meal.getName().equals(mealName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(format("No meal named %s in %s", mealName, name)));
    }

    public void changePriceOfMeal(String mealName, Double newPrice) {
        this.getMealByName(mealName).changePrice(newPrice);
    }

    public List<MealPriceEvolution> getPriceEvolutionOfMeal(String mealName) {
        return this.getMealByName(mealName).getPriceEvolutions();
    }

    public enum Type
    {
        VEGETARIAN,
        OTHER
    }
}
