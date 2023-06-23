package ats.v1.query.evaluator;

import ats.v1.pkb.Pkb;
import ats.v1.query.processor.Query;
import ats.v1.query.processor.QueryNode;
import ats.v1.query.processor.QueryResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class QueryEvaluator {

    private final List<String> statementTypes = Arrays.asList("stmt", "assign", "while", "procedure", "if", "call", "variable");

    private final QueryResolver resolver = new QueryResolver();

    public String evaluate(final Pkb pkb, final Query query) {
        String answerType = "list";
        List<Integer> listResult = null;
        List<String> strListResult = null;
        boolean boolResult = true;
        for (QueryNode qn : query.getSuchThat()) {
            String type1 = qn.getParam1().getNodeType();
            String type2 = qn.getParam2().getNodeType();
            if (qn.getName().equals("modifies")) {
                if (type1.equals("number") && type2.equals("lexeme")) {
                    boolean doesModify = pkb.modifies(Integer.parseInt(qn.getParam1().getName()), qn.getParam2().getName());
                    boolResult = boolResult && doesModify;
                    answerType = "bool";
                } else if (type2.equals("lexeme")) {
                    List<Integer> statements = pkb.modifies(qn.getParam2().getName(), qn.getParam1().getNodeType());
                    listResult = intersection(listResult, statements);
                    answerType = "ints";
                } else {
                    List<String> vars = pkb.modifies(Integer.parseInt(qn.getParam1().getName()));
                    strListResult = intersection(strListResult, vars);
                    answerType = "strings";
                }
            } else if (qn.getName().equals("uses")){
                if (type1.equals("number") && type2.equals("lexeme")) {
                    boolean doesUse = pkb.uses(Integer.parseInt(qn.getParam1().getName()), qn.getParam2().getName());
                    boolResult = boolResult && doesUse;
                    answerType = "bool";
                } else if (type2.equals("lexeme")) {
                    List<Integer> statements = pkb.uses(qn.getParam2().getName(), qn.getParam1().getNodeType());
                    listResult = intersection(listResult, statements);
                } else {
                    List<String> vars = pkb.uses(Integer.parseInt(qn.getParam1().getName()));
                    strListResult = intersection(strListResult, vars);
                    answerType = "strings";
                }
            } else if (qn.getName().equals("follows")) {
                if (type1.equals("number") && type2.equals("number")) {
                    boolean doesFollow = pkb.follows(Integer.parseInt(qn.getParam1().getName()), Integer.parseInt(qn.getParam2().getName()));
                    boolResult = boolResult && doesFollow;
                    answerType = "bool";
                } else if (type1.equals("number") && statementTypes.contains(type2)) {
                    int after = pkb.follows_after(Integer.parseInt(qn.getParam1().getName()), qn.getParam2().getNodeType());
                    if (after != -1) {
                        listResult = intersection(listResult, Arrays.asList(after));
                    }
                    answerType = "ints";
                } else if (statementTypes.contains(type1) && type2.equals("number")) {
                    int before = pkb.follows_before(Integer.parseInt(qn.getParam2().getName()), qn.getParam1().getNodeType());
                    if (before != -1) {
                        listResult = intersection(listResult, Arrays.asList(before));
                    }
                    answerType = "ints";
                }
            }
        }
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
        if (answerType.equals("bool")){
            return resolver.resolve(boolResult);
        } else if (answerType.equals("ints")){
            return resolver.resolve(listResult);
        } else {
            return resolver.resolveString(strListResult);
        }
    }

    private <T> List<T> intersection(List<T> list1, List<T> list2) {
        if (list1 == null) return list2;

        List<T> list = new ArrayList<T>();
        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }
}
