package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public class PathInfo {

    private final Path path;
    private final Fare fare;

    private PathInfo(Path path, Fare fare) {
        this.path = path;
        this.fare = fare;
    }

    public static PathInfo of(Path path, Fare fare) {
        return new PathInfo(path, fare);
    }

    public List<Station> getStations(){
        return path.getStations();
    }

    public int getDistance(){
        return path.getDistance();
    }

    public int getFare(){
        return fare.getValue();
    }
}
