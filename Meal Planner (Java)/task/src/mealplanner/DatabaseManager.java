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
    }

    // ----------------------------- TABLE CREATION ------------------------------------------------------
    public static void createPlanTable() {
        String createPlanTable = "CREATE TABLE IF NOT EXISTS plan (" +
                "plan_id INTEGER NOT NULL, meal_id INTEGER NOT NULL, meal_category VARCHAR(30) NOT NULL, " +
                "meal_name VARCHAR(50) NOT NULL, plan_day VARCHAR" +
                ")";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            connection.setAutoCommit(true);
            PreparedStatement preparedStatement = connection.prepareStatement(createPlanTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
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

    // ----------------------------- DATA MANIPULATION ------------------------------------------------------
    public static void insertPlan(int planID, int mealID, String mealCategory, String mealName, String planDay) {
        String insert = "INSERT INTO plan (plan_id, meal_id, meal_category, meal_name, plan_day) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            connection.setAutoCommit(true);
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, planID);
            preparedStatement.setInt(2, mealID);
            preparedStatement.setString(3, mealCategory);
            preparedStatement.setString(4, mealName);
            preparedStatement.setString(5, planDay);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static Plan selectPlanByDay(String day, String category) {
        String selectPlanByDay = String.format("SELECT *  FROM plan WHERE plan_day = '%s' AND meal_category = '%s'", day, category);
        Plan plan = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement = connection.prepareStatement(selectPlanByDay);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                plan = new Plan(
                        rs.getInt("plan_id"),
                        rs.getInt("meal_id"),
                        rs.getString("meal_category"),
                        rs.getString("meal_name"),
                        rs.getString("plan_day")
                );
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return plan;

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
    public static List<Meal> selectMeals() {

        List<Ingredient> ingredients = getIngredients();

        List<Meal> meals = new ArrayList<>();

        String selectAllMeals = "SELECT * FROM meals";

        return selectMeals(ingredients, meals, selectAllMeals);
    }

    // Select meal by name
    public static Meal selectMealByName(String mealName) {
        List<Ingredient> ingredients = getIngredients();

        String selectMealByName = String.format("SELECT * FROM meals WHERE meal = '%s'", mealName);

        Meal meal = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement = connection.prepareStatement(selectMealByName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int mealID = rs.getInt("meal_id");
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getMeal_id() == mealID) {
                        meal = new Meal(
                                rs.getInt("meal_id"),
                                rs.getString("category"),
                                rs.getString("meal"),
                                List.of(ingredient.getIngredient().split(","))
                        );
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return meal;
    }

    // Get meals by their category
    public static List<Meal> selectMealsByCategory(String category) {
        List<Ingredient> ingredients = getIngredients();

        List<Meal> meals = new ArrayList<>();

        String selectAllMeals = String.format("SELECT * FROM meals WHERE category = '%s'", category);

        return selectMeals(ingredients, meals, selectAllMeals);
    }

    public static List<Meal> selectMealsByCategoryAndOrder(String category) {
        List<Ingredient> ingredients = getIngredients();

        List<Meal> meals = new ArrayList<>();

        String selectAllMeals = String.format("SELECT * FROM meals WHERE category = '%s' ORDER BY meal", category);

        return selectMeals(ingredients, meals, selectAllMeals);
    }

    // General method to get meals
    private static List<Meal> selectMeals(List<Ingredient> ingredients, List<Meal> meals, String selectAllMeals) {
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

    // Count the number of meals in the database
    public static int getNumberOfMeals() {
        int count = 0;
        String countAllMeals = "SELECT COUNT(*) FROM meals";
        return countRow(count, countAllMeals);
    }

    public static int countNumberOfPlan() {
        int count = 0;
        String countAllPlan = "SELECT COUNT(*) FROM plan";
        return countRow(count, countAllPlan);
    }

    private static int countRow(int count, String countAllPlan) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement preparedStatement = connection.prepareStatement(countAllPlan);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return count;
    }
}