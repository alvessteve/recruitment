package model.restaurant;

public class RestaurantFactory {

    public static Restaurant createVegetarianRestaurantWith2Meals(String name) {
        Restaurant restaurant = new Restaurant(name);
        restaurant.addMeal("Vegetable Salad", 10.0, Meal.Type.VEGETARIAN);
        restaurant.addMeal("Vegetable steak", 30.0, Meal.Type.VEGETARIAN);
        return restaurant;
    }

    public static Restaurant createNonVegetarianRestaurantWith2Meals(String name) {
        Restaurant restaurant = new Restaurant(name);
        restaurant.addMeal("Chicken Salad", 15.0, Meal.Type.OTHER);
        restaurant.addMeal("Vegetable Salad", 10.0, Meal.Type.VEGETARIAN);
        return restaurant;
    }
}
