package subway.path.domain;

import subway.station.domain.Station;

public interface PathService {

    PathInfo findPath(Station start, Station end );

}
