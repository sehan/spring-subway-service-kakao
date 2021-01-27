package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

abstract class PathFinderTest {

    PathFinder pathFinder;

    Station 판교역 = new Station(1L,"판교역");
    Station 수내역 = new Station(2L,"수내역");
    Station 정자역 = new Station(3L,"정자역");
    Station 기흥역 = new Station(4L,"기흥역");
    Station 광교역 = new Station(5L,"광교역");
    Station 영통역 = new Station(6L,"영통역");
    Station 서천역 = new Station(7L,"서천역");

    Section 분당선1 = new Section(수내역, 정자역, 1);
    Section 분당선2 = new Section(정자역, 기흥역, 1);
    Section 분당선3 = new Section(기흥역, 영통역, 1);
    Section 분당선4 = new Section(영통역, 서천역, 1);

    Section 신분당선1 = new Section(판교역, 정자역, 1);
    Section 신분당선2 = new Section(정자역, 광교역, 5);
    Section 신분당선3 = new Section(광교역, 영통역, 1);

    @DisplayName("최단거리경로를 찾아준다")
    @Test
    void findShortestPath() {
        Path path = pathFinder.findShortestPath(판교역, 서천역);

        assertThat(path.getDistance()).isEqualTo(4);
        assertThat(path.getStations())
                .containsExactly(판교역, 정자역, 기흥역, 영통역, 서천역);
    }

}

class DefaultPathFinderTest extends PathFinderTest {

    @BeforeEach
    void setUp() {
        pathFinder = new DefaultPathFinder(
                Arrays.asList(판교역, 수내역, 정자역, 기흥역, 광교역, 영통역, 서천역),
                Arrays.asList(분당선1, 분당선2, 분당선3, 분당선4, 신분당선1, 신분당선2, 신분당선3)
        );
    }

}