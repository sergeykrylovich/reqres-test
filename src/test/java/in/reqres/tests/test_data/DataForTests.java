package in.reqres.tests.test_data;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataForTests {

    static Stream<Arguments> positiveDataForMethodSource() {
        return Stream.of(
                Arguments.of(1,1),
                Arguments.of(1,11),
                Arguments.of(12,1),
                Arguments.of(2,6)
            );

    }
    static Stream<Arguments> negativeDataForMethodSource() {
        return Stream.of(
                Arguments.of(-2,12),
                Arguments.of(-1,0),
                Arguments.of(-2,6)
            );

    }
    static Stream<Arguments> positiveDataForUpdateSingleUser() {
        return Stream.of(
                Arguments.of(500, "morpheus", "zion resident"),
                Arguments.of(501, "Neo", "Fight master"),
                Arguments.of(700, "Trinity", "What a woman")
            );

    }
    static Stream<Arguments> positiveDataForCreateSingleUser() {
        return Stream.of(
                Arguments.of( "morpheus", "zion resident"),
                Arguments.of( "Neo", "Fight master"),
                Arguments.of("Trinity", "What a woman")
            );

    }
}
