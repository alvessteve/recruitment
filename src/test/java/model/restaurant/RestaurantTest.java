package model.restaurant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class RestaurantTest {

    @Test
    void isVegetarian_whenAllMealsVegetarian_thenTrue() {
        // Given
        Restaurant restaurant = RestaurantFactory.createVegetarianRestaurant("The restaurant");

        // When
        Restaurant.Type restaurantType = restaurant.getType();

        // Then
        assertThat(restaurantType).isEqualTo(Restaurant.Type.VEGETARIAN);
    }

    @Test
    void isVegetarian_whenSomeMealsNotVegetarian_thenFalse() {
        // Given
        Restaurant restaurant = RestaurantFactory.createNonVegetarianRestaurant("The restaurant");

        // When
        Restaurant.Type restaurantType = restaurant.getType();

        // Then
        assertThat(restaurantType).isNotEqualTo(Restaurant.Type.VEGETARIAN);
        assertThat(restaurantType).isEqualTo(Restaurant.Type.OTHER);
    }
}