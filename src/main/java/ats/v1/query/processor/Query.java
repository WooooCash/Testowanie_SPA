package ats.v1.query.processor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Slf4j
public class Query {

    private final List<QueryTree> queryTrees = new ArrayList<>();

    public void addQueryTree(final QueryTree queryTree) {
        this.queryTrees.add(queryTree);
        log.debug("QueryTree added");
    }

}
