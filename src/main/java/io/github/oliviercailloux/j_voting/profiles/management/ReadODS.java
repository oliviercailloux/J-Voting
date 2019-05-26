package io.github.oliviercailloux.j_voting.profiles.management;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * Read .ODS file & extract elections data. The .ODS file must be Election Data
 * Format.
 *
 */
public class ReadODS {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(ReadODS.class.getName());

    public static void main(String[] args) throws Exception {
        System.out.println(checkFormat(
                        ReadODS.class.getResourceAsStream("./testods.ods")));
        System.out.println(checkFormat(
                        ReadODS.class.getResourceAsStream("./facon1.ods")));
        System.out.println(checkFormat(
                        ReadODS.class.getResourceAsStream("./facon2.ods")));
    }

    public static String checkFormat(InputStream inputStream) throws Exception {
        LOGGER.debug("Open Stream");
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream);
        LOGGER.debug("Open Spreadsheet");
        Table table = spreadsheetDoc.getSheetByIndex(0);
        LOGGER.debug("Get sheet index 0 done");
        if (table.getCellByPosition(1, 0).getStringValue().equals(""))
            return readSpreadsheetDocument(table);
        else if (table.getCellByPosition(0, 0).getStringValue().equals(""))
            return readFormat1(table);
        return readFormat2(table);
    }

    public static String readSpreadsheetDocument(Table table) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int nbAlternatives = Integer.parseInt(
                        table.getCellByPosition(0, 0).getStringValue());
        List<Alternative> alternatives = new ArrayList<>(nbAlternatives);
        for (int i = 1; i <= nbAlternatives; i++)
            alternatives.add(new Alternative(i));
        stringBuilder.append("There are " + nbAlternatives + " alternatives\n"
                        + "List of alternatives : " + alternatives + "\n");
        int nbOrders = Integer
                        .parseInt(table.getCellByPosition(2, nbAlternatives + 1)
                                        .getStringValue());
        // Set<Alternative> pref = new LinkedHashSet<>(nbCandidates);
        stringBuilder.append("There are " + nbOrders + " different orders\n");
        CellRange prefRange = table.getCellRangeByPosition(1,
                        nbAlternatives + 2, nbAlternatives,
                        nbOrders + nbAlternatives + 1);
        // List<Alternative> ordersList = new ArrayList<>(nbOrders);
        for (int i = 0; i < prefRange.getRowNumber(); i++) {
            int nbVoters = Integer.parseInt(
                            table.getCellByPosition(0, i + nbAlternatives + 2)
                                            .getStringValue());
            stringBuilder.append(nbVoters + " voters for preference " + (i + 1)
                            + " : ");
            for (int j = 0; j < prefRange.getColumnNumber(); j++) {
                // ordersList.add(alternatives.get(Integer.valueOf(prefRange
                // .getCellByPosition(j, i).getStringValue())
                // - 1));
                stringBuilder.append(prefRange.getCellByPosition(j, i)
                                .getStringValue() + ">");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("\n");
        }
        return stringBuilder.toString();
    }

    public static String readFormat1(Table table) throws Exception {
        // TODO
        StringBuilder stringBuilder = new StringBuilder();
        int nbAlternatives = getNbAlternatives(table);
        int nbTotVoters = getnbTotVoters(table, 1);
        CellRange prefRange = table.getCellRangeByPosition(1, 1, nbTotVoters,
                        nbAlternatives);
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
            Set<Alternative> set = new LinkedHashSet<>();
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

    public static String readFormat2(Table table) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int nbAlternatives = getNbAlternatives(table);
        int nbTotVoters = getnbTotVoters(table, 0);
        CellRange prefRange = table.getCellRangeByPosition(0, 1,
                        nbTotVoters - 1, nbAlternatives);
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

    private static int getNbAlternatives(Table table) {
        int nbAlternatives = 0;
        List<Alternative> alternatives = new ArrayList<>(nbAlternatives);
        while (!table.getCellByPosition(0, nbAlternatives + 1).getStringValue()
                        .equals("")) {
            nbAlternatives++;
            alternatives.add(new Alternative(nbAlternatives));
        }
        System.out.println("There are " + nbAlternatives + " alternatives\n"
                        + "List of alternatives : " + alternatives);
        return nbAlternatives;
    }

    private static int getnbTotVoters(Table table, int position) {
        int nbTotVoters = 0;
        while (!table.getCellByPosition(nbTotVoters + position, 0)
                        .getStringValue().equals(""))
            nbTotVoters++;
        System.out.println(("There are " + nbTotVoters + " voters"));
        return nbTotVoters;
    }
}