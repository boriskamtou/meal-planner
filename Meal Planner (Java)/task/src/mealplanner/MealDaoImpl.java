/* package mealplanner;

import java.util.List;

public class MealDaoImpl implements MealDao{

    private static final String CONNECTION_URL = "jdbc:postgresql:meals_db";

 private static final    String CREATE_DB = "CREATE TABLE IF NOT EXISTS meals (" +
            "meal_id INTEGER NOT NULL, category VARCHAR(30) NOT NULL, meal VARCHAR(50) NOT NULL" +
            ")";
    private static final String SELECT_ALL = "SELECT * FROM meals";
    private static final String SELECT = "SELECT * FROM meals WHERE id = %d";
    private static final String INSERT_DATA = "INSERT INTO DEVELOPER VALUES (%d , '%s')";
    private static final String UPDATE_DATA = "UPDATE DEVELOPER SET name " +
            "= '%s' WHERE id = %d";
    private static final String DELETE_DATA = "DELETE FROM DEVELOPER WHERE id = %d";

    private final MealDBClient dbClient;

    public MealDaoImpl(MealDBClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public List<Meal> findAll() {
        return null;
    }

    @Override
    public Meal findMealById(int id) {
        return null;
    }

    @Override
    public void addMeal(Meal meal) {

    }

    @Override
    public void updateMeal(Meal meal) {

    }

    @Override
    public void deleteMeal(int id) {

    }
}
*/