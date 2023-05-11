package ats.v1.query.processor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QueryTree {

    private final QueryNode root;

    private final QueryNode result;

    private final QueryNode suchThat;

    private final QueryNode with;

    private final QueryNode pattern;
}
