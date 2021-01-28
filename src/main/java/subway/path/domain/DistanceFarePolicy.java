package subway.path.domain;

public class DistanceFarePolicy implements FarePolicy{

    private final int DEFAULT_FARE = 1250;

    enum AdditionFare {
        MIDDLE_RANGE(11,50,5,100),
        LONG_RANGE(51,Integer.MAX_VALUE,8,100);

        final static int DEFAULT_DISTANCE = 10;

        private int min;
        private int max;
        private int unit;
        private int farePerUnit;

        AdditionFare(int min, int max, int unit, int farePerUnit) {
            this.min = min;
            this.max = max;
            this.unit = unit;
            this.farePerUnit = farePerUnit;
        }

        public int getFare(int additionDistance) {
            if( additionDistance <= 0 ) return 0;

            int numOfUnit = additionDistance / unit;
            if( additionDistance % unit > 0 ) numOfUnit++;
            return numOfUnit * farePerUnit;
        }

        public static int calFare(int distance) {
            if( distance <= DEFAULT_DISTANCE ) return 0;

            int middleRangeDistance = Math.min(distance, MIDDLE_RANGE.max ) - DEFAULT_DISTANCE;
            int fare = MIDDLE_RANGE.getFare(middleRangeDistance);

            int longRangeDistance = distance - MIDDLE_RANGE.max;
            fare += LONG_RANGE.getFare(longRangeDistance);

            return fare;
        }
    }

    @Override
    public Fare apply(Fare fare, Path path, Age age) {
        fare.add(DEFAULT_FARE);
        fare.add(AdditionFare.calFare(path.getDistance()));
        return fare;
    }


}
