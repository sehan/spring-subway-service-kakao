package subway.path.domain;

import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LineExtraFarePolicy implements FarePolicy{

    private final LineExtraFareTable lineExtraFareTable;

    public LineExtraFarePolicy(LineExtraFareTable lineExtraFareTable) {
        this.lineExtraFareTable = lineExtraFareTable;
    }

    public LineExtraFarePolicy(Map<Section, Integer> lineAdditionFareData) {
        this(new LineExtraFareTable(lineAdditionFareData));
    }

    @Override
    public Fare apply(Fare fare, Path path, Age age) {

        List<Integer> extraFares = findLineExtraFare(path.getStations());
        fare.plus(extraFares.stream()
                .reduce(Integer::max)
                .get());

        return fare;
    }

    private List<Integer> findLineExtraFare(List<Station> stations) {
        Station up, down;
        List<Integer> lineExtraFares = new ArrayList<>();
        for( int idx = 0 ; stations.size() - 1 > idx ; idx++ ){
            up = stations.get(idx);
            down = stations.get(idx+1);
            lineExtraFares.add(lineExtraFareTable.get(up, down));
        }

        return lineExtraFares;
    }

}
