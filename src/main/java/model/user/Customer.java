package model.user;

import lombok.Getter;
import model.command.OrderCommand;
import model.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Customer implements User
{
    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    private final Type type;

    @Getter
    private final List<Order> orders;

    public Customer(String firstName, String lastName,Type type)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.orders = new ArrayList<>();
    }

    public void makeOrder(OrderCommand orderCommand)
    {
        orders.add(new Order(orderCommand.restaurant(), this, orderCommand.meals()));
    }

    public void makeOrders(List<OrderCommand> orderCommands) {
        orderCommands.stream()
                .map(orderCommand -> new Order(orderCommand.restaurant(), this, orderCommand.meals()))
                .forEach(orders::add);
    }

    public List<Restaurant> findRestaurantsOfType(List<Restaurant> restaurants, Restaurant.Type type)
    {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getType() == type)
                .toList();
    }

    public enum Type {
        CHILD,
        STUDENT,
        OTHER
    }
}
