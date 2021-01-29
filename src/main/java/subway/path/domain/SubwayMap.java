package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public interface SubwayMap {

    PathInfo findPath(Station start, Station end );

    PathInfo findPath(Station start, Station end, Age age );

    void addStation(Station station);

    void addSection(Section section);

    void refresh(List<Line> lines);
}
