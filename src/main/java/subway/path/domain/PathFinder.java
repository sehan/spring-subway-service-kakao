package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public interface PathFinder {

    void addStation(Station station);

    void addSection(Section section);

    Path findShortestPath(Station start, Station end);

    void refresh(List<Line> lines);
}

