package ats.v1.query.processor;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class QueryWithNode {

    private String firstParamName = "";

    private String firstParamType;

    private String firstParamArgument;

    private boolean firstParamHash = false;

    private String secondParamName;

    private String secondParamType;

    private String secondParamArgument;

    private Integer secondParamValue;

    private boolean secondParamHash = false;

}
