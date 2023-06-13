package ats.v1.common;

import java.util.List;

public interface Parser<TYPE> {

    List<TYPE> parse(String declare, String query);

}
