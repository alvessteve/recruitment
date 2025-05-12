package model.restaurant;

import java.time.LocalDate;

public record MealPriceEvolution(LocalDate date, Double oldPrice, Double newPrice) {
    public MealPriceEvolution {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (oldPrice == null || newPrice == null) {
            throw new IncorrectPriceException("Price cannot be null");
        }
        if (oldPrice < 0 || newPrice < 0) {
            throw new IncorrectPriceException("Price cannot be negative");
        }
    }
}
