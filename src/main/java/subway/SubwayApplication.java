package subway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.domain.SimplePathFinder;
import subway.path.domain.PathFinder;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SubwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubwayApplication.class, args);
    }

    @Bean
    public PathFinder pathFinder(LineDao lineDao, StationDao stationDao){
        List<Line> lines = lineDao.findAll();
        List<Station> allStations = stationDao.findAll();

        List<Section> allSections = lines.stream()
                .flatMap(line -> line.getSections().getSections().stream())
                .distinct()
                .collect(Collectors.toList());
        return new SimplePathFinder(allStations, allSections);
    }
}

