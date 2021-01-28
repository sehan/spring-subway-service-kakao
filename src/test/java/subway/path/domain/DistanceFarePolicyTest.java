package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceFarePolicyTest {

    @DisplayName("10Km 이내는 기본운임 입니다")
    @Test
    void defaultFare(){
        DistanceFarePolicy policy = new DistanceFarePolicy();
        Fare fare = Fare.of(0);
        policy.apply(fare, Path.of(Arrays.asList(), 10), Age.adult());

        assertThat(fare.getValue()).isEqualTo(1250);
    }

    @DisplayName("이용 거리 초과 시 추가운임 부과합니다 ( 11km~50km 까지 5km 마다 100원 )")
    @ParameterizedTest
    @ValueSource(ints = {11,12,13,14,15})
    void middleRange(int distance){
        DistanceFarePolicy policy = new DistanceFarePolicy();
        Fare fare = Fare.of(0);
        policy.apply(fare, Path.of(Arrays.asList(), distance), Age.adult());

        assertThat(fare.getValue()).isEqualTo(1250 + 100);
    }

    @DisplayName("이용 거리 초과 시 추가운임 부과합니다 ( 11km~50km 까지 5km 마다 100원, 기본운임거리보다 6km~ 이상 초과한 경우 )")
    @ParameterizedTest
    @ValueSource(ints = {16,17,18,19,20})
    void middleRange2(int distance){
        DistanceFarePolicy policy = new DistanceFarePolicy();
        Fare fare = Fare.of(0);
        policy.apply(fare, Path.of(Arrays.asList(), distance), Age.adult());

        assertThat(fare.getValue()).isEqualTo(1250 + 100*2);
    }


    @DisplayName("이용 거리 초과 시 추가운임 부과합니다 ( 50km 초과 시 8km 마다 100원 )")
    @ParameterizedTest
    @ValueSource(ints = {51,52,53,54,55,56,57})
    void longRange(int distance){
        DistanceFarePolicy policy = new DistanceFarePolicy();
        Fare fare = Fare.of(0);
        policy.apply(fare, Path.of(Arrays.asList(), distance), Age.adult());

        assertThat(fare.getValue()).isEqualTo(1250 + 100*8 + 100);
    }

}