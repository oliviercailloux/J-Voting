package io.github.oliviercailloux.j_voting.profiles.management;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        checkFormat(ReadODS.class.getResourceAsStream("./facon1.ods"));
        checkFormat(ReadODS.class.getResourceAsStream("./facon2.ods"));
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
        stringBuilder.append("There are " + nbOrders + " differents orders\n");
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
        System.out.println("1");
        // TODO
        return "";
    }

    public static String readFormat2(Table table) throws Exception {
        // TODO
        System.out.println("2");
        return "";
    }
}
