package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableAntiSymmetricPreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;

public class MutableAntiSymmetricPreferenceImpl implements MutableAntiSymmetricPreference {

	protected Voter voter;
	protected MutableGraph<Alternative> graph;
	private static final Logger LOGGER = LoggerFactory.getLogger(MutableAntiSymmetricPreference.class.getName());

	private MutableAntiSymmetricPreferenceImpl(MutableGraph<Alternative> prefGraph, Voter voter) {
		this.voter = voter;
		graph = prefGraph;
	}

	/**
	 * @param pref  is a mutable graph of alternatives representing the preference.
	 *              Each node of the graph contains a set, and this set must contain
	 *              only one element, to respect the antisymmetric condition (there
	 *              s no ex aequo alternatives)
	 * @param voter is the Voter associated to the Preference.
	 * @return the mutable antisymmetric preference
	 * @see Voter
	 * @see MutableAntiSymmetricPreference#asGraph()
	 */
	public static MutableAntiSymmetricPreferenceImpl given(MutableGraph<Alternative> pref, Voter voter) {
		LOGGER.debug("MutableAntiSymmetricPreferenceImpl given");
		Preconditions.checkNotNull(pref);
		Preconditions.checkNotNull(voter);
		for (Alternative a1 : pref.nodes()) {
			for (Alternative a2 : pref.successors(a1)) {
				if (pref.hasEdgeConnecting(a2, a1) && !a2.equals(a1)) {
					throw new IllegalArgumentException("Must not contain ex-eaquo Alternative");
				}
			}
		}
		return new MutableAntiSymmetricPreferenceImpl(pref, voter);

	}

	/**
	 * @param pref is a set of lists of sets of Alternatives representing the
	 *             preference. In the first set, every list is a linear comparison
	 *             of sets of alternatives. (first in the list is preferred to next
	 *             ones, etc.) Those sets of alternatives contain ex-aequo
	 *             alternatives.
	 * @return the mutable preference, implemented with a transitively closed graph.
	 * @see Voter
	 * @see Preference
	 * @see MutablePreference
	 * @see MutablePreferenceImpl#asGraph()
	 */
	public static MutableGraph<Alternative> preferenceGraphMaker(Set<List<Set<Alternative>>> pref) {
		LOGGER.debug("MutableAntiSymmetricPreferenceImpl preferenceGraphMaker");
		Preconditions.checkNotNull(pref);
		MutableGraph<Alternative> currentgraph = GraphBuilder.directed().allowsSelfLoops(true).build();
		for (List<Set<Alternative>> array : pref) {
			ArrayList<Alternative> tmp = new ArrayList<>();
			for (Set<Alternative> set : array) {
				// in a set of equality, adding every node to the graph
				// and in TMP list
				for (Alternative alt : set) {
					tmp.add(alt);
				}
				// create edges from every node in TMP to every node in current equality set
				// If one of them is not in the graph, they are added.
				for (Alternative alt : tmp) {
					for (Alternative alt2 : set) {
						currentgraph.putEdge(alt, alt2);
					}
				}
			}
		}
		if (currentgraph.nodes().isEmpty())
			throw new IllegalArgumentException("Must contain at least one alternative");
		return currentgraph;
	}

	/**
	 * Factory method making new MutableAntiSymmetricPreference from an other
	 * Preference. It creates a new similar graph instance (mutable and
	 * anti-symmetric). The voter instance of the created preference is the same as
	 * the copied preference.
	 *
	 * @param pref a Preference
	 * @return a copy of this preference as a MutablePreference, with the same
	 *         voter.
	 */
	public static MutableAntiSymmetricPreferenceImpl given(Preference pref) {
		Preconditions.checkNotNull(pref);
		return new MutableAntiSymmetricPreferenceImpl(Graphs.copyOf(pref.asGraph()), pref.getVoter());
	}

	@Override
	public void addAlternative(Alternative alternative) {
		LOGGER.debug("MutableAntiSymmetricPreferenceImpl addAlternative");
		Preconditions.checkNotNull(alternative);
		graph.putEdge(alternative, alternative);
	}

	@Override
	public void putEdge(Alternative a1, Alternative a2) {
		LOGGER.debug("MutableAntiSymmetricPreferenceImpl putEdge");
		Preconditions.checkNotNull(a1);
		Preconditions.checkNotNull(a2);
		if (graph.hasEdgeConnecting(a2, a1)) {
			throw new IllegalArgumentException("Must not contain ex-eaquo Alternative");
		}
		graph.putEdge(a1, a2);
		graph = Graphs.copyOf(Graphs.transitiveClosure(graph));
	}

	@Override
	public ImmutableGraph<Alternative> asGraph() {
		return ImmutableGraph.copyOf(graph);
	}

	@Override
	public MutableGraph<Alternative> asMutableGraph() {
		return graph;
	}

	/**
	 * 
	 * TODO : For now, any modification will not affect the graph
	 */
	@Override
	public Set<Alternative> getAlternatives() {
		return graph.nodes();
	}

	@Override
	public Voter getVoter() {
		return voter;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("graph", graph).add("voter", voter).toString();
	}
}
