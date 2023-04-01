package ats.v1.spa_frontend.scanner;

import ats.v1.spa_frontend.token.Token;
import ats.v1.spa_frontend.token.TokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerTest {

    @Test
    void testScanProgramWithCorrectCharacters() {
        String program = "procedure {} myVar = 4";
        TokenType[] expectedTokens = {
                TokenType.PROCEDURE,
                TokenType.LEFT_BRACE,
                TokenType.RIGHT_BRACE,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.NUMBER,
                TokenType.EOF,
        };

        Scanner s = new Scanner(program);
        List<Token> scannedTokens = s.scanTokens();
        assertEquals(expectedTokens.length, scannedTokens.size());
        for (int i = 0; i < scannedTokens.size(); i++)
            assertEquals(expectedTokens[i], scannedTokens.get(i).getType());
    }

    @Test
    void testScanProgramWithUnrecognizedTokens() {
        String program = "% [ & ^";
        Scanner s = new Scanner(program);
        List<Token> scannedTokens = s.scanTokens();

        assertEquals(1, scannedTokens.size()); // EOF token will always be added at the end
    }

}