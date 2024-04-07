/* package mealplanner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MealDBClient {
    private final DataSource dataSource;

    public MealDBClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run(String str) {
        try (
                Connection con = dataSource.getConnection();
                Statement statement = con.createStatement()
        ) {
            statement.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Meal select(String query) {
        List<Meal> meals = selectForList(query);
        if (meals.size() == 1) {
            return meals.get(0);
        } else if (meals.size() == 0) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Meal> selectForList(String query) {
        List<Meal> meals = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                // Retrieve column values
                int id = resultSetItem.getInt("meal_id");
                String mealName = resultSetItem.getString("meal");
                String category = resultSetItem.getString("category");
                Meal meal = new Meal(id, category, mealName, new ArrayList<>());
                meals.add(meal);
            }

            return meals;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meals;
    }


}
*/