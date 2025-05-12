package model.user;

import model.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.restaurant.RestaurantFactory.createNonVegetarianRestaurant;
import static model.restaurant.RestaurantFactory.createVegetarianRestaurant;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("John", "Doe", Customer.Type.STUDENT);
    }

    @Test
    void findRestaurants_OfTypeVegetarian_ifOne_thenReturnIt() {
        //Given
        Restaurant vegetarianRestaurant = createVegetarianRestaurant("The restaurant");
        Restaurant nonVegetarianRestaurant = createNonVegetarianRestaurant("The restaurant 2");

        //When
        List<Restaurant> vegetarianRestaurants = customer.findRestaurantsOfType(List.of(vegetarianRestaurant, nonVegetarianRestaurant), Restaurant.Type.VEGETARIAN);

        //Then
        assertThat(vegetarianRestaurants).containsExactly(vegetarianRestaurant);
    }

    @Test
    void findRestaurants_OfTypeVegetarian_ifNone_thenReturnNone() {
        //Given
        Restaurant nonVegetarianRestaurant = createNonVegetarianRestaurant("The Big Fernand");
        Restaurant anotherNonVegetarianRestaurant = createNonVegetarianRestaurant("The restaurant");

        //When
        List<Restaurant> vegetarianRestaurants = customer.findRestaurantsOfType(List.of(anotherNonVegetarianRestaurant, nonVegetarianRestaurant), Restaurant.Type.VEGETARIAN);

        //Then
        assertThat(vegetarianRestaurants).isEmpty();
    }
}