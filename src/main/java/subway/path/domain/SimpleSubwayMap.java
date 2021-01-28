package subway.path.domain;

import subway.station.domain.Station;

public class SimpleSubwayMap implements SubwayMap {

    private PathFinder pathFinder;
    private FareCalculator fareCalculator;

    public SimpleSubwayMap(SimplePathFinder pathFinder, FareCalculator fareCalculator) {
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
