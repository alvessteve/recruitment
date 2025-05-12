import java.util.List;

import model.command.OrderCommand;
import model.restaurant.Meal;
import model.restaurant.Restaurant;
import model.user.Customer;
import model.user.RestaurantOwner;
import static model.user.Customer.Type.OTHER;

public class Main
{
    public static void main(String[] args)
    {
        // *******************
        // *** RESTAURANTS ***
        // *******************

        Restaurant ticino = new Restaurant("Le Ticino");
        Restaurant etoile = new Restaurant("L'étoile");
        Restaurant texan = new Restaurant("Le texan");

        // *************
        // *** USERS ***
        // *************

        RestaurantOwner robertDupont = new RestaurantOwner("Robert", "Dupont", ticino);
        RestaurantOwner magaliNoel = new RestaurantOwner("Magali", "Noel", etoile);
        RestaurantOwner nicolasBenoit = new RestaurantOwner("Nicolas", "Benoit", texan);

        Customer catherine = new Customer("Catherine", "Zwahlen", OTHER);
        Customer clementine = new Customer("Clementine", "Delerce", OTHER);

        // *************
        // *** MEALS ***
        // *************

        robertDupont.addMeal("Pizza tonno", 20.50, Meal.Type.OTHER);
        robertDupont.addMeal("Pasta bolognese", 18.30, Meal.Type.OTHER);
        robertDupont.addMeal("Tiramisu", 12.80, Meal.Type.VEGETARIAN);

        magaliNoel.addMeal("Cassoulet", 22.60, Meal.Type.OTHER);
        magaliNoel.addMeal("Risotto", 19.15, Meal.Type.VEGETARIAN);
        magaliNoel.addMeal("Banana split", 14.90, Meal.Type.VEGETARIAN);

        nicolasBenoit.addMeal("Burger vege", 21.10, Meal.Type.VEGETARIAN);
        nicolasBenoit.addMeal("Fajitas", 24.0, Meal.Type.OTHER);

        // **************
        // *** ORDERS ***
        // **************

        catherine.makeOrder(new OrderCommand(ticino, List.of("Pizza tonno", "Tiramisu")));
        clementine.makeOrder(new OrderCommand(etoile, List.of("Risotto", "Banana split")));

        List<OrderCommand> ordersCommand = List.of(
                new OrderCommand(ticino, List.of("Pasta bolognese")),
                new OrderCommand(etoile, List.of("Risotto", "Cassoulet")),
                new OrderCommand(texan, List.of("Burger vege", "Fajitas"))
        );
        catherine.makeOrders(ordersCommand);

        System.out.println("done");
    }
}