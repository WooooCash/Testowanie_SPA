package ats.v1.query.processor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

class QueryResolverTest {

    QueryResolver resolver = new QueryResolver();

    @Test
    void shouldResolveBooleanCorrectly() {
        assertThat(resolver.resolve(true)).isEqualTo("TRUE");
        assertThat(resolver.resolve(false)).isEqualTo("FALSE");
    }

    @Test
    void shouldResolveIntegerCorrectly() {
        assertThat(resolver.resolve(4)).isEqualTo("4");
    }

    @Test
    void shouldResolveIntegerListCorrectly() {
        List<Integer> integerList = List.of(1, 3, 5, 7, 9);
        assertThat(resolver.resolve(integerList)).isEqualTo("1, 3, 5, 7, 9");
    }

    @Test
    void shouldResolveStringListCorrectly() {
        List<String> strings = List.of("jeden", "dwa", "trzy", "cztery", "piec");
        assertThat(resolver.resolveString(strings)).isEqualTo("jeden, dwa, trzy, cztery, piec");
    }

}