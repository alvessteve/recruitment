package model.user;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.Getter;
import model.Entity;
import model.restaurant.Meal;
import model.restaurant.Restaurant;
import static java.lang.String.format;
import static java.time.LocalDate.now;

public class Order implements Entity
{
    @Getter
    private final LocalDate date;

    @Getter
    private final Restaurant restaurant;

    @Getter
    private final Customer customer;

    @Getter
    private final List<Meal> meals;

    Order(Restaurant restaurant, Customer customer, List<String> mealNames)
    {
        this.date = now();
        this.restaurant = restaurant.withReceivedOrder(this);
        this.customer = customer;
        this.meals = mealNames.stream().map(restaurant::getMealByName).toList();
    }

    public String getName()
    {
        return format("From %s - in %s", customer.getName(), restaurant.getName());
    }

    /**
     * Methode qui doit encore continuer a etre refactorée.
     * Par manque de temps, je n'ai pu appliquer le principe du boy scout rule que pour des choses ameliorant la lisibilite
     * comme:
     * - enlever la bouble while en for
     * - deplacer dans des fonctions dediees les differentes etapes (application de la promo du second plat et le calcul du prix)
     *
     * Mais avant cela un jeu de tests, respectant le cahier des charges et le comportement attendu, a été crée visible ci-dessous
     * {@link OrderTest pour les tests du calcul de prix}
     */
    public Double getPrice()
    {
        Double totalAmount = 0D;
        int mealNumber = 0;
        for (Meal each : meals) {
            mealNumber += 1;

            if (eligibleForFreeSecondMealDiscount(mealNumber))
                continue;

            totalAmount = processCustomerDiscounts(each, totalAmount);
        }
        return totalAmount;
    }

    private boolean eligibleForFreeSecondMealDiscount(int mealNumber) {
        boolean hasOrderedInThePastWeek = false;
        for (Order order : customer.getOrders()) {
            if (order != this && ChronoUnit.DAYS.between(order.date, now()) <= 7)
                hasOrderedInThePastWeek = true;
        }
        return hasOrderedInThePastWeek && mealNumber == 2;
    }

    /**
     * //FIXME methode a refactorer car contient plusieurs problemes:
     *  - magic numbers
     *  - manque de separations des responsabilites (gestion du discount + calcul du prix)
     *  - duplication/forte similarites des comportements
     *
     *  des pistes sont possibles et à creuser comme utiliser un pattern decorator pour le discount (semble s'y preter à premiere vue) si on veut faire un gros refacto
     *  ou alors continuer à isoler et décomposer si on veut le faire entrer dans un process de tiny steps de refacto qui peuvent etre inclus dans une story.
     *
     * Par manque de temps, je n'ai pu que l'isoler
     */
    private Double processCustomerDiscounts(Meal each, Double totalAmount) {
        switch (customer.getType()) {
            case CHILD:
                if (customer.getOrders().size() % 10 == 0)
                    totalAmount += each.getPrice() * (1 - 0.5 - 0.10 - 0.15);
                else if (customer.getOrders().stream().filter(o -> o.getRestaurant().equals(restaurant)).count() % 5 == 0)
                    totalAmount += each.getPrice() * (1 - 0.5 - 0.10);
                else totalAmount += each.getPrice() * (1 - 0.5);
                break;
            case STUDENT:
                if (customer.getOrders().size() % 10 == 0)
                    totalAmount += each.getPrice() * (1 - 0.25 - 0.10 - 0.15);
                else if (customer.getOrders().stream().filter(o -> o.getRestaurant().equals(restaurant)).count() % 5 == 0)
                    totalAmount += each.getPrice() * (1 - 0.25 - 0.10);
                else totalAmount += each.getPrice() * (1 - 0.25);
                break;
            default:
                if (customer.getOrders().size() % 10 == 0)
                    totalAmount += each.getPrice() * (1 - 0.10 - 0.15);
                else if (customer.getOrders().stream().filter(o -> o.getRestaurant().equals(restaurant)).count() % 5 == 0)
                    totalAmount += each.getPrice() * (1 - 0.10);
                else totalAmount += each.getPrice();
        }
        return totalAmount;
    }
}
