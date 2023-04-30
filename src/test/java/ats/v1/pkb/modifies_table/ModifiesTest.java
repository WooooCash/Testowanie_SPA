package ats.v1.pkb.modifies_table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModifiesTest {
    ModifiesTableImpl m;

    @BeforeEach
    void setup() {
        m = new ModifiesTableImpl();
    }

    @Test
    void testGetModified() {
        int lineNr = 7;
        int[] varIndices = {0, 3, 5, 6, 7};
        for (int vi : varIndices)
            m.setModifies(lineNr, vi);

        assertArrayEquals(varIndices, m.getModified(lineNr).stream().mapToInt(Integer::intValue).toArray());
    }

    @Test
    void testGetModifies() {
        int varIdx = 7;
        int[] lineNrs = {0, 3, 5, 6, 7};
        for (int ln : lineNrs)
            m.setModifies(ln, varIdx);

        assertArrayEquals(lineNrs, m.getModifies(varIdx).stream().mapToInt(Integer::intValue).toArray());
    }

    @Test
    void testIsModified() {
        int varIdx = 1;
        int lineNr = 1;
        m.setModifies(lineNr, varIdx);
        assertTrue(m.isModified(varIdx, lineNr));
    }

    @Test
    void testGetModifiedReturnsNullWhenEmpty() {
        assertNull(m.getModified(420));
    }

    @Test
    void testIsModifiedReturnsFalseInTwoCases() {
        m.setModifies(1, 1);
        assertFalse(m.isModified(1, 2));
        assertFalse(m.isModified(10, 1));
    }

}
