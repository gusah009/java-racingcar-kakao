package racingcar;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RacingCarModelTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ParseNames {
        @ParameterizedTest
        @MethodSource
        void should_parseNames_when_validNameLine(String nameLine, String[] names) {
            List<String> result = RacingCarModel.parseNames(nameLine);
            assertThat(result).containsExactly(names);
        }

        Stream<Arguments> should_parseNames_when_validNameLine() {
            return Stream.of(
                    Arguments.of(",abc", new String[]{"", "abc"}),
                    Arguments.of("abc, def, geh", new String[]{"abc", " def", " geh"})
            );
        }

        @Test
        void should_throwException_when_tooManyNames() {
            String nameLine = "abc,".repeat(100) + "abc";
            assertThatThrownBy(() -> RacingCarModel.parseNames(nameLine))
                    .isInstanceOf(RuntimeException.class);
        }
    }


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class from {

        @ParameterizedTest
        @MethodSource
        void should_throwException_when_givenCars(List<Car> cars) {
            assertThatThrownBy(() -> RacingCarModel.from(cars))
                    .isInstanceOf(RuntimeException.class);
        }

        Stream<Arguments> should_throwException_when_givenCars() {
            Engine engine = Engine.defaultEngine;
            return Stream.of(
                    Arguments.of((Object) null),
                    Arguments.of(List.of()),
                    Arguments.of(
                            List.of(Car.from("abc", engine),
                                    Car.from("abc", engine))
                    ));
        }

        @Test
        void should_returnRacingCarModel_when_givenValidCars() {
            Engine engine = Engine.defaultEngine;
            List<Car> cars = List.of(
                    Car.from("abc", engine),
                    Car.from("abcd", engine)
            );
            RacingCarModel model = RacingCarModel.from(cars);
            assertThat(model).isNotNull();
        }
    }
}