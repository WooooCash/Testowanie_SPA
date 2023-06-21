package ats.v1.query.processor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class QueryNode {

    private final String name;

    private final String nodeType;

    private final String param1;

    private final String param2;

    public QueryNode(final String name) {
        this.name = name;
        this.nodeType = null;
        this.param1 = null;
        this.param2 = null;
    }

    public QueryNode(final String name, final String noteType) {
        this.name = name;
        this.param1 = null;
        this.param2 = null;
        this.nodeType = noteType;
    }

    public QueryNode(final String name, final String param1, final String param2) {
        this.name = name;
        this.param1 = param1;
        this.param2 = param2;
        this.nodeType = null;
    }

}
