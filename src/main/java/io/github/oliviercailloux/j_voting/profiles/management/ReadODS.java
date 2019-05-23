package io.github.oliviercailloux.j_voting.profiles.management;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * Read .ODS file & extract elections data. The .ODS file must be Election Data
 * Format.
 *
 */
public class ReadODS {

    public static void main(String[] args) throws Exception {
        readSpreadsheetDocument("testods.ods");
    }

    public static void readSpreadsheetDocument(String file) throws Exception {
        InputStream inputStream = ReadODS.class.getResourceAsStream(file);
        SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument
                        .loadDocument(inputStream);
        Table sheet = spreadsheetDoc.getSheetByIndex(0);
        int nbCandidates = Integer.parseInt(
                        sheet.getCellByPosition(0, 0).getStringValue());
        List<Alternative> alter = new ArrayList<>(nbCandidates);
        for (int i = 1; i <= nbCandidates; i++)
            alter.add(new Alternative(i));
        System.out.println(nbCandidates + " candidates");
        System.out.println("List of candidates : " + alter);
        int nbOrders = Integer
                        .parseInt(sheet.getCellByPosition(2, nbCandidates + 1)
                                        .getStringValue());
        Set<Alternative> pref = new LinkedHashSet<>(nbCandidates);
        System.out.println(nbOrders + " differents orders");
        CellRange prefRange = sheet.getCellRangeByPosition(1, nbCandidates + 2,
                        nbCandidates, nbOrders + nbCandidates + 2);
        List<Alternative> ordersList = new ArrayList<Alternative>(nbOrders);
        for (int i = 0; i < prefRange.getRowNumber() - 1; i++) {
            int nbVoters = Integer.parseInt(
                            sheet.getCellByPosition(0, i + nbCandidates + 2)
                                            .getStringValue());
            System.out.print("\n" + nbVoters + " voters for preference " + i
                            + " : ");
            for (int j = 0; j < prefRange.getColumnNumber(); j++) {
                ordersList.add(alter.get(Integer.valueOf(prefRange
                                .getCellByPosition(j, i).getStringValue())
                                - 1));
                System.out.print(prefRange.getCellByPosition(j, i)
                                .getStringValue() + "; ");
            }
        }
    }
}
