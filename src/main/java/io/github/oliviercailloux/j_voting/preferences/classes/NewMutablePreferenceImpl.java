package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;

/**
 * Implements MutablePreference interface.
 *
 * The structure of a MutablePreference is a MutableGraph in which an edge
 * represent the relation "at least as good as".
 *
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.Preference
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference
 */
public class NewMutablePreferenceImpl implements MutablePreference {

	Set<List<Set<Alternative>>> preference;
	Voter voter;
	MutableGraph<Alternative> graph;
	private static final Logger LOGGER = LoggerFactory.getLogger(NewMutablePreferenceImpl.class.getName());

	/**
	 * 
	 * @param preference : <code> not null </code>
	 * @param voter      : <code> not null </code>
	 */
	public NewMutablePreferenceImpl(Set<List<Set<Alternative>>> preference, Voter voter) {
		LOGGER.debug("Constructor NewMutablePreferenceImpl");
		this.voter = voter;
		this.preference = preference;
		this.graph = createGraph(preference);
	}

	protected static MutableGraph<Alternative> createGraph(Set<List<Set<Alternative>>> pref) {
		LOGGER.debug("PreferenceImpl preferenceGraphMaker");
		Preconditions.checkNotNull(pref);
		MutableGraph<Alternative> currentgraph = GraphBuilder.directed().allowsSelfLoops(true).build();
		for (List<Set<Alternative>> array : pref) {
			ArrayList<Alternative> tmp = new ArrayList<>();
			for (Set<Alternative> set : array) {
				// in a set of equality, adding every node to the graph
				// and in TMP list
				for (Alternative alt : set) {
					if (!currentgraph.nodes().contains(alt))
						currentgraph.addNode(alt);
					tmp.add(alt);
				}
				// then create edges from every node in TMP to every node in current equality
				// set
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

	@Override
	public Set<Alternative> getAlternatives() {
		return null;
	}

	@Override
	public Voter getVoter() {
		return null;
	}

	@Override
	public MutableGraph<Alternative> asGraph() {
		return null;
	}

}
