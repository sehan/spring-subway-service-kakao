package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

public class SimpleSubwayMap implements SubwayMap {

    private final PathFinder pathFinder;
    private final LineExtraFareTable lineExtraFareTable;
    private final FareCalculator fareCalculator;

    public SimpleSubwayMap(SimplePathFinder pathFinder, LineExtraFareTable lineExtraFareTable) {
        this.pathFinder = pathFinder;
        this.lineExtraFareTable = lineExtraFareTable;
        this.fareCalculator = new SimpleFareCalculator(Arrays.asList(
                new DistanceFarePolicy(),
                new LineExtraFarePolicy(lineExtraFareTable),
                new AgeFarePolicy()
        ));
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

    @Override
    public void addStation(Station station) {
        pathFinder.addStation(station);
    }

    @Override
    public void addSection(Section section){
        pathFinder.addSection(section);
    }

    @Override
    public void refresh(List<Line> lines) {
        pathFinder.refresh(lines);
        lineExtraFareTable.refresh(lines);
    }


}
