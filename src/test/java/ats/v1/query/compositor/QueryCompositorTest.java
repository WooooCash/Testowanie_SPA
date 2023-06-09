package ats.v1.query.compositor;

import ats.v1.query.QueryTestUtils;
import ats.v1.query.processor.Query;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class QueryCompositorTest {

    @Test
    void shouldCreateDeclarationsCorrectly() {
        QueryCompositor queryCompositor = new QueryCompositor();
        assertDoesNotThrow(() -> queryCompositor.composite(QueryTestUtils.getDeclarationTokens()));
    }

    @Test
    void shouldCreateResultCorrectly() {
        QueryCompositor compositor = new QueryCompositor();
        Query query = compositor.composite(QueryTestUtils.getWKurweMocneTokens());
        assertThat(query.getResult()).isNotNull();
        assertThat(query.getResult().get(0).getName()).isEqualTo("a1");
        assertThat(query.getResult().get(0).getNodeType()).isEqualTo("assign");
    }

    @Test
    void shouldCreateSuchThatCorrectly() {
        QueryCompositor compositor = new QueryCompositor();
        Query query = compositor.composite(QueryTestUtils.getWKurweMocneTokens());
        assertThat(query.getSuchThat()).isNotNull();
        assertThat(query.getSuchThat()).hasSize(3);
        assertThat(query.getSuchThat().get(0).getName()).isEqualTo("affects");
        assertThat(query.getSuchThat().get(1).getParam1().getName()).isEqualTo("w2");
    }

    @Test
    void shouldCreateWithCorrectly() {
        QueryCompositor compositor = new QueryCompositor();
        Query query = compositor.composite(QueryTestUtils.getTokens());
        assertThat(query.getWith()).isNotNull().hasSize(3);
    }

}