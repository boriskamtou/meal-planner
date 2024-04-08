package mealplanner;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Meal {
    private int meal_id;
    private String category;
    private String name;
    private List<String> ingredients;

    public Meal(int meal_id, String category, String name, List<String> ingredients) {
        this.meal_id = meal_id;
        this.category = category;
        this.name = name;
        this.ingredients = ingredients;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}