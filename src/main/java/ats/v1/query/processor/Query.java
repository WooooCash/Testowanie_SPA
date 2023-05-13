package ats.v1.query.processor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Query {

    private QueryNode root;

    private QueryNode result;

    private QueryNode suchThat;

    private QueryNode with;

    private QueryNode pattern;

    public Query() {
        this.pattern = null;
        this.root = null;
        this.result = null;
        this.suchThat = null;
        this.with = null;
    }
}
