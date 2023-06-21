package ats.v1.query.evaluator;

import ats.v1.pkb.Pkb;
import ats.v1.query.processor.Query;
import ats.v1.query.processor.QueryResolver;

import java.util.List;

public class QueryEvaluator {

    private final QueryResolver resolver = new QueryResolver();

    public void evaluate(final Pkb pkb, final Query query) {
//        String name = query.getSuchThat().getName();
//        switch (name) {
//            case "modifies":
//                if (query.getSuchThat().getChildren().size() == 1) {
//                    List<String> modifies =
//                            pkb.modifies(Integer.parseInt(query.getSuchThat().getChildren().get(0).getName()));
//                    return resolver.resolveString(modifies);
//                }
//                break;
//            case "follows":
////                if (query.getSuchThat().)
//                break;
//            case "uses":
//                if(query.getSuchThat().getChildren().size() == 1) {
//
//                }
//                break;
//        }
//        return resolver.resolve(false);
    }
}
