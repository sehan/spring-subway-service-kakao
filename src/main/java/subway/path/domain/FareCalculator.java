package subway.path.domain;

import java.util.List;

public interface FareCalculator {
    Fare calculate(Path path, Age age);
}

class SimpleFareCalculator implements FareCalculator {

    private List<FarePolicy> farePolicies;

    public SimpleFareCalculator(List<FarePolicy> farePolicies) {
        this.farePolicies = farePolicies;
    }

    @Override
    public Fare calculate(Path path, Age age) {
        Fare fare = Fare.of(0);
        for( FarePolicy farePolicy : farePolicies ){
            farePolicy.apply(fare, path, age);
        }
        return fare;
    }
}
