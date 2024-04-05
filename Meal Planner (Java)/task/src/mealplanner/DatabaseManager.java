package mealplanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseManager {
    static String DB_URL = "jdbc:postgresql:meals_db";
    static String USER = "postgres";
    static String PASS = "1111";

    public DatabaseManager() {
        createMealTable();
        createIngredientsTable();
    }


    public static void createMealTable() {
        String createMealTable = "CREATE TABLE IF NOT EXISTS meals (" +
                "meal_id INTEGER NOT NULL, category VARCHAR(30) NOT NULL, meal VARCHAR(50) NOT NULL" +
                ")";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            connection.setAutoCommit(true);
            PreparedStatement preparedStatement = connection.prepareStatement(createMealTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void createIngredientsTable() {
        String createIngredientsTable = "CREATE TABLE IF NOT EXISTS ingredients (" +
                "ingredient VARCHAR NOT NULL, ingredient_id INTEGER NOT NULL, meal_id INTEGER NOT NULL" +
                ")";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            connection.setAutoCommit(true);
            PreparedStatement preparedStatement = connection.prepareStatement(createIngredientsTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void insertMeal(int mealID, String mealCategory, String mealName, String ingredient) {
        String insert = "INSERT INTO meals (meal_id, category, meal) VALUES(?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            connection.setAutoCommit(true);
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, mealID);
            preparedStatement.setString(2, mealCategory);
            preparedStatement.setString(3, mealName);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        insertIngredient(mealID, ingredient, mealID);

        System.out.println("The meal has been added!");
    }

    public static void insertIngredient(int ingredientID, String ingredient, int mealID) {
        String insert = "INSERT INTO ingredients (ingredient_id, ingredient, meal_id) VALUES(?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            connection.setAutoCommit(true);
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, ingredientID);
            preparedStatement.setString(2, ingredient);
            preparedStatement.setInt(3, mealID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    // Get list of all meals in the database
    public static List<Meal> getMeals() {

        List<Ingredient> ingredients = getIngredients();

        List<Meal> meals = new ArrayList<>();

        String selectAllMeals = "SELECT * FROM meals";

        return getMeals(ingredients, meals, selectAllMeals);
    }

    // Get meals by their category
    public static List<Meal> getMealsByCategory(String category) {
        List<Ingredient> ingredients = getIngredients();

        List<Meal> meals = new ArrayList<>();

        String selectAllMeals = String.format("SELECT * FROM meals WHERE category = '%s'", category);

        return getMeals(ingredients, meals, selectAllMeals);
    }

    // General method to get meals
    private static List<Meal> getMeals(List<Ingredient> ingredients, List<Meal> meals, String selectAllMeals) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllMeals);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int mealID = rs.getInt("meal_id");
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getMeal_id() == mealID) {
                        Meal meal = new Meal(
                                rs.getInt("meal_id"),
                                rs.getString("category"),
                                rs.getString("meal"),
                                List.of(ingredient.getIngredient().split(","))
                        );
                        meals.add(meal);
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return meals;
    }

    public static List<Ingredient> getIngredients() {

        List<Ingredient> ingredients = new ArrayList<>();

        String selectAllIngredients = "SELECT * FROM ingredients";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllIngredients);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient(rs.getInt("ingredient_id"), rs.getString("ingredient"), rs.getInt("meal_id"));
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return ingredients;
    }

    public static int getNumberOfMeals() {
        int count = 0;
        String countAllMeals = "SELECT COUNT(*) FROM meals";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement = connection.prepareStatement(countAllMeals);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return count;
    }
}