package ats.v1.query.processor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Query {
    private List<QueryNode> result = new ArrayList<>();
    private List<QueryNode> suchThat = new ArrayList<>();
    private List<QueryNode> with = new ArrayList<>();
    private List<QueryNode> pattern = new ArrayList<>();
}
