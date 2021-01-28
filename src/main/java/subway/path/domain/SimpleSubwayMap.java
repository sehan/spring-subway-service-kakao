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
        return findPath(start, end, Age.adult());
    }

    @Override
    public PathInfo findPath(Station start, Station end, Age age) {
        Path path = pathFinder.findShortestPath(start, end);
        Fare fare = fareCalculator.calculate(path, age);
        return PathInfo.of(path, fare);

    }
}
