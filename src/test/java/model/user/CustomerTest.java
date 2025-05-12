package model.user;

import model.restaurant.Restaurant;
import model.restaurant.RestaurantFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void findRestaurants_OfTypeVegetarian_ifOne_thenReturnIt() {
        //Given
        Customer customer = new Customer("John", "Doe", Customer.Type.STUDENT);
        Restaurant vegetarianRestaurant = RestaurantFactory.createVegetarianRestaurant("The restaurant");
        Restaurant nonVegetarianRestaurant = RestaurantFactory.createNonVegetarianRestaurant("The restaurant 2");

        //When
        List<Restaurant> vegetarianRestaurants = customer.findRestaurantsOfType(List.of(vegetarianRestaurant, nonVegetarianRestaurant), Restaurant.Type.VEGETARIAN);

        //Then
        assertThat(vegetarianRestaurants).containsExactly(vegetarianRestaurant);
    }

    @Test
    void findRestaurants_OfTypeVegetarian_ifNone_thenReturnNone() {
        //Given
        Customer customer = new Customer("John", "Doe", Customer.Type.STUDENT);
        Restaurant nonVegetarianRestaurant = RestaurantFactory.createNonVegetarianRestaurant("The Big Fernand");
        Restaurant anotherNonVegetarianRestaurant = RestaurantFactory.createNonVegetarianRestaurant("The restaurant");

        //When
        List<Restaurant> vegetarianRestaurants = customer.findRestaurantsOfType(List.of(anotherNonVegetarianRestaurant, nonVegetarianRestaurant), Restaurant.Type.VEGETARIAN);

        //Then
        assertThat(vegetarianRestaurants).isEmpty();
    }
}