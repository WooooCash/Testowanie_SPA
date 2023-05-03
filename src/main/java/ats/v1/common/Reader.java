package ats.v1.common;

import java.util.List;

public interface Reader<TYPE> {

    List<TYPE> parse(String query);

}
