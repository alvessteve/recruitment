package model.user;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import model.command.OrderCommand;
import model.restaurant.Meal;
import model.restaurant.RestaurantFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.restaurant.Restaurant;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static model.user.Customer.Type.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class OrderTest
{
    private static Restaurant restaurant;

    private static String firstMealName;
    private String secondMealName;

    private static final double CHILD_DISCOUNT = 0.5;
    private static final double STUDENT_DISCOUNT = 0.75;

    @BeforeEach
    void setUp() {
        restaurant = RestaurantFactory.createVegetarianRestaurantWith2Meals("The restaurant");
        firstMealName = restaurant.getMeals().get(0).getName();
        secondMealName = restaurant.getMeals().get(1).getName();
    }

    @Test
    void getPrice_whenChild_when1stOrder_then50PercentDiscount()
    {
        // Given
        Customer customer = new Customer("Ba", "Bar", CHILD);
        customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName, secondMealName)));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double totalPrice = totalPriceOfMeals(order);
        assertThat(totalPrice * CHILD_DISCOUNT).isEqualTo(order.getPrice());
    }

    @Test
    void getPrice_whenStudent_then25PercentDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", Customer.Type.STUDENT);
        customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName, secondMealName)));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double totalPrice = totalPriceOfMeals(order);
        assertThat(totalPrice * STUDENT_DISCOUNT).isEqualTo(order.getPrice());
    }

    @Test
    void getPrice_whenAdult_thenNoDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", Customer.Type.OTHER);
        customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName, secondMealName)));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double totalPrice = totalPriceOfMeals(order);
        assertThat(totalPrice).isEqualTo(order.getPrice());
    }

    @Test
    void getPrice_whenChild_when5thOrder_then10PercentDiscount_above50PercentDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", CHILD);
        IntStream.range(0, 5)
                .forEach(i -> customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName))));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double expectedPrice = 4.0;
        assertThat(expectedPrice).isEqualTo(order.getPrice());
    }

    @Test
    void getPrice_whenStudent_when5thOrder_then10PercentDiscount_above25PercentDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", STUDENT);
        IntStream.range(0, 5)
                .forEach(i -> customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName))));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double discountedMeal = 6.5;
        assertThat(discountedMeal).isEqualTo(order.getPrice());
    }

    @Test
    void getPrice_whenAdult_when5thOrder_then10PercentDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", OTHER);
        IntStream.range(0, 5)
                .forEach(i -> customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName))));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double expectedPrice = 9.0;
        assertThat(expectedPrice).isEqualTo(order.getPrice());
    }

    @Test
    void getPrice_whenChild_when10thOrder_then15PercentDiscount_above50PercentDiscountAnd10PercentDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", CHILD);
        IntStream.range(0, 10)
                .forEach(i -> customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName))));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double expectedPrice = 2.5;
        assertThat(expectedPrice).isEqualTo(order.getPrice());
    }

    @Test
    void getPrice_whenStudent_when10thOrder_then15PercentDiscount_above25PercentDiscountAnd10PercentDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", STUDENT);
        IntStream.range(0, 10)
                .forEach(i -> customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName))));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double expectedPrice = 5.0;
        assertThat(expectedPrice).isEqualTo(order.getPrice());
    }

    @Test
    void getPrice_whenAdult_when10thOrder_then15PercentDiscount_Above10PercentDiscount() {
        // Given
        Customer customer = new Customer("Ba", "Bar", OTHER);
        IntStream.range(0, 10)
                .forEach(i -> customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName))));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        double expectedPrice = 7.5;
        assertThat(expectedPrice).isEqualTo(order.getPrice());
    }

    @ParameterizedTest
    @MethodSource("provideEachCustomerType")
    void getPrice_whateverCustomer_whenOrderedWithinTheWeek_thenSecondMealFree(Customer customer, Double expectedPrice) {
        // Given
        customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName)));
        customer.makeOrder(new OrderCommand(restaurant, List.of(firstMealName, secondMealName)));

        // When
        Order order = customer.getOrders().get(0);

        // Then
        assertThat(order.getPrice()).isEqualTo(expectedPrice);
    }

    private static Stream<Arguments> provideEachCustomerType() {
        return Stream.of(
                of(new Customer("Ba", "Bar", Customer.Type.CHILD), restaurant.getMealByName(firstMealName).getPrice() * CHILD_DISCOUNT),
                of(new Customer("Ba", "Bar", Customer.Type.STUDENT), restaurant.getMealByName(firstMealName).getPrice() * STUDENT_DISCOUNT),
                of(new Customer("Ba", "Bar", Customer.Type.OTHER), restaurant.getMealByName(firstMealName).getPrice())
        );
    }

    private double totalPriceOfMeals(Order order) {
        return order.getMeals().stream().mapToDouble(Meal::getPrice).sum();
    }
}