package subway.path.domain;

import java.util.stream.Stream;

public class AgeFarePolicy implements FarePolicy{

    enum AgeGroup {
        Adult(20, Integer.MAX_VALUE, 0, 0),
        Teenager(13,19, 350, 20),
        Children(6,13, 350, 50),
        Infants(0,6, 0, 100);

        private int min;
        private int maxExclude;
        private int deductedAmount;
        private int rateOfDiscount;

        AgeGroup(int min, int maxExclude, int deductedAmount, int rateOfDiscount) {
            this.min = min;
            this.maxExclude = maxExclude;
            this.deductedAmount = deductedAmount;
            this.rateOfDiscount = rateOfDiscount;
        }

        boolean contains(int age){
            return min <= age && age < maxExclude;
        }

        public static AgeGroup valueOfAge(int age){
            return Stream.of(AgeGroup.values())
                    .filter(ageGroup -> ageGroup.contains(age))
                    .findFirst()
                    .orElse(Adult);
        }

        public void applyDiscount(Fare fare) {
            if( rateOfDiscount > 0 ) {
                int discountFare = (fare.getValue() - deductedAmount) * rateOfDiscount / 100;
                fare.minus(discountFare);
            }
        }
    }

    @Override
    public Fare apply(Fare fare, Path path, Age age) {

        AgeGroup ageGroup = AgeGroup.valueOfAge(age.getValue());
        ageGroup.applyDiscount(fare);

        return fare;
    }
}
