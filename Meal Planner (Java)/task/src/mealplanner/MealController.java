package mealplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MealController {
    private static final String INGREDIENTS = "Ingredients";
    private static final String NAME = "Name";
    private static final String CATEGORIES = "Category";

    static int ID = 1;

    private static final String regexIngredients = "^(?!\\s*$)([\\p{L}\\s]+)+$";


    static List<Meal> meals = new ArrayList<>();

    public void initializeDB() {
        DatabaseManager.createMealTable();
        DatabaseManager.createIngredientsTable();
    }

    public void show() {
        List<Meal> meals = DatabaseManager.getMeals();
        if (meals.isEmpty()) {
            System.out.println("No meals saved. Add a meal first.");
            return;
        }
        for (Meal meal : meals) {
            System.out.println();
            System.out.printf("Category: %s \n", meal.getCategory().trim());
            System.out.printf("Name: %s \n", meal.getName().trim());
            System.out.println("Ingredients: ");
            for (String ingredient : meal.getIngredients()) {
                System.out.println(ingredient.trim());
            }
            System.out.println();
        }
    }

    public static boolean verifyIngredients(String[] ingredients) {
        boolean isValid = true;
        for (String ingredient : ingredients) {
            if (!ingredient.matches(regexIngredients)) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public void add() {
        Scanner sc = new Scanner(System.in);

        // Save meal category
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        String mealCategory;
        do {
            mealCategory = sc.nextLine();
            if (!mealCategory.matches("breakfast|lunch|dinner")) {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        } while (!mealCategory.matches("breakfast|lunch|dinner"));


        // Save meal name
        System.out.println("Input the meal's name:");
        String mealName;
        do {
            mealName = sc.nextLine();
            if (!mealName.matches("^(?!\\s*$)[a-zA-Z\\s]+$")) {
                System.out.println("Wrong format. Use letters only!");
            }
        } while (!mealName.matches("^(?!\\s*$)[a-zA-Z\\s]+$"));


        // Save Ingredients
        System.out.println("Input the ingredients:");
        String mealIngredients;
        do {
            mealIngredients = sc.nextLine();
            String[] ingredients = mealIngredients.split(",");
            boolean isValidIngredients = verifyIngredients(ingredients);

            if (!isValidIngredients) {
                System.out.println("Wrong format. Use letters only!");
            }
        } while (!verifyIngredients(mealIngredients.split(",")));

        DatabaseManager.insertMeal(ID, mealCategory, mealName, mealIngredients);
        ID = ID + 1;
    }

}