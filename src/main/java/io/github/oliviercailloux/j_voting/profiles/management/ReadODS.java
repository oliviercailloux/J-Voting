package io.github.oliviercailloux.j_voting.profiles.management;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * Read .ODS file & extract elections data. The .ODS file must be Election Data
 * Format. For more information on accepted formats, refer to the
 * Doc/votingFilesFormatsDoc.adoc file
 */
public class ReadODS {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(ReadODS.class.getName());

    /**
     * Function defining which type of file formatting is used
     * 
     * @param inputStream : ods file
     * @return : A string to display the characteristics of the file send
     */
    public static String checkFormatandPrint(InputStream inputStream)
                    throws Exception {
        if (Objects.equals(inputStream, null))
            throw new IllegalArgumentException("InputStream can't be null");
        LOGGER.debug("Open Stream");
        LOGGER.debug(inputStream.toString());
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream);
        LOGGER.debug("Open Spreadsheet");
        Table table = spreadsheetDoc.getSheetByIndex(0);
        LOGGER.debug("Get sheet index 0 done");
        LOGGER.debug("Checking format");
        if (table.getCellByPosition(1, 0).getStringValue().equals(""))
            return printFormatLikeSOC(table);
        else if (table.getCellByPosition(0, 0).getStringValue().equals(""))
            return printFormatWithEqualsPref(table);
        return printFormatWithoutEqualsPref(table);
    }

    /**
     * Function returning a string containing a formatting of the voting data
     * contained in a table in a format similar to SOC
     * 
     * @param table : an ods table containing voting information
     * @return : A string to display the characteristics of the table send
     */
    public static String printFormatLikeSOC(Table table) {
        if (Objects.equals(table, null))
            throw new IllegalArgumentException("table can't be null");
        StringBuilder stringBuilder = new StringBuilder();
        int nbAlternatives = Integer.parseInt(
                        table.getCellByPosition(0, 0).getStringValue());
        List<Alternative> alternatives = new ArrayList<>(nbAlternatives);
        for (int i = 1; i <= nbAlternatives; i++)
            alternatives.add(new Alternative(Integer.valueOf(
                            table.getCellByPosition(0, i).getStringValue())));
        stringBuilder.append("There are " + nbAlternatives + " alternatives\n"
                        + "List of alternatives : " + alternatives + "\n");
        int nbOrders = Integer
                        .parseInt(table.getCellByPosition(2, nbAlternatives + 1)
                                        .getStringValue());
        stringBuilder.append("There are " + nbOrders + " different orders\n");
        CellRange prefRange = table.getCellRangeByPosition(1,
                        nbAlternatives + 2, nbAlternatives,
                        nbOrders + nbAlternatives + 1);
        for (int i = 0; i < prefRange.getRowNumber(); i++) {
            int nbVoters = Integer.parseInt(
                            table.getCellByPosition(0, i + nbAlternatives + 2)
                                            .getStringValue());
            stringBuilder.append(nbVoters + " voters for preference " + (i + 1)
                            + " : ");
            for (int j = 0; j < prefRange.getColumnNumber(); j++) {
                stringBuilder.append(prefRange.getCellByPosition(j, i)
                                .getStringValue() + ">");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Function returning a string containing a formatting of the voting data
     * contained in a table in the format accepting ties between alternatives
     * 
     * @param table : an ods table containing voting information
     * @return : A string to display the characteristics of the table send
     */
    public static String printFormatWithEqualsPref(Table table) {
        if (Objects.equals(table, null))
            throw new IllegalArgumentException("table can't be null");
        StringBuilder stringBuilder = new StringBuilder();
        List<Alternative> alternatives = getAlternatives(table);
        stringBuilder.append("There are " + alternatives.size()
                        + " alternatives\n" + "List of alternatives : "
                        + alternatives + "\n");
        int nbTotVoters = getnbTotVoters(table);
        stringBuilder.append("There are " + nbTotVoters + " voters\n");
        CellRange prefRange = table.getCellRangeByPosition(1, 1, nbTotVoters,
                        alternatives.size());
        for (int j = 0; j < prefRange.getColumnNumber(); j++) {
            int max = Integer.parseInt(
                            prefRange.getCellByPosition(j, 0).getStringValue());
            for (int i = 0; i < prefRange.getRowNumber(); i++) {
                if (max < Integer.parseInt(prefRange.getCellByPosition(j, i)
                                .getStringValue()))
                    max = Integer.parseInt(prefRange.getCellByPosition(j, i)
                                    .getStringValue());
            }
            stringBuilder.append("Voter " + (j + 1) + " : ");
            int nbChoice = 1;
            Set<Alternative> set;
            while (nbChoice <= max) {
                set = new LinkedHashSet<>();
                for (int i = 0; i < prefRange.getRowNumber(); i++) {
                    if (nbChoice == Integer
                                    .parseInt(prefRange.getCellByPosition(j, i)
                                                    .getStringValue())) {
                        int number = Integer.parseInt(
                                        table.getCellByPosition(0, i + 1)
                                                        .getStringValue());
                        set.add(new Alternative(number));
                    }
                }
                nbChoice++;
                if (set.size() >= 2) {
                    stringBuilder.append("{");
                    for (Alternative alter : set) {
                        stringBuilder.append(alter + ",");
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1)
                                    .append("}>");
                } else {
                    for (Alternative alter : set) {
                        stringBuilder.append(alter + ">");
                    }
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Function returning a string containing a formatting of the voting data
     * contained in a table in a format that does not accept ties between
     * alternatives
     * 
     * @param table : an ods table containing voting information
     * @return : A string to display the characteristics of the table send
     */
    public static String printFormatWithoutEqualsPref(Table table) {
        if (Objects.equals(table, null))
            throw new IllegalArgumentException("table can't be null");
        StringBuilder stringBuilder = new StringBuilder();
        List<Alternative> alternatives = getAlternatives(table);
        stringBuilder.append("There are " + alternatives.size()
                        + " alternatives\n" + "List of alternatives : "
                        + alternatives + "\n");
        int nbTotVoters = getnbTotVoters(table);
        stringBuilder.append("There are " + nbTotVoters + " voters\n");
        CellRange prefRange = table.getCellRangeByPosition(0, 1,
                        nbTotVoters - 1, alternatives.size());
        for (int j = 0; j < prefRange.getColumnNumber(); j++) {
            stringBuilder.append("Voter " + (j + 1) + " : ");
            for (int i = 0; i < prefRange.getRowNumber(); i++) {
                stringBuilder.append(Integer.parseInt(prefRange
                                .getCellByPosition(j, i).getStringValue())
                                + ">");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Function returning the different alternatives of a table passed in
     * parameter
     * 
     * @param table : ods table containing voting informations (looklike SOC
     *              format excluded)
     * @return : the list of alternatives
     */
    private static List<Alternative> getAlternatives(Table table) {
        List<Alternative> alternatives = new ArrayList<>();
        while (!table.getCellByPosition(0, alternatives.size() + 1)
                        .getStringValue().equals("")) {
            alternatives.add(
                            new Alternative(Integer.valueOf(table
                                            .getCellByPosition(0,
                                                            alternatives.size()
                                                                            + 1)
                                            .getStringValue())));
        }
        return alternatives;
    }

    /**
     * Function returning the number of Voters of a table passed in parameter
     * 
     * @param table : ods table containing voting informations (looklike SOC
     *              format excluded)
     * @return : the number of Voters
     */
    private static int getnbTotVoters(Table table) {
        int nbTotVoters = 0;
        int start = (table.getCellByPosition(0, 0).getStringValue().equals(""))
                        ? 1
                        : 0;
        while (!table.getCellByPosition(nbTotVoters + start, 0).getStringValue()
                        .equals(""))
            nbTotVoters++;
        return nbTotVoters;
    }
}
