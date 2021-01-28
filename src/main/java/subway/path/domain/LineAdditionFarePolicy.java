package subway.path.domain;

import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LineAdditionFarePolicy implements FarePolicy{

    private LineAdditionFareTable lineAdditionFareTable;

    public LineAdditionFarePolicy(LineAdditionFareTable lineAdditionFareTable) {
        this.lineAdditionFareTable = lineAdditionFareTable;
    }

    public LineAdditionFarePolicy(Map<Section, Integer> lineAdditionFareData) {
        this(new LineAdditionFareTable(lineAdditionFareData));
    }

    @Override
    public Fare apply(Fare fare, Path path, Age age) {

        List<Integer> additionFares = findLineAdditionFare(path.getStations());
        fare.add(additionFares.stream()
                .reduce(Integer::max)
                .get());

        return fare;
    }

    private List<Integer> findLineAdditionFare(List<Station> stations) {
        Station up, down;
        List<Integer> lineAdditionFares = new ArrayList<>();
        for( int idx = 0 ; stations.size() - 1 > idx ; idx++ ){
            up = stations.get(idx);
            down = stations.get(idx+1);
            lineAdditionFares.add(lineAdditionFareTable.get(up, down));
        }

        return lineAdditionFares;
    }

}
