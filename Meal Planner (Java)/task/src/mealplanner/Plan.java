package mealplanner;

public class Plan {

    private int planId;
    private int mealId;
    private String mealCategory;
    private String mealName;
    private String planDay;

    public Plan(int planId, int mealId, String mealCategory, String mealName, String planDay) {
        this.planId = planId;
        this.mealId = mealId;
        this.mealCategory = mealCategory;
        this.mealName = mealName;
        this.planDay = planDay;
    }
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getPlanDay() {
        return planDay;
    }

    public void setPlanDay(String planDay) {
        this.planDay = planDay;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "planId=" + planId +
                ", mealId=" + mealId +
                ", mealCategory='" + mealCategory + '\'' +
                ", mealName='" + mealName + '\'' +
                ", planDay='" + planDay + '\'' +
                '}';
    }
}
