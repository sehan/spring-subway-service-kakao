package subway.path.domain;

public interface FareCalculator {
    Fare calculate(Path path, Age age);
}

