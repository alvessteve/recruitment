package model.user;

import model.command.OrderCommand;
import model.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static assertions.CustomerAssert.assertThat;
import static assertions.RestaurantAssert.assertThat;
import static model.restaurant.RestaurantFactory.createNonVegetarianRestaurantWith2Meals;
import static model.restaurant.RestaurantFactory.createVegetarianRestaurantWith2Meals;
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
        Restaurant vegetarianRestaurant = createVegetarianRestaurantWith2Meals("The restaurant");
        Restaurant nonVegetarianRestaurant = createNonVegetarianRestaurantWith2Meals("The restaurant 2");

        //When
        List<Restaurant> vegetarianRestaurants = customer.findRestaurantsOfType(List.of(vegetarianRestaurant, nonVegetarianRestaurant), Restaurant.Type.VEGETARIAN);

        //Then
        assertThat(vegetarianRestaurants).containsExactly(vegetarianRestaurant);
    }

    @Test
    void findRestaurants_OfTypeVegetarian_ifNone_thenReturnNone() {
        //Given
        Restaurant nonVegetarianRestaurant = createNonVegetarianRestaurantWith2Meals("The Big Fernand");
        Restaurant anotherNonVegetarianRestaurant = createNonVegetarianRestaurantWith2Meals("The restaurant");

        //When
        List<Restaurant> vegetarianRestaurants = customer.findRestaurantsOfType(List.of(anotherNonVegetarianRestaurant, nonVegetarianRestaurant), Restaurant.Type.VEGETARIAN);

        //Then
        assertThat(vegetarianRestaurants).isEmpty();
    }

    @Test
    void makeOrders_withCommands_ThenMakeOrders() {
        // Given
        Restaurant vegetarianRestaurant = createVegetarianRestaurantWith2Meals("The restaurant");
        Restaurant meatRestaurant = createNonVegetarianRestaurantWith2Meals("Au bovin");

        OrderCommand orderCommandForVegetarianRestaurant = new OrderCommand(vegetarianRestaurant,
                List.of(vegetarianRestaurant.getMeals().get(0).getName(), vegetarianRestaurant.getMeals().get(1).getName()));
        OrderCommand orderCommandForMeatRestaurant = new OrderCommand(meatRestaurant, List.of(meatRestaurant.getMeals().get(0).getName()));
        List<OrderCommand> orderCommands = List.of(
                orderCommandForVegetarianRestaurant,
                orderCommandForMeatRestaurant
        );

        // When
        customer.makeOrders(orderCommands);

        // Then
        assertThat(customer).hasMadeOrder(orderCommandForVegetarianRestaurant);
        assertThat(customer).hasMadeOrder(orderCommandForMeatRestaurant);
        assertThat(vegetarianRestaurant).hasReceivedCommand(orderCommandForVegetarianRestaurant);
        assertThat(meatRestaurant).hasReceivedCommand(orderCommandForMeatRestaurant);
    }

    @Test
    void makeOrder_withRestaurantAndMeals_ThenMakeOrder() {
        // Given
        Restaurant vegetarianRestaurant = createVegetarianRestaurantWith2Meals("The restaurant");
        List<String> meals = List.of(vegetarianRestaurant.getMeals().get(0).getName(), vegetarianRestaurant.getMeals().get(1).getName());

        // When
        OrderCommand orderCommand = new OrderCommand(vegetarianRestaurant, meals);
        customer.makeOrder(orderCommand);

        // Then
        assertThat(customer).hasMadeOrder(orderCommand);
        assertThat(vegetarianRestaurant).hasReceivedCommand(orderCommand);
    }

}