package io.github.oliviercailloux.j_voting.profiles.management;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.BadFormatODSException;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.classes.CompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.CompletePreference;

/**
 * Read .ODS file & extract elections data. The .ODS file must be Election Data
 * Format. For more information on accepted formats, refer to the
 * Doc/votingFilesFormatsDoc.adoc file
 */
public class ReadODS {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadODS.class.getName());

	/**
	 * Private constructor because of the static class
	 */
	private ReadODS() {
		throw new IllegalStateException("Utility Class");
	}

	/**
	 * Function defining which type of file formatting is used
	 * 
	 * @param inputStream ods file
	 * @return a string to display the characteristics of the file send
	 */
	public static String checkFormatandPrint(InputStream inputStream) throws Exception {
		Objects.requireNonNull(inputStream);
		LOGGER.debug("Open Stream");
		SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument.loadDocument(inputStream);
		LOGGER.debug("Open Spreadsheet");
		Table table = spreadsheetDoc.getSheetByIndex(0);
		LOGGER.debug("Get sheet index 0 done");
		LOGGER.debug("Checking format");
		if (table.getCellByPosition(1, 0).getStringValue().equals(""))
			return printFormatCountOfRankings(table);
		else if (table.getCellByPosition(0, 0).getStringValue().equals(""))
			return printFormatRanksFormat(table);
		return printFormatVotersToRankings(table);
	}

	/**
	 * Function returning a string containing a formatting of the voting data
	 * contained in a table in a format similar to SOC
	 * 
	 * @param table an ods table containing voting information
	 * @return a string to display the characteristics of the table send
	 */
	public static String printFormatCountOfRankings(Table table) {
		Objects.requireNonNull(table);
		StringBuilder stringBuilder = new StringBuilder();
		int nbAlternatives = Integer.parseInt(table.getCellByPosition(0, 0).getStringValue());
		List<Alternative> alternatives = new ArrayList<>(nbAlternatives);
		for (int i = 1; i <= nbAlternatives; i++)
			alternatives.add(Alternative.withId(Integer.valueOf(table.getCellByPosition(0, i).getStringValue())));
		stringBuilder.append(
				"There are " + nbAlternatives + " alternatives\n" + "List of alternatives : " + alternatives + "\n");
		int nbOrders = Integer.parseInt(table.getCellByPosition(2, nbAlternatives + 1).getStringValue());
		stringBuilder.append("There are " + nbOrders + " different orders\n");
		CellRange prefRange = table.getCellRangeByPosition(1, nbAlternatives + 2, nbAlternatives,
				nbOrders + nbAlternatives + 1);
		for (int i = 0; i < prefRange.getRowNumber(); i++) {
			int nbVoters = Integer.parseInt(table.getCellByPosition(0, i + nbAlternatives + 2).getStringValue());
			stringBuilder.append(nbVoters + " voters for preference " + (i + 1) + " : ");
			for (int j = 0; j < prefRange.getColumnNumber(); j++) {
				stringBuilder.append(prefRange.getCellByPosition(j, i).getStringValue() + ">");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * Function returning a string containing a formatting of the voting data
	 * contained in a table in the format accepting ties between alternatives
	 * 
	 * @param table an ods table containing voting information
	 * @return a string to display the characteristics of the table send
	 */
	public static String printFormatRanksFormat(Table table) {
		Objects.requireNonNull(table);
		StringBuilder stringBuilder = new StringBuilder();
		List<Alternative> alternatives = getAlternatives(table);
		stringBuilder.append("There are " + alternatives.size() + " alternatives\n" + "List of alternatives : "
				+ alternatives + "\n");
		int nbTotVoters = getnbTotVoters(table);
		stringBuilder.append("There are " + nbTotVoters + " voters\n");
		CellRange prefRange = table.getCellRangeByPosition(1, 1, nbTotVoters, alternatives.size());
		for (int j = 0; j < prefRange.getColumnNumber(); j++) {
			Voter voter = Voter.createVoter(Integer.parseInt(table.getCellByPosition(j + 1, 0).getStringValue()));
			stringBuilder.append("Voter " + voter.getId() + " : ");
			int nbChoice = 1;
			while (nbChoice <= alternatives.size()) {
				Set<Alternative> set = Sets.newLinkedHashSet();
				for (int i = 0; i < prefRange.getRowNumber(); i++) {
					if (nbChoice == Integer.parseInt(prefRange.getCellByPosition(j, i).getStringValue())) {
						int number = Integer.parseInt(table.getCellByPosition(0, i + 1).getStringValue());
						set.add(Alternative.withId(number));
					}
				}
				nbChoice++;
				if (set.size() >= 2) {
					stringBuilder.append("{");
					for (Alternative alter : set) {
						stringBuilder.append(alter + ",");
					}
					stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("}>");
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
	 * @param table an ods table containing voting information
	 * @return A string to display the characteristics of the table send
	 */
	public static String printFormatVotersToRankings(Table table) {
		Objects.requireNonNull(table);
		StringBuilder stringBuilder = new StringBuilder();
		List<Alternative> alternatives = getAlternatives(table);
		stringBuilder.append("There are " + alternatives.size() + " alternatives\n" + "List of alternatives : "
				+ alternatives + "\n");
		int nbTotVoters = getnbTotVoters(table);
		stringBuilder.append("There are " + nbTotVoters + " voters\n");
		CellRange prefRange = table.getCellRangeByPosition(0, 1, nbTotVoters - 1, alternatives.size());
		for (int j = 0; j < prefRange.getColumnNumber(); j++) {
			Voter voter = Voter.createVoter(Integer.parseInt(table.getCellByPosition(j, 0).getStringValue()));
			stringBuilder.append("Voter " + voter.getId() + " : ");
			for (int i = 0; i < prefRange.getRowNumber(); i++) {
				stringBuilder.append(Integer.parseInt(prefRange.getCellByPosition(j, i).getStringValue()) + ">");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * Function returning the different alternatives of a table passed in parameter
	 * 
	 * @param table ods table containing voting informations (looklike SOC format
	 *              excluded) <code>not null</code>
	 * @return the list of alternatives
	 */
	private static List<Alternative> getAlternatives(Table table) {
		Objects.requireNonNull(table);
		List<Alternative> alternatives = new ArrayList<>();
		while (!table.getCellByPosition(0, alternatives.size() + 1).getStringValue().equals("")) {
			String valueOfCurrentCell = table.getCellByPosition(0, alternatives.size() + 1).getStringValue();
			alternatives.add(Alternative.withId(Integer.valueOf(valueOfCurrentCell)));
		}
		return alternatives;
	}

	/**
	 * Function returning the number of Voters of a table passed in parameter
	 * 
	 * @param table ods table containing voting informations (looklike SOC format
	 *              excluded) <code>not null</code>
	 * @return the number of Voters
	 */
	private static int getnbTotVoters(Table table) {
		Objects.requireNonNull(table);
		int nbTotVoters = 0;
		int start = (table.getCellByPosition(0, 0).getStringValue().equals("")) ? 1 : 0;
		while (!table.getCellByPosition(nbTotVoters + start, 0).getStringValue().equals(""))
			nbTotVoters++;
		return nbTotVoters;
	}

	/**
	 * Function defining which type of file formatting is used
	 * 
	 * @param inputStream ods file
	 * @return an ImmutableSet of CompletePreference
	 */
	public static ImmutableSet<CompletePreference> checkFormatandReturnCompletePreference(InputStream inputStream)
			throws Exception {
		Objects.requireNonNull(inputStream);
		LOGGER.debug("Open Stream");
		SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument.loadDocument(inputStream);
		LOGGER.debug("Open Spreadsheet");
		Table table = spreadsheetDoc.getSheetByIndex(0);
		LOGGER.debug("Get sheet index 0 done");
		LOGGER.debug("Checking format");
		if (table.getCellByPosition(1, 0).getStringValue().equals(""))
			throw new BadFormatODSException("Expected data at cell (1, 0)");
		else if (table.getCellByPosition(0, 0).getStringValue().equals(""))
			return completeFormatRanksFormat(table);
		return completeFormatVotersToRankings(table);
	}

	/**
	 * Function returning an ImmutableList of CompletePreference containing a
	 * formatting of the voting data contained in a table in the format accepting
	 * ties between alternatives
	 * 
	 * @param table an ods table containing voting information
	 * @return an ImmutableSet of CompletePreference
	 * @throws EmptySetException
	 * @throws DuplicateValueException
	 */
	public static ImmutableSet<CompletePreference> completeFormatRanksFormat(Table table)
			throws DuplicateValueException, EmptySetException {
		Objects.requireNonNull(table);
		List<CompletePreference> completePreferences = Lists.newArrayList();
		List<Alternative> alternatives = getAlternatives(table);
		int nbTotVoters = getnbTotVoters(table);
		CellRange prefRange = table.getCellRangeByPosition(1, 1, nbTotVoters, alternatives.size());
		for (int j = 0; j < prefRange.getColumnNumber(); j++) {
			Voter voter = Voter.createVoter(Integer.parseInt(table.getCellByPosition(j + 1, 0).getStringValue()));
			int nbChoice = 1;
			List<Set<Alternative>> equivalenceClasses = Lists.newArrayList();
			while (nbChoice <= alternatives.size()) {
				Set<Alternative> set = Sets.newLinkedHashSet();
				for (int i = 0; i < prefRange.getRowNumber(); i++) {
					if (nbChoice == Integer.parseInt(prefRange.getCellByPosition(j, i).getStringValue())) {
						set.add(Alternative
								.withId(Integer.parseInt(table.getCellByPosition(0, i + 1).getStringValue())));
					}
				}
				if (!set.isEmpty())
					equivalenceClasses.add(set);
				nbChoice++;
			}
			completePreferences.add(CompletePreferenceImpl.asCompletePreference(voter, equivalenceClasses));
		}
		return ImmutableSet.copyOf(completePreferences);
	}

	/**
	 * Function returning an ImmutableList of CompletePreference containing a
	 * formatting of the voting data contained in a table in a format that does not
	 * accept ties between alternatives
	 * 
	 * @param table an ods table containing voting information
	 * @return an ImmutableSet of CompletePreference
	 * @throws EmptySetException
	 * @throws DuplicateValueException
	 */
	public static ImmutableSet<CompletePreference> completeFormatVotersToRankings(Table table)
			throws DuplicateValueException, EmptySetException {
		Objects.requireNonNull(table);
		List<CompletePreference> completePreferences = Lists.newArrayList();
		List<Alternative> alternatives = getAlternatives(table);
		int nbTotVoters = getnbTotVoters(table);
		CellRange prefRange = table.getCellRangeByPosition(0, 1, nbTotVoters - 1, alternatives.size());
		for (int j = 0; j < prefRange.getColumnNumber(); j++) {
			List<Set<Alternative>> list = Lists.newArrayList();
			Voter voter = Voter.createVoter(Integer.parseInt(table.getCellByPosition(j, 0).getStringValue()));
			for (CompletePreference completePreference : completePreferences)
				if (completePreference.getVoter() == voter)
					throw new DuplicateValueException("Two voters can't have the same ID");
			for (int i = 0; i < prefRange.getRowNumber(); i++) {
				list.add(ImmutableSet.of(createAlternativeFromCellRange(j, i, prefRange)));
			}
			completePreferences.add(CompletePreferenceImpl.asCompletePreference(voter, list));
		}
		return ImmutableSet.copyOf(completePreferences);
	}

	private static Alternative createAlternativeFromCellRange(int colomnIndex, int rowIndex, CellRange prefRange) {
		return Alternative
				.withId(Integer.parseInt(prefRange.getCellByPosition(colomnIndex, rowIndex).getStringValue()));
	}
}
