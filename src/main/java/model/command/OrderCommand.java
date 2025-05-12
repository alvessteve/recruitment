package model.command;

import model.restaurant.Restaurant;

import java.util.List;

public record OrderCommand(Restaurant restaurant, List<String> meals) {
    public OrderCommand {
        if(restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        if(meals == null || meals.isEmpty()) {
            throw new IllegalArgumentException("Meals cannot be null or empty");
        }
    }
}
