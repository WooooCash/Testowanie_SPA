package ats.v1.spa_frontend.token;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenValueTest {

    @ParameterizedTest
    @MethodSource("getCorrectValues")
    void shouldProvideCorrectValue(final String param, final TokenType expectedValue) {
        TokenValue tokenValue = new TokenValue();
        assertThat(tokenValue.getTokenType(param).equals(expectedValue));
    }

    @ParameterizedTest
    @MethodSource("getIncorrectValues")
    void shouldNotProvideValues(final String param) {
        TokenValue tokenValue = new TokenValue();
        assertThat(tokenValue.getTokenType(param)).isNull();
    }

    private static Stream<Arguments> getCorrectValues() {
        return Stream.of(
                Arguments.of("+", TokenType.PLUS),
                Arguments.of(";", TokenType.SEMICOLON),
                Arguments.of("/", TokenType.SLASH)
        );
    }

    private static Stream<Arguments> getIncorrectValues() {
        return Stream.of(
                Arguments.of("."),
                Arguments.of("@"),
                Arguments.of("!"),
                Arguments.of("&")
        );
    }

}