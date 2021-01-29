package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class AgeFarePolicyTest {

    @DisplayName("연령별 요금할인 적용 ( 6세 미만 )")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    void infants(int age) {
        AgeFarePolicy policy = new AgeFarePolicy();
        Fare fare = Fare.of(1250);
        policy.apply(fare, null, Age.of(age));

        assertThat(fare.getValue()).isEqualTo(0);
    }

    @DisplayName("연령별 요금할인 적용 ( 6세 이상 13세 미만 )")
    @ParameterizedTest
    @ValueSource(ints = {6,7,8,9,10,11,12})
    void child(int age) {
        AgeFarePolicy policy = new AgeFarePolicy();
        Fare fare = Fare.of(1250);
        policy.apply(fare, null, Age.of(age));

        assertThat(fare.getValue()).isEqualTo(800);
    }

    @DisplayName("연령별 요금할인 적용 ( 13세 이상 19세 미만 )")
    @ParameterizedTest
    @ValueSource(ints = {13,14,15,16,17,18})
    void teenager(int age) {
        AgeFarePolicy policy = new AgeFarePolicy();
        Fare fare = Fare.of(1250);
        policy.apply(fare, null, Age.of(age));

        assertThat(fare.getValue()).isEqualTo(1070);
    }

    @DisplayName("연령별 요금할인 적용 ( 19세 이상 )")
    @ParameterizedTest
    @ValueSource(ints = {19, 30, 40, 50, 100})
    void adult(int age) {
        AgeFarePolicy policy = new AgeFarePolicy();
        Fare fare = Fare.of(1250);
        policy.apply(fare, null, Age.of(age));

        assertThat(fare.getValue()).isEqualTo(1250);
    }
}