package assertions;

import model.command.OrderCommand;
import model.restaurant.Meal;
import model.restaurant.Restaurant;
import org.assertj.core.api.AbstractAssert;

public class RestaurantAssert extends AbstractAssert<RestaurantAssert, Restaurant> {

    public RestaurantAssert(Restaurant actual) {
        super(actual, RestaurantAssert.class);
    }

    public static RestaurantAssert assertThat(Restaurant actual) {
        return new RestaurantAssert(actual);
    }

    public void hasReceivedCommand(OrderCommand orderCommand) {
        isNotNull();
        if (actual.getOrders().isEmpty()) {
            failWithMessage("Expected restaurant to have received a command, but no command was received");
        }
        boolean hasReceivedCommand = actual.getOrders().stream()
                .anyMatch(restaurantOrder -> restaurantOrder.getMeals().stream().map(Meal::getName).allMatch(orderCommand.meals()::contains));
        if (!hasReceivedCommand) {
            failWithMessage("Expected restaurant to have received command %s, but it didn't", orderCommand);
        }
    }

}
