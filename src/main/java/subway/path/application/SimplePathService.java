package subway.path.application;

import org.springframework.stereotype.Service;
import subway.path.domain.*;
import subway.station.domain.Station;

@Service
public class SimplePathService implements PathService {

    private PathFinder pathFinder;
    private FareCalculator fareCalculator;

    public SimplePathService(PathFinder pathFinder, FareCalculator fareCalculator) {
        this.pathFinder = pathFinder;
        this.fareCalculator = fareCalculator;
    }

    @Override
    public PathInfo findPath(Station start, Station end) {
        Path path = pathFinder.findShortestPath(start, end);
        Fare fare = fareCalculator.calculate(path, Age.of(13));
        return PathInfo.of(path, fare);
    }
}
