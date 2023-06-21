package ats.v1.query.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QueryToken {

    final QueryTokenValue type;
    final String lexeme;
    final int value;
}
