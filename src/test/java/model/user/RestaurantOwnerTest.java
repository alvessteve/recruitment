package model.user;

import model.restaurant.IncorrectPriceException;
import model.restaurant.Meal;
import model.restaurant.MealPriceEvolution;
import model.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RestaurantOwnerTest {

    private RestaurantOwner owner;
    private Restaurant restaurant;
    private String mealName;
    private double mealPrice;

    @BeforeEach
    void setUp() {
        // Given
        mealPrice = 10.0;
        mealName = "Pasta";
        restaurant = new Restaurant("Test Restaurant");
        owner = new RestaurantOwner("John", "Doe", restaurant);
        owner.addMeal(mealName, mealPrice, Meal.Type.OTHER);
    }

    @Test
    void changeMealPrice_whenUnknownMeal_ThenThrowError()  {
        // When
        String unknownMealName = "Unknown Meal";
        double newPrice = 12.0;

        // Then
        assertThatThrownBy(() -> owner.changeMealPrice(unknownMealName, newPrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No meal named Unknown Meal in Test Restaurant");
    }

    @Test
    void changeMealPrice_whenExistingMeal_thenChangePrice() {
        // When
        double newPrice = 12.0;
        owner.changeMealPrice(mealName, newPrice);

        // Then
        Meal pasta = restaurant.getMealByName(mealName);
        assertThat(pasta.getPrice()).isEqualTo(newPrice);
    }

    @Test
    void changeMealPrice_whenNegativePrice_thenThrowException() {
        // When
        double newPrice = -2.0;

        // Then
        assertThatThrownBy(() -> owner.changeMealPrice(mealName, newPrice))
                .isInstanceOf(IncorrectPriceException.class)
                .hasMessageContaining("Price cannot be negative");
    }

    @Test
    void changeMealPrice_whenSameAmount_thenNoChange() {
        // When
        double samePrice = mealPrice;

        // Then
        assertThatThrownBy(() -> owner.changeMealPrice(mealName, samePrice))
                .isInstanceOf(IncorrectPriceException.class)
                .hasMessageContaining("Price is the same as before");
    }

    @Test
    void getPriceEvolutionOfMeal_whenMealPriceChanged_thenReturnPriceEvolution() {
        // Given
        double newPrice = 12.0;
        owner.changeMealPrice(mealName, newPrice);

        // When
        List<MealPriceEvolution> priceEvolutionOfMeal = owner.getPriceEvolutionOfMeal(mealName);

        // Then
        assertThat(priceEvolutionOfMeal.size()).isEqualTo(1);
        assertThat(priceEvolutionOfMeal.get(0).oldPrice()).isEqualTo(mealPrice);
        assertThat(priceEvolutionOfMeal.get(0).newPrice()).isEqualTo(newPrice);
    }
}