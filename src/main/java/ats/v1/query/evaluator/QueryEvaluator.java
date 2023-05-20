package ats.v1.query.evaluator;

import ats.v1.pkb.PKB;
import ats.v1.query.processor.Query;

public class QueryEvaluator {

    public String evaluate(final PKB pkb, final Query query) {
        String name = query.getSuchThat().getName();
        switch (name) {
            case "Modifies":
                if (query.getSuchThat().getChildren().size() == 1) {
                    pkb.modifies(Integer.parseInt(query.getSuchThat().getChildren().get(0).getName()));
                }

                break;
        }
    return "siema";
    }
}
