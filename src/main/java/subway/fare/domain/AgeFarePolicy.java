package subway.fare.domain;

public class AgeFarePolicy {
    public static int applyAgeDiscount(int fare, int age) {
        AgeGrade ageGrade = AgeGrade.of(age);
        return ageGrade.getDiscountedFare(fare);
    }
}
