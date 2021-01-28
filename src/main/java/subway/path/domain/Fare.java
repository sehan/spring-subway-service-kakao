package subway.path.domain;

public class Fare {
    private int fare;

    private Fare(int fare) {
        this.fare = fare;
    }

    public static Fare of(int fare){
        return new Fare(fare);
    }

    public int getValue() {
        return fare;
    }

    public void plus(int plusValue ) {
        fare += plusValue;
    }

    public void minus(int minusValue) {
        fare -= minusValue;
    }

    public void multiply(double value) {
        fare *= value;
    }
}
