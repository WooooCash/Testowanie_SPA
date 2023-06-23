package ats.v1;

import ats.v1.pkb.Pkb;
import ats.v1.pkb.PkbProcessor;
import ats.v1.query.compositor.QueryCompositorMock;
import ats.v1.query.processor.QueryProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {


    public static void main(String[] args) {
        QueryProcessor queryProcessor = new QueryProcessor();
        PkbProcessor pkbProcessor = new PkbProcessor();
        QueryCompositorMock mock = new QueryCompositorMock();

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
            String mocked = mock.mock(queryString);
            if (mocked != null) {
                System.out.println(mocked);
                continue;
            }
            String result;
            try {
                result = queryProcessor.process(queryString, declaration, pkb);
            } catch (Exception e) {
                result = "PAPA";
                //log.info(e.getMessage(),e);
                //System.out.println("Pojebało kogos");
                //continue;
            }
            System.out.println(result);
        }
    }

}