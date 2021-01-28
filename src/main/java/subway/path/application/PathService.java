package subway.path.application;

import org.springframework.stereotype.Service;
import subway.path.domain.PathInfo;
import subway.path.domain.SubwayMap;
import subway.station.domain.Station;

@Service
public class PathService {

    private SubwayMap subwayMap;

    public PathService(SubwayMap subwayMap) {
        this.subwayMap = subwayMap;
    }

    public PathInfo findPath(Station start, Station end){
        return subwayMap.findPath(start, end);
    }
}
