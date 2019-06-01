package io.github.oliviercailloux.j_voting.profiles.management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

class ReadODSTest {

    private static InputStream inputStream1, inputStream2, inputStream3;
    private static String stringCompare1, stringCompare2, stringCompare3;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        stringCompare1 = "There are 3 alternatives\n"
                        + "List of alternatives : [1, 2, 3]\n"
                        + "There are 6 different orders\n"
                        + "263 voters for preference 1 : 2>1>3\n"
                        + "249 voters for preference 2 : 1>2>3\n"
                        + "78 voters for preference 3 : 1>3>2\n"
                        + "46 voters for preference 4 : 2>3>1\n"
                        + "17 voters for preference 5 : 3>1>2\n"
                        + "11 voters for preference 6 : 3>2>1\n";
        stringCompare2 = "There are 5 alternatives\n"
                        + "List of alternatives : [1, 2, 3, 4, 5]\n"
                        + "There are 6 voters\n" + "Voter 1 : 1>3>{2,4}>5\n"
                        + "Voter 2 : {1,2}>3>5>4\n" + "Voter 3 : 5>3>4>2>1\n"
                        + "Voter 4 : 5>3>4>2>1\n" + "Voter 5 : 3>5>4>{1,2}\n"
                        + "Voter 6 : {1,2,3}>4>5\n";
        stringCompare3 = "There are 5 alternatives\n"
                        + "List of alternatives : [1, 3, 2, 4, 5]\n"
                        + "There are 6 voters\n" + "Voter 1 : 1>3>2>4>5\n"
                        + "Voter 2 : 2>5>1>2>3\n" + "Voter 3 : 4>2>3>5>1\n"
                        + "Voter 4 : 5>1>2>3>4\n" + "Voter 5 : 2>3>4>5>1\n"
                        + "Voter 6 : 1>4>5>2>3\n";
    }

    @Test
    void testCheckFormatandPrint() throws Exception {
        inputStream1 = ReadODS.class
                        .getResourceAsStream("election_data_format.ods");
        inputStream2 = ReadODS.class.getResourceAsStream("rank_format.ods");
        inputStream3 = ReadODS.class
                        .getResourceAsStream("profile_format_strict.ods");
        assertEquals(stringCompare1, ReadODS.checkFormatandPrint(inputStream1));
        assertEquals(stringCompare2, ReadODS.checkFormatandPrint(inputStream2));
        assertEquals(stringCompare3, ReadODS.checkFormatandPrint(inputStream3));
    }

    @Test
    void testPrintFormatLikeSOC() throws Exception {
        inputStream1 = ReadODS.class
                        .getResourceAsStream("election_data_format.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream1);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        String stringrReadODS = ReadODS.printFormatLikeSOC(table);
        assertEquals(stringCompare1, stringrReadODS);
    }

    @Test
    void testPrintFormatWithEqualsPref() throws Exception {
        inputStream2 = ReadODS.class.getResourceAsStream("rank_format.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream2);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        String stringrReadODS = ReadODS.printFormatWithEqualsPref(table);
        assertEquals(stringCompare2, stringrReadODS);
    }

    @Test
    void testPrintFormatWithoutEqualsPref() throws Exception {
        inputStream3 = ReadODS.class
                        .getResourceAsStream("profile_format_strict.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream3);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        String stringrReadODS = ReadODS.printFormatWithoutEqualsPref(table);
        assertEquals(stringCompare3, stringrReadODS);
    }
}
