package subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.path.domain.PathInfo;
import subway.path.domain.PathService;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;

@RestController
@RequestMapping("/paths")
public class PathController {

    private PathService pathService;
    private StationService stationService;

    public PathController(PathService pathService, StationService stationService) {
        this.pathService = pathService;
        this.stationService = stationService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> paths(Long source, Long target){
        PathInfo pathInfo = pathService.findPath(stationService.findStationById(source), stationService.findStationById(target));
        return ResponseEntity.ok(PathResponse.of(pathInfo));
    }

}
