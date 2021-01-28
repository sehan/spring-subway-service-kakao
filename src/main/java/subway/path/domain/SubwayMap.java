package subway.path.domain;

import subway.station.domain.Station;

public interface SubwayMap {

    PathInfo findPath(Station start, Station end );

}
