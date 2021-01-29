package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 동일구간이 여러노선에 추가되지 않는다는 전재로 만든 Line 추가요금 테이블
 */
public class LineExtraFareTable {

    Map<Section, Integer> extraFareData;

    public LineExtraFareTable(Map<Section, Integer> extraFareData) {
        this.extraFareData = extraFareData;
    }

    public static LineExtraFareTable of(List<Line> lines) {
        return new LineExtraFareTable(ExtraFareDataFactory.create(lines));
    }

    public void add(Section section, int extraFare) {
        extraFareData.put(section, extraFare);
    }

    public void remove(Section section) {
        extraFareData.remove(section);
    }

    public void refresh(List<Line> lines) {
        extraFareData = ExtraFareDataFactory.create(lines);
    }

    public Integer get(Station up, Station down) {
        Section key = extraFareData.keySet()
                .stream()
                .filter(section -> (section.getUpStation().equals(up) && section.getDownStation().equals(down))
                        || (section.getUpStation().equals(down) && section.getDownStation().equals(up)))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(key)) {
            return extraFareData.get(key);
        }
        return 0;
    }

    static class ExtraFareDataFactory {
        public static Map<Section, Integer> create(List<Line> lines) {
            Map<Section, Integer> data = new HashMap<>();
            for (Line line : lines) {
                Long extraFare = line.getExtraFare();
                line.getSections()
                        .getSections()
                        .stream()
                        .forEach(section -> data.put(section, extraFare.intValue()));
            }
            return data;
        }
    }
}
