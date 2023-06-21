package ats.v1.query.compositor;

import ats.v1.query.QueryTestUtils;
import ats.v1.query.processor.Query;
import ats.v1.query.token.QueryTokenValue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryCompositorTest {

    @Disabled
    @Test
    void shouldCreateSelectCorrectly() {
        QueryCompositor queryCompositor = new QueryCompositor();
        Query query = queryCompositor.composite(QueryTestUtils.getTokens());
        assertThat(query.getResult()).isNotNull();
        assertThat(query.getResult().get(0).getName()).isEqualTo("a");
    }

    @Disabled
    @Test
    void shouldCreateSuchThatCorrectly() {
        QueryCompositor compositor = new QueryCompositor();
        Query query = compositor.composite(QueryTestUtils.getTokens());
        assertThat(query.getSuchThat()).isNotNull();
        assertThat(query.getSuchThat().get(0).getName()).isEqualTo(QueryTokenValue.MODIFIES.name());
        assertThat(query.getSuchThat().get(0).getParam1()).isNotNull().isEqualTo("a");
        assertThat(query.getSuchThat().get(0).getParam2()).isNotNull().isEqualTo("v");
    }

}