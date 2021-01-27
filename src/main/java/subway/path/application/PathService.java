package subway.path.application;

import org.springframework.stereotype.Service;
import subway.path.domain.Path;
import subway.path.domain.PathFinder;
import subway.station.domain.Station;

@Service
public class PathService {

    private PathFinder pathFinder;

    public PathService(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    public Path findPath(Station start, Station end ) {
        return pathFinder.findShortestPath(start, end);
    }

}
