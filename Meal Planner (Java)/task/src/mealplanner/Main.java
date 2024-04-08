package mealplanner;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        MealController mealController = new MealController();
        mealController.initializeDB();
        Scanner sc = new Scanner(System.in);
        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("What would you like to do (add, show, plan, save, exit)?");
            String userChoice = sc.nextLine();

            switch (userChoice) {
                case "exit" -> {
                    shouldContinue = false;
                    System.out.println("Bye!");
                }
                case "add" -> mealController.add();
                case "show" -> mealController.show();
                case "plan" -> mealController.plan();
                case "save" -> mealController.save();
            }
        }
    }
}