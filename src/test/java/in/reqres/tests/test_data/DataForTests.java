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
}
