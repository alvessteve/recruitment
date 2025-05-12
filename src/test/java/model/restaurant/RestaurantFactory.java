package model.restaurant;

public class RestaurantFactory {

    public static Restaurant createVegetarianRestaurant(String name) {
        Restaurant restaurant = new Restaurant(name);
        restaurant.addMeal("Vegetable Salad", 10.0, Meal.Type.VEGETARIAN);
        restaurant.addMeal("Vegetable steak", 30.0, Meal.Type.VEGETARIAN);
        return restaurant;
    }

    public static Restaurant createNonVegetarianRestaurant(String name) {
        Restaurant restaurant = new Restaurant(name);
        restaurant.addMeal("Chicken Salad", 15.0, Meal.Type.OTHER);
        restaurant.addMeal("Vegetable Salad", 10.0, Meal.Type.VEGETARIAN);
        return restaurant;
    }
}
