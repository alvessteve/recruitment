package assertions;

import model.command.OrderCommand;
import model.restaurant.Meal;
import model.restaurant.Restaurant;
import model.user.Customer;
import org.assertj.core.api.AbstractAssert;

public class CustomerAssert extends AbstractAssert<CustomerAssert, Customer> {

    public CustomerAssert(Customer actual) {
        super(actual, CustomerAssert.class);
    }

    public static CustomerAssert assertThat(Customer actual) {
        return new CustomerAssert(actual);
    }

    public void hasMadeOrder(OrderCommand orderCommand) {
        isNotNull();
        if (actual.getOrders().isEmpty()) {
            failWithMessage("Expected customer to have made a command, but no command was made");
        }
        boolean hasReceivedCommand = actual.getOrders().stream()
                .anyMatch(order -> order.getMeals().stream().map(Meal::getName).allMatch(orderCommand.meals()::contains));
        if (!hasReceivedCommand) {
            failWithMessage("Expected customer to have made a command %s, but it didn't", orderCommand);
        }
    }

}
