package ats.v1.query.processor;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class QueryNode {

    private final String name;

    private final QueryNode parent;

    private final List<QueryNode> children;

    public QueryNode(){
        this.name = null;
        this.parent = null;
        this.children = null;
    }
    public QueryNode(final String name) {
        this.name = name;
        this.parent = null;
        this.children = null;
    }

    public QueryNode(final String name, final QueryNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = null;
    }

    public QueryNode(final String name, final List<QueryNode> children) {
        this.name = name;
        this.parent = null;
        this.children = children;
    }
}
