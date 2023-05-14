package ats.v1.query.compositor;

import ats.v1.query.QueryTestUtils;
import ats.v1.query.processor.Query;
import ats.v1.query.token.QueryTokenType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryCompositorTest {

    @Test
    void shouldCreateSelectCorrectly() {
        QueryCompositor queryCompositor = new QueryCompositor();
        Query query = queryCompositor.composite(QueryTestUtils.getTokens());
        assertThat(query.getResult()).isNotNull();
        assertThat(query.getResult().getName()).isEqualTo("a");
    }

    @Test
    void shouldCreateSuchThatCorrectly() {
        QueryCompositor compositor = new QueryCompositor();
        Query query = compositor.composite(QueryTestUtils.getTokens());
        assertThat(query.getSuchThat()).isNotNull();
        assertThat(query.getSuchThat().getName()).isEqualTo(QueryTokenType.MODIFIES.name());
        assertThat(query.getSuchThat().getChildren()).hasSize(2);
        assertThat(query.getSuchThat().getChildren().get(0).getName()).isNotNull().isEqualTo("a");
        assertThat(query.getSuchThat().getChildren().get(1).getName()).isNotNull().isEqualTo("v");
    }

}