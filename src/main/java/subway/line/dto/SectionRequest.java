package subway.line.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SectionRequest {
    private final Long upStationId;
    private final Long downStationId;
    private final int distance;

    @JsonCreator
    public SectionRequest(Long upStationId, Long downStationId, int distance) {
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }
}
