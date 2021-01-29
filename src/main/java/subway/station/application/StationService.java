package subway.station.application;

import org.springframework.stereotype.Service;
import subway.path.domain.SubwayMap;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationRequest;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {
    private StationDao stationDao;
    private SubwayMap subwayMap;

    public StationService(StationDao stationDao, SubwayMap subwayMap) {
        this.stationDao = stationDao;
        this.subwayMap = subwayMap;
    }

    public StationResponse saveStation(StationRequest stationRequest) {
        Station station = stationDao.insert(stationRequest.toStation());
        subwayMap.addStation(station);
        return StationResponse.of(station);
    }

    public Station findStationById(Long id) {
        return stationDao.findById(id);
    }

    public List<StationResponse> findAllStationResponses() {
        List<Station> stations = stationDao.findAll();

        return stations.stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteStationById(Long id) {
        stationDao.deleteById(id);
    }
}
