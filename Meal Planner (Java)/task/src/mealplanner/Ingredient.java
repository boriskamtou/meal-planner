package mealplanner;

public class Ingredient {
    private int ingredient_id;
    private String ingredient;
    private int meal_id;

    public Ingredient(int ingredient_id, String ingredient, int meal_id) {
        this.ingredient_id = ingredient_id;
        this.ingredient = ingredient;
        this.meal_id = meal_id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }
}
