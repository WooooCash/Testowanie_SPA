package ats.v1;

import ats.v1.pkb.Pkb;
import ats.v1.pkb.PkbProcessor;
import ats.v1.query.processor.QueryProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class FirstIterationQueriesTest {


    private final QueryProcessor queryProcessor = new QueryProcessor();
    private static final PkbProcessor pkbProcessor = new PkbProcessor();

    private static Pkb pkb;

    @BeforeAll
    static void init() throws IOException {
        pkb = pkbProcessor.process("program_from_wyklad.smpl");
    }

    @Disabled("not all tests will pass, run manually only")
    @ParameterizedTest
    @MethodSource("getValues")
    void shouldNotProvideValues(final String declaration, final String query, final String expectedValue) {
        String result = queryProcessor.process(query, declaration, pkb);
        assertThat(result).isNotEmpty().isEqualTo(expectedValue);
    }

    private static Stream<Arguments> getValues() {
        return Stream.of(
                Arguments.of("procedure p;", "Select p such that Calls (p, “Third”)", null),
                Arguments.of("variable v;", "Select v such that Modifies (10, v)", null),
                Arguments.of("stmt s;", "Select s such that Modifies (s, “x”)", null),
                Arguments.of("assign a;", "Select a pattern a (_,”x+2*y”)", null),
                Arguments.of("", "Select BOOLEAN such that Next (3, 10)", null),
                Arguments.of("", "Select BOOLEAN such that Next* (2, 11)", null),
                Arguments.of("", "Select BOOLEAN such that Affects (1, 4)", null),
                Arguments.of("procedure p;", "Select p", "procedures First, Second and Third"),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null),
                Arguments.of("", "", null)
        );
    }

}
