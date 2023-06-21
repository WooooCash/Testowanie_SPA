package ats.v1.query.processor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Query {
    private List<QueryNode> root;
    private List<QueryNode> result;
    private List<QueryNode> suchThat;
    private List<QueryNode> with;
    private List<QueryNode> pattern;
}
