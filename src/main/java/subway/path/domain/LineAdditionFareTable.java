package subway.path.domain;

import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.Map;
import java.util.Objects;

public class LineAdditionFareTable {

    Map<Section, Integer> addtionFareTable;

    public LineAdditionFareTable(Map<Section, Integer> additionFareTable) {
        this.addtionFareTable = additionFareTable;
    }

    public Integer get(Station up, Station down) {
        Section key = addtionFareTable.keySet()
                .stream()
                .filter(section -> section.getUpStation().equals(up) && section.getDownStation().equals(down))
                .findFirst()
                .orElse(null);

        if(Objects.nonNull(key)){
            return addtionFareTable.get(key);
        }
        return 0;
    }
}
