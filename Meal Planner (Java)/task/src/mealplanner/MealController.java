package mealplanner;

import java.util.List;
import java.util.Scanner;

public class MealController {
    private static final String INGREDIENTS = "Ingredients";
    private static final String NAME = "Name";
    private static final String CATEGORIES = "Category";

    static int ID = 1;
    static int plan_id = 1;

    private static final String regexIngredients = "^(?!\\s*$)([\\p{L}\\s]+)+$";


    public static final List<String> WEEKS_DAY = List.of("Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday");
    private static final List<String> MEAL_CATEGORIES = List.of("breakfast", "lunch", "dinner");

    public void initializeDB() {
        DatabaseManager.createMealTable();
        DatabaseManager.createIngredientsTable();
        DatabaseManager.getNumberOfMeals();
        DatabaseManager.createPlanTable();
        ID = DatabaseManager.getNumberOfMeals();
        plan_id = DatabaseManager.countNumberOfPlan();
    }

    public boolean checkIfMealExist(List<Meal> meals, String meal) {
        boolean isMealExist = false;
        for (Meal m : meals) {
            if (m.getName().equals(meal)) {
                isMealExist = true;
                break;
            }
        }
        return isMealExist;
    }

    public void plan() {

        Scanner sc = new Scanner(System.in);
        for (String day : WEEKS_DAY) {
            System.out.println(day);
            for (String category : MEAL_CATEGORIES) {
                List<Meal> mealList = DatabaseManager.selectMealsByCategoryAndOrder(category.toLowerCase());

                for (Meal meal : mealList) {
                    System.out.println(meal.getName());
                }
                System.out.printf("Choose the %s for %s from the list above:\n", category, day);
                String mealChoose = sc.nextLine();

                boolean isMealExist = checkIfMealExist(mealList, mealChoose);
                while (!isMealExist) {
                    System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                    mealChoose = sc.nextLine();
                    isMealExist = checkIfMealExist(mealList, mealChoose);
                }
                Meal currentMealSelected = DatabaseManager.selectMealByName(mealChoose);
                plan_id = plan_id + 1;
                DatabaseManager.insertPlan(
                        plan_id,
                        currentMealSelected.getMeal_id(),
                        currentMealSelected.getCategory(),
                        currentMealSelected.getName(),
                        day
                );
            }
            System.out.printf("Yeah! We planned the meals for %s.\n", day);
            System.out.println();
        }

        displayPlan();
    }

    public void displayPlan() {
        for (String day : WEEKS_DAY) {
            System.out.println(day);
            for (String category : MEAL_CATEGORIES) {
                Plan plan = DatabaseManager.selectPlanByDay(day, category);
                System.out.println(String.format("%s: ", category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase()) + plan.getMealName());
            }
            System.out.println();
        }
    }

    public void show() {
        List<String> availableCategories = List.of("breakfast", "lunch", "dinner");

        Scanner sc = new Scanner(System.in);

        List<Meal> meals = DatabaseManager.selectMeals();
        if (meals.isEmpty()) {
            System.out.println("No meals found.");
            return;
        }

        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        String selectedCategory = sc.nextLine();
        while (!availableCategories.contains(selectedCategory)) {
            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            selectedCategory = sc.nextLine();
        }

        meals = DatabaseManager.selectMealsByCategory(selectedCategory);

        if (meals.isEmpty()) {
            System.out.println("No meals found.");
        } else {
            System.out.println("Category: " + selectedCategory);
            System.out.println();
        }

        for (Meal meal : meals) {
            if (meal.getCategory().trim().equals(selectedCategory.trim())) {
                System.out.printf("Name: %s \n", meal.getName().trim());
                System.out.println("Ingredients: ");
                for (String ingredient : meal.getIngredients()) {
                    System.out.println(ingredient.trim());
                }
                System.out.println();
            }
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
