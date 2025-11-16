package com.breadhardit.travelagencykata;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class StructuralPatternExercices {
    /*
     * We have a calories-counter API. The users can ask how much calories food have.
     * User can ask for a list of items. And this item's can be dishes or ingredients.
     * A dish has a list of ingredients, and each ingredient has calories.
     * Te number of calories to return in a query is the summary of the calories of
     * the elements queried
     */

    public static class Menu {
        List<MenuItem> menuItems;

        public Menu(List<MenuItem> menuItems) {
            this.menuItems = menuItems;
        }

        public Long calculateCalories(){
            return this.menuItems.stream().collect(Collectors.summarizingLong(MenuItem::calculateCalories)).getSum();
        }
    }

    public interface MenuItem {
        long calculateCalories();
    }

    public record Dish(String name, List<Food> foodList) implements MenuItem {

        @Override
        public long calculateCalories() {
            return this.foodList.stream().collect(Collectors.summarizingLong(Food::getCalories)).getSum();
        }
    }

    public record Food(String name, Long caloresPer100g, Long weight) implements MenuItem {

        public Long getCalories() {
            return caloresPer100g * weight;
        }

        @Override
        public long calculateCalories() {
            return getCalories();
        }
    }

    @Test
    void testCalories() {
        Food potato = new Food("POTATO", 80L, 300L);
        Food bread = new Food("BREAD", 320L, 100L);
        Food tomato = new Food("TOMATO", 85L, 50L);
        Food burger = new Food("BURGER", 340L, 120L);
        Food lettuce = new Food("LETTUCE", 16L, 120L);
        Food apple = new Food("APPLE", 34L, 180L);
        Food ketchup = new Food("KETCHUP", 180L, 15L);
        Food beer = new Food("BEER", 80L, 330L);
        Dish completeBuger = new Dish("COMPLETE BURGER", List.of(potato, bread, burger));
        Dish greenSalad = new Dish("GREEN SALAD", List.of(lettuce, tomato));
        Menu menu = new Menu(List.of(greenSalad, completeBuger, ketchup, apple, beer));
        log.info("Calories: {}", menu.calculateCalories());
    }
}
