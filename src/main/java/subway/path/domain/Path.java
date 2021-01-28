package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public interface Path {

    static Path of(List<Station> stations, int distance) {
        return new SimplePath(stations, distance);
    }

    List<Station> getStations();

    int getDistance();

    class SimplePath implements Path {
        private List<Station> stations;
        private int distance;

        private SimplePath(List<Station> stations, int distance) {
            this.stations = stations;
            this.distance = distance;
        }

        @Override
        public List<Station> getStations() {
            return stations;
        }

        @Override
        public int getDistance() {
            return distance;
        }

    }
}

