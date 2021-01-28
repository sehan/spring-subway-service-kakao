package subway.path.domain;

import java.util.List;

public interface FareCalculator {
    Fare calculate(Path path, Age age);
}

