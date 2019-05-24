package io.github.oliviercailloux.j_voting.profiles.management;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

import com.google.common.base.Objects;

class ReadODSTest {

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void testMain() {
        fail("Not yet implemented");
    }

    @Test
    void testCheckFormat() {
        fail("Not yet implemented");
    }

    @Test
    void testReadSpreadsheetDocument() throws Exception {
        InputStream inputStream = ReadODS.class
                        .getResourceAsStream("./testods.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        StringBuilder stringBuilderReadODS = ReadODS
                        .readSpreadsheetDocument(table);
        StringBuilder stringBuilder = new StringBuilder(
                        "There are 3 alternatives\n"
                                        + "List of alternatives : [1, 2, 3]\n"
                                        + "There are 6 differents orders\n"
                                        + "263 voters for preference 1 : 2>1>3\n"
                                        + "249 voters for preference 2 : 1>2>3\n"
                                        + "78 voters for preference 3 : 1>3>2\n"
                                        + "46 voters for preference 4 : 2>3>1\n"
                                        + "17 voters for preference 5 : 3>1>2\n"
                                        + "11 voters for preference 6 : 3>2>1"
                                        + "\n");
        System.out.println(stringBuilder);
        assertTrue(Objects.equal(stringBuilder.toString(),
                        stringBuilderReadODS.toString()));
    }

    @Test
    void testReadFormat1() {
        fail("Not yet implemented");
    }

    @Test
    void testReadFormat2() {
        fail("Not yet implemented");
    }
}
