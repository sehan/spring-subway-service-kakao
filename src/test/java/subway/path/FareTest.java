package subway.path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Lines;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.domain.AGE;
import subway.path.domain.Vertex;
import subway.path.domain.strategy.AgeFare;
import subway.path.domain.strategy.DistanceFare;
import subway.path.domain.strategy.FareStrategy;
import subway.path.domain.strategy.LineFare;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FareTest {
    FareStrategy fareStrategy;

    @DisplayName("나이 할인 요금 테스트")
    @Test
    void ageFare() {
        fareStrategy = new AgeFare(AGE.BABY);
        assertThat(fareStrategy.apply(1350)).isEqualTo(0);

        fareStrategy = new AgeFare(AGE.CHILD);
        assertThat(fareStrategy.apply(1350)).isEqualTo(850);

        fareStrategy = new AgeFare(AGE.TEENAGER);
        assertThat(fareStrategy.apply(1350)).isEqualTo(1150);

        fareStrategy = new AgeFare(AGE.ADULT);
        assertThat(fareStrategy.apply(1350)).isEqualTo(1350);

    }

    @DisplayName("라인 추가 요금 테스트")
    @Test
    void lineExtraFare() {
        List<Section> sections = Arrays.asList(
                new Section(new Station(1L, "잠실새내"), new Station(2L, "잠실"), 1)
        );

        List<Section> sections2 = Arrays.asList(
                new Section(new Station(2L, "잠실"), new Station(3L, "복정"), 1)
        );

        Lines lines = Lines.of(Arrays.asList(
                Line.of(1L, "2호선", "bg-red-600", 400, new Sections(sections)),
                Line.of(1L, "8호선", "bg-red-600", 1000, new Sections(sections2))
        ));

        List<Vertex> vertexs = lines.getVertexs(1L, 2L);
        fareStrategy = new LineFare(vertexs);
        assertThat(fareStrategy.apply(1000)).isEqualTo(1400);

        vertexs = lines.getVertexs(1L, 3L);
        fareStrategy = new LineFare(vertexs);
        assertThat(fareStrategy.apply(1000)).isEqualTo(2000);
    }

    @DisplayName("거리 추가 요금 테스트")
    @Test
    void distanceExtraFare() {
        fareStrategy = new DistanceFare(10);
        assertThat(fareStrategy.apply(0)).isEqualTo(1250);

        fareStrategy = new DistanceFare(11);
        assertThat(fareStrategy.apply(0)).isEqualTo(1350);

        fareStrategy = new DistanceFare(15);
        assertThat(fareStrategy.apply(0)).isEqualTo(1350);

        fareStrategy = new DistanceFare(50);
        assertThat(fareStrategy.apply(0)).isEqualTo(2050);

        fareStrategy = new DistanceFare(58);
        assertThat(fareStrategy.apply(0)).isEqualTo(2150);
    }
}
