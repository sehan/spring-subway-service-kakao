package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.path.domain.PathInfo;
import subway.path.domain.SubwayMap;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;

@RestController
@RequestMapping("/paths")
public class PathController {

    private SubwayMap subwayMap;
    private StationService stationService;

    public PathController(SubwayMap subwayMap, StationService stationService) {
        this.subwayMap = subwayMap;
        this.stationService = stationService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> paths(Long source, Long target){
        PathInfo pathInfo = subwayMap.findPath(stationService.findStationById(source), stationService.findStationById(target));
        return ResponseEntity.ok(PathResponse.of(pathInfo));
    }

}
