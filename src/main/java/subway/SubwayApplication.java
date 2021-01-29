package subway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.domain.*;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class SubwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubwayApplication.class, args);
    }

    @Bean
    public SubwayMap subwayMap(LineDao lineDao, StationDao stationDao){
        List<Line> lines = lineDao.findAll();
        List<Station> allStations = stationDao.findAll();

        List<Section> allSections = lines.stream()
                .flatMap(line -> line.getSections().getSections().stream())
                .distinct()
                .collect(Collectors.toList());

        return new SimpleSubwayMap(
                new SimplePathFinder(allStations, allSections),
                LineExtraFareTable.of(lines));
    }

}

