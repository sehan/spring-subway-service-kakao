package subway.station.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import subway.station.domain.Station;

public class StationResponse {
    private final Long id;
    private final String name;

    @JsonCreator
    public StationResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static StationResponse of(Station station) {
        return new StationResponse(station.getId(), station.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
