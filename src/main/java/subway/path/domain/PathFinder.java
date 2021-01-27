package subway.path.domain;

import subway.station.domain.Station;

public interface PathFinder {

    Path findShortestPath(Station start, Station end);

}

