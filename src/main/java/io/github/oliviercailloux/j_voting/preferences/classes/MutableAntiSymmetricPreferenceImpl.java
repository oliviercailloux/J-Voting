package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableAntiSymmetricPreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;

public class MutableAntiSymmetricPreferenceImpl extends PreferenceImpl implements MutableAntiSymmetricPreference {

	protected MutableGraph<Alternative> graph;
	private static final Logger LOGGER = LoggerFactory.getLogger(MutableAntiSymmetricPreference.class.getName());

	private MutableAntiSymmetricPreferenceImpl(MutableGraph<Alternative> prefGraph, Voter voter) {
		this.voter = voter;
		graph = prefGraph;
	}

	/**
	 * Create a mutable antisymmetric preference from a set of data. Those datas are
	 * implemented in a graph.
	 *
	 * @param pref  is a set of lists of sets of Alternatives representing the
	 *              preference. In the first set, every list is a linear comparison
	 *              of sets of alternatives. (first in the list is preferred to next
	 *              ones, etc.) Those sets of alternatives usually contain ex-aequo
	 *              alternatives, but in this antisymmetric case, they must contain
	 *              only one element.
	 * @param voter is the Voter associated to the Preference.
	 * @return the mutable preference, implemented with a graph.
	 * @see Voter
	 * @see Preference
	 * @see MutableAntiSymmetricPreference#asGraph()
	 */
	public static MutableAntiSymmetricPreferenceImpl of(Set<List<Set<Alternative>>> pref, Voter voter) {
		LOGGER.debug("MutablePreferenceImpl of Factory");
		Preconditions.checkNotNull(pref);
		Preconditions.checkNotNull(voter);
		// Check for antisymmetric condition to be respected.
		for (List<Set<Alternative>> list : pref) {
			for (Set<Alternative> set : list) {
				if (set.size() != 1)
					throw new IllegalArgumentException("Must not contain ex-eaquo Alternative");
			}
		}
		return new MutableAntiSymmetricPreferenceImpl(preferenceGraphMaker(pref), voter);
	}

	/**
	 * Adds an alternative to the Preference. This alternative is not preferred to
	 * any other of the preference, it is being added isolated.
	 *
	 * @param alternative to add to the preference.
	 */
	public void addAlternative(Alternative alternative) {
		LOGGER.debug("MutableAntiSymmetricPreferenceImpl addAlternative");
		Preconditions.checkNotNull(alternative);
		graph.putEdge(alternative, alternative);
	}

	/**
	 * Adds an edge from a1 to a2, so that a1 is preferred to a2 (a1 > a2). If one
	 * of them is not in the graph, they are added.
	 *
	 * Graph is rearranged : a transitive closure is applied to it/
	 *
	 * @param a1 preferred alternative to a2
	 * @param a2 "lower" alternative
	 */
	public void addStrictPreference(Alternative a1, Alternative a2) {
		LOGGER.debug("MutableAntiSymmetricPreferenceImpl addStrictPreference");
		Preconditions.checkNotNull(a1);
		Preconditions.checkNotNull(a2);
		graph.putEdge(a1, a2);
		graph = Graphs.copyOf(Graphs.transitiveClosure(graph));
	}
}
