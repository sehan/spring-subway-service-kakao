package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LineAdditionFarePolicyTest {

    Station 판교역 = new Station(1L,"판교역");
    Station 수내역 = new Station(2L,"수내역");
    Station 정자역 = new Station(3L,"정자역");
    Station 기흥역 = new Station(4L,"기흥역");
    Station 광교역 = new Station(5L,"광교역");
    Station 영통역 = new Station(6L,"영통역");
    Station 서천역 = new Station(7L,"서천역");

    Line 신분당선 = new Line(1L, "신분당선", "blue", 900L, null );
    Line 분당선 = new Line(2L, "분당선", "yellow", 100L, null );
    Line 수인선 = new Line(3L, "수인선", "red", 1000L, null );

    @DisplayName("노선에 포함된 추가요금중 가장 높은 금액을 적용합니다 ( 하나의 노선만 이용한 경우 )")
    @Test
    void lineAdditionFare(){

        Map<Section, Integer> data = new HashMap<>();
        data.put(new Section(판교역, 정자역, 1), 신분당선.getExtraFare().intValue() );
        LineAdditionFarePolicy policy = new LineAdditionFarePolicy(data);

        Fare fare = Fare.of(0);
        Path path = Path.of(Arrays.asList(판교역, 정자역), 1);
        policy.apply(fare, path, Age.adult());

        assertThat(fare.getValue()).isEqualTo(신분당선.getExtraFare().intValue());
    }

    @DisplayName("노선에 포함된 추가요금중 가장 높은 금액을 적용합니다 ( 두개의 노선을 이용한 경우 )")
    @Test
    void lineAdditionFare2(){
        Map<Section, Integer> data = new HashMap<>();
        data.put(new Section(판교역, 정자역, 1), 신분당선.getExtraFare().intValue() );
        data.put(new Section(정자역, 영통역, 10), 분당선.getExtraFare().intValue() );
        LineAdditionFarePolicy policy = new LineAdditionFarePolicy(data);

        Fare fare = Fare.of(0);
        Path path = Path.of(Arrays.asList(판교역, 정자역, 영통역), 11);
        policy.apply(fare, path, Age.adult());

        assertThat(fare.getValue()).isEqualTo(신분당선.getExtraFare().intValue());
    }

    @DisplayName("노선에 포함된 추가요금중 가장 높은 금액을 적용합니다 ( 세개의 노선을 이용한 경우 )")
    @Test
    void lineAdditionFare3(){
        Map<Section, Integer> data = new HashMap<>();
        data.put(new Section(판교역, 정자역, 1), 신분당선.getExtraFare().intValue() );
        data.put(new Section(정자역, 영통역, 10), 분당선.getExtraFare().intValue() );
        data.put(new Section(영통역, 서천역, 1), 수인선.getExtraFare().intValue() );
        LineAdditionFarePolicy policy = new LineAdditionFarePolicy(data);

        Fare fare = Fare.of(0);
        Path path = Path.of(Arrays.asList(판교역, 정자역, 영통역, 서천역), 12);
        policy.apply(fare, path, Age.adult());

        assertThat(fare.getValue()).isEqualTo(수인선.getExtraFare().intValue());
    }

}