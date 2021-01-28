package subway.path.domain;

public interface FarePolicy {

    Fare apply(Fare fare, Path path, Age age);

}