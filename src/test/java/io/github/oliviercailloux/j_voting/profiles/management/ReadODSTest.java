package io.github.oliviercailloux.j_voting.profiles.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.InputStream;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.BadFormatODSException;
import io.github.oliviercailloux.j_voting.preferences.ImmutableCompletePreference;
import io.github.oliviercailloux.j_voting.preferences.classes.ImmutableCompletePreferenceImpl;

class ReadODSTest {

    private static String stringCompare1, stringCompare2, stringCompare3;
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(ReadODSTest.class.getName());

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        stringCompare1 = "There are 3 alternatives\n"
                        + "List of alternatives : [Alternative{id=1}, Alternative{id=2}, Alternative{id=3}]\n"
                        + "There are 6 different orders\n"
                        + "263 voters for preference 1 : 2>1>3\n"
                        + "249 voters for preference 2 : 1>2>3\n"
                        + "78 voters for preference 3 : 1>3>2\n"
                        + "46 voters for preference 4 : 2>3>1\n"
                        + "17 voters for preference 5 : 3>1>2\n"
                        + "11 voters for preference 6 : 3>2>1\n";
        stringCompare2 = "There are 5 alternatives\n"
                        + "List of alternatives : [Alternative{id=1}, Alternative{id=2}, Alternative{id=3}, Alternative{id=4}, Alternative{id=5}]\n"
                        + "There are 6 voters\n" + "Voter 1 : Alternative{id=1}>Alternative{id=3}>{Alternative{id=2},Alternative{id=4}}>Alternative{id=5}\n"
                        + "Voter 2 : {Alternative{id=1},Alternative{id=2}}>Alternative{id=3}>Alternative{id=5}>Alternative{id=4}\n" + "Voter 3 : Alternative{id=5}>Alternative{id=3}>Alternative{id=4}>Alternative{id=2}>Alternative{id=1}\n"
                        + "Voter 4 : Alternative{id=5}>Alternative{id=3}>Alternative{id=4}>Alternative{id=2}>Alternative{id=1}\n" + "Voter 5 : Alternative{id=3}>Alternative{id=5}>Alternative{id=4}>{Alternative{id=1},Alternative{id=2}}\n"
                        + "Voter 6 : {Alternative{id=1},Alternative{id=2},Alternative{id=3}}>Alternative{id=4}>Alternative{id=5}\n";
        stringCompare3 = "There are 5 alternatives\n"
                        + "List of alternatives : [Alternative{id=1}, Alternative{id=3}, Alternative{id=2}, Alternative{id=4}, Alternative{id=5}]\n"
                        + "There are 6 voters\n" + "Voter 1 : 1>3>2>4>5\n"
                        + "Voter 2 : 2>5>1>4>3\n" + "Voter 3 : 4>2>3>5>1\n"
                        + "Voter 4 : 5>1>2>3>4\n" + "Voter 5 : 2>3>4>5>1\n"
                        + "Voter 6 : 1>4>5>2>3\n";
    }

    @Test
    void testCheckFormatandPrint() throws Exception {
        InputStream inputStream1 = ReadODS.class
                        .getResourceAsStream("election_data_format.ods");
        InputStream inputStream2 = ReadODS.class
                        .getResourceAsStream("rank_format.ods");
        InputStream inputStream3 = ReadODS.class
                        .getResourceAsStream("profile_format_strict.ods");
        assertEquals(stringCompare1, ReadODS.checkFormatandPrint(inputStream1));
        assertEquals(stringCompare2, ReadODS.checkFormatandPrint(inputStream2));
        assertEquals(stringCompare3, ReadODS.checkFormatandPrint(inputStream3));
    }

    @Test
    void testPrintFormatCountOfRankings() throws Exception {
        InputStream inputStream1 = ReadODS.class
                        .getResourceAsStream("election_data_format.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream1);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        String stringrReadODS = ReadODS.printFormatCountOfRankings(table);
        assertEquals(stringCompare1, stringrReadODS);
    }

    @Test
    void testPrintFormatRanksFormat() throws Exception {
        InputStream inputStream2 = ReadODS.class
                        .getResourceAsStream("rank_format.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream2);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        String stringrReadODS = ReadODS.printFormatRanksFormat(table);
        assertEquals(stringCompare2, stringrReadODS);
    }

    @Test
    void printFormatVotersToRankingsTest() throws Exception {
        InputStream inputStream3 = ReadODS.class
                        .getResourceAsStream("profile_format_strict.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream3);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        String stringrReadODS = ReadODS.printFormatVotersToRankings(table);
        assertEquals(stringCompare3, stringrReadODS);
    }

    @Test
    void completeFormatVotersToRankingsTest() throws Exception {
        InputStream inputStream = ReadODS.class
                        .getResourceAsStream("rank_format_reduced.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        ImmutableSet<ImmutableCompletePreference> completePreferences = ReadODS
                        .completeFormatRanksFormat(table);
        ImmutableSet<ImmutableCompletePreference> completePreferencesTest = ImmutableSet
                        .of(ImmutableCompletePreferenceImpl.given(
                                        Voter.withId(1),
                                        ImmutableList.of(getAlternatives(1),
                                                        getAlternatives(3),
                                                        getAlternatives(2, 4),
                                                        getAlternatives(5))),
                                        ImmutableCompletePreferenceImpl
                                                        .given(
                                                                        Voter.withId(
                                                                                        2),
                                                                        ImmutableList.of(
                                                                                        getAlternatives(2,
                                                                                                        1),
                                                                                        getAlternatives(3),
                                                                                        getAlternatives(5),
                                                                                        getAlternatives(4))),
                                        ImmutableCompletePreferenceImpl
                                                        .given(
                                                                        Voter.withId(
                                                                                        3),
                                                                        ImmutableList.of(
                                                                                        getAlternatives(5),
                                                                                        getAlternatives(3),
                                                                                        getAlternatives(4),
                                                                                        getAlternatives(2),
                                                                                        getAlternatives(1))));
        assertEquals(completePreferencesTest, completePreferences);
    }

    @Test
    void completeFormatRanksFormat() throws Exception {
        InputStream inputStream = ReadODS.class.getResourceAsStream(
                        "profile_format_strict_reduced.ods");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream);
        Table table = spreadsheetDoc.getSheetByIndex(0);
        ImmutableSet<ImmutableCompletePreference> completePreferences = ReadODS
                        .completeFormatVotersToRankings(table);
        ImmutableSet<ImmutableCompletePreference> completePreferencesTest = ImmutableSet
                        .of(ImmutableCompletePreferenceImpl.given(
                                        Voter.withId(1),
                                        ImmutableList.of(getAlternatives(1),
                                                        getAlternatives(3),
                                                        getAlternatives(2),
                                                        getAlternatives(4),
                                                        getAlternatives(5))),
                                        ImmutableCompletePreferenceImpl
                                                        .given(
                                                                        Voter.withId(
                                                                                        2),
                                                                        ImmutableList.of(
                                                                                        getAlternatives(2),
                                                                                        getAlternatives(5),
                                                                                        getAlternatives(1),
                                                                                        getAlternatives(4),
                                                                                        getAlternatives(3))),
                                        ImmutableCompletePreferenceImpl
                                                        .given(
                                                                        Voter.withId(
                                                                                        3),
                                                                        ImmutableList.of(
                                                                                        getAlternatives(4),
                                                                                        getAlternatives(2),
                                                                                        getAlternatives(3),
                                                                                        getAlternatives(5),
                                                                                        getAlternatives(1))));
        assertEquals(completePreferencesTest, completePreferences);
    }

    @Test
    void checkFormatandThrowException() throws Exception {
        InputStream inputStream = ReadODS.class
                        .getResourceAsStream("election_data_format.ods");
        assertThrows(BadFormatODSException.class, () -> ReadODS
                        .checkFormatandReturnCompletePreference(inputStream));
    }

    @Test
    void checkFormatandReturnCompletePreference() throws Exception {
        InputStream inputStream = ReadODS.class.getResourceAsStream(
                        "profile_format_strict_reduced.ods");
        ImmutableSet<ImmutableCompletePreference> completePreferences = ReadODS
                        .checkFormatandReturnCompletePreference(inputStream);
        ImmutableSet<ImmutableCompletePreference> completePreferencesTest = ImmutableSet
                        .of(ImmutableCompletePreferenceImpl.given(
                                        Voter.withId(1),
                                        ImmutableList.of(getAlternatives(1),
                                                        getAlternatives(3),
                                                        getAlternatives(2),
                                                        getAlternatives(4),
                                                        getAlternatives(5))),
                                        ImmutableCompletePreferenceImpl
                                                        .given(
                                                                        Voter.withId(
                                                                                        2),
                                                                        ImmutableList.of(
                                                                                        getAlternatives(2),
                                                                                        getAlternatives(5),
                                                                                        getAlternatives(1),
                                                                                        getAlternatives(4),
                                                                                        getAlternatives(3))),
                                        ImmutableCompletePreferenceImpl
                                                        .given(
                                                                        Voter.withId(
                                                                                        3),
                                                                        ImmutableList.of(
                                                                                        getAlternatives(4),
                                                                                        getAlternatives(2),
                                                                                        getAlternatives(3),
                                                                                        getAlternatives(5),
                                                                                        getAlternatives(1))));
        assertEquals(completePreferencesTest, completePreferences);
        inputStream = ReadODS.class
                        .getResourceAsStream("rank_format_reduced.ods");
        completePreferences = ReadODS
                        .checkFormatandReturnCompletePreference(inputStream);
        completePreferencesTest = ImmutableSet.of(
                        ImmutableCompletePreferenceImpl.given(
                                        Voter.withId(1),
                                        ImmutableList.of(getAlternatives(1),
                                                        getAlternatives(3),
                                                        getAlternatives(2, 4),
                                                        getAlternatives(5))),
                        ImmutableCompletePreferenceImpl.given(
                                        Voter.withId(2),
                                        ImmutableList.of(ImmutableSet.of(
                                                        Alternative.withId(2),
                                                        Alternative.withId(1)),
                                                        getAlternatives(3),
                                                        getAlternatives(5),
                                                        getAlternatives(4))),
                        ImmutableCompletePreferenceImpl.given(
                                        Voter.withId(3),
                                        ImmutableList.of(getAlternatives(5),
                                                        getAlternatives(3),
                                                        getAlternatives(4),
                                                        getAlternatives(2),
                                                        getAlternatives(1))));
        assertEquals(completePreferencesTest, completePreferences);
    }

    private static ImmutableSet<Alternative> getAlternatives(int... ids) {
        Set<Alternative> returnedSet = Sets.newHashSet();
        for (int id : ids) {
            returnedSet.add(Alternative.withId(id));
        }
        return ImmutableSet.copyOf(returnedSet);
    }
}
