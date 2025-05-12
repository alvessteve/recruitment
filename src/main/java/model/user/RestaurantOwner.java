package model.user;


import lombok.Getter;
import model.restaurant.Meal;
import model.restaurant.MealPriceEvolution;
import model.restaurant.Restaurant;

import java.util.List;

public class RestaurantOwner implements User
{
    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    private final Restaurant restaurant;

    public RestaurantOwner(String firstName, String lastName, Restaurant restaurant)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.restaurant = restaurant;
    }

    public void addMeal(String mealName, Double price, Meal.Type mealType)
    {
        restaurant.addMeal(mealName, price, mealType);
    }

    public void changeMealPrice(String mealName, Double newPrice) {
        restaurant.changePriceOfMeal(mealName, newPrice);
    }

    public List<MealPriceEvolution> getPriceEvolutionOfMeal(String mealName) {
        return restaurant.getPriceEvolutionOfMeal(mealName);
    }
}