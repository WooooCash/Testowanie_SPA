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

    private final List<String> statementTypes = Arrays.asList("stmt", "assign", "while", "if", "call");
    private final List<String> stringTypes = Arrays.asList("procedure", "variable");

    private final QueryResolver resolver = new QueryResolver();

    public String evaluate(final Pkb pkb, final Query query) {
        String answerType = "ints";
        List<Integer> listResult = null;
        List<String> strListResult = null;
        boolean boolResult = true;
        String resultType = query.getResult().get(0).getNodeType();

        if (query.getSuchThat().isEmpty()) {
            if (statementTypes.contains(resultType)) {
                listResult = pkb.getStatements(resultType);
                answerType = "ints";
            } else if (stringTypes.contains(resultType)) {
                strListResult = pkb.getStringTypes(resultType);
                answerType = "strings";
            }
        }
        for (QueryNode qn : query.getSuchThat()) {
            String type1 = qn.getParam1().getNodeType();
            String type2 = qn.getParam2().getNodeType();
            if (qn.getName().equals("modifies")) {
                if (type1.equals("number") && type2.equals("lexeme")) {
                    boolean doesModify = pkb.modifies(Integer.parseInt(qn.getParam1().getName()), qn.getParam2().getName());
                    boolResult = boolResult && doesModify;
                    answerType = "bool";
                } else if (type2.equals("lexeme")) {
                    List<Integer> statements = pkb.modifies(qn.getParam2().getName(), type1);
                    listResult = intersection(listResult, statements);
                    answerType = "ints";
                    if (type1.equals("procedure")) {
                        List<String> names = pkb.getProcedureNamesByLines(listResult);
                        strListResult = intersection(strListResult, names);
                        answerType = "strings";
                    }
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
                    List<Integer> statements = pkb.uses(qn.getParam2().getName(), type1);
                    listResult = intersection(listResult, statements);
                    answerType = "ints";
                    if (type1.equals("procedure")) {
                        List<String> names = pkb.getProcedureNamesByLines(listResult);
                        strListResult = intersection(strListResult, names);
                        answerType = "strings";
                    }
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
                    int after = pkb.follows_after(Integer.parseInt(qn.getParam1().getName()), type2);
                    if (after != -1) {
                        listResult = intersection(listResult, Arrays.asList(after));
                    }
                    answerType = "ints";
                } else if (statementTypes.contains(type1) && type2.equals("number")) {
                    int before = pkb.follows_before(Integer.parseInt(qn.getParam2().getName()), type1);
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
//        if (statementTypes.contains(resultType)) {
//            return resolver.resolve(listResult);
//        } else if (stringTypes.contains(resultType)) {
//            return resolver.resolveString(strListResult);
//        } else {
//            return resolver.resolve(boolResult);
//        }
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
