package ats.v1;

import ats.v1.pkb.Pkb;
import ats.v1.pkb.PkbProcessor;
import ats.v1.query.processor.QueryProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static final QueryProcessor queryProcessor = new QueryProcessor();
    private static final PkbProcessor pkbProcessor = new PkbProcessor();

    public static void main(String[] args) {
        log.info("Test");
        Pkb pkb;
        try {
            pkb = pkbProcessor.process(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Ready");

        java.util.Scanner sc = new java.util.Scanner(System.in);
        while (sc.hasNextLine()) {
            String declaration = sc.nextLine();
            String queryString = sc.nextLine();
            if (queryString.length() == 0) {
                break;
            }
            log.info("Start processing: " + queryString);
            String result = queryProcessor.process(queryString, declaration, pkb);
            System.out.println(result);
        }
    }

}