package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class MutableLinearPreferenceImpl implements MutableLinearPreference {

	protected Voter voter;
	protected MutableGraph<Alternative> graph;
	protected Set<Alternative> alternatives;
	protected List<Alternative> list;
	protected ImmutableGraph<Alternative> delegate;

	private static final Logger LOGGER = LoggerFactory.getLogger(MutableLinearPreferenceImpl.class.getName());

	private MutableLinearPreferenceImpl(Voter voter, List<Alternative> list) {
		this.voter = voter;
		this.list = list;
		this.graph = GraphBuilder.directed().allowsSelfLoops(true).build();
		
		for(int i = 0; i < list.size(); i++) {
			for(int j = i; j < list.size(); j++) {
				graph.putEdge(list.get(i), list.get(j));
			}
		}
		
		this.alternatives = graph.nodes();
	}

	/**
	 * @param list  is a LinkedList of alternatives representing the preference.
	 * @param voter is the Voter associated to the Preference.
	 * @return the mutable linear preference
	 */
	public static MutableLinearPreference given(Voter voter, List<Alternative> list) {
		LOGGER.debug("MutableLinearPreferenceImpl given");
		Preconditions.checkNotNull(voter);
		Preconditions.checkNotNull(list);

		return new MutableLinearPreferenceImpl(voter, list);
	}

	@Override
	public void changeOrder(Alternative alternative, int rank) {
		LOGGER.debug("MutableLinearPreferenceImpl changeOrder");
		Preconditions.checkNotNull(alternative);
		Preconditions.checkNotNull(rank);
		
		int initRank = list.indexOf(alternative);
		Alternative temp;
		
		if (initRank > rank) { // swap à gauche
			for (int i = initRank; i >= rank; i--) {
				temp = list.get(i-1);
				swap(temp, alternative);
			}
		} else if (initRank < rank) { // swap à droite
			for (int i = initRank; i <= rank; i++) {
				temp = list.get(i-1);
				swap(alternative, temp);
			}
		}
	}

	@Override
	public void removeAlternative(Alternative a) {
		LOGGER.debug("MutableLinearPreferenceImpl deleteAlternative");
		Preconditions.checkNotNull(a);

		graph.removeNode(a);
		list.remove(a);
	}

	@Override
	public void addAlternative(Alternative a) {
		LOGGER.debug("MutablePreferenceImpl addAlternative");
		Preconditions.checkNotNull(a);
		
		list.add(a);
		graph.addNode(a);
		
		for(int i = 0; i < list.size(); i++) {
			graph.putEdge(list.get(i), list.get(list.size() - 1));
		}
	}

	/**
	 * @return an immutable set of all alternatives of the preference
	 * 
	 */
	@Override
	public Set<Alternative> getAlternatives() {
		LOGGER.debug("MutableLinearPreferenceImpl getAlternatives");
		Preconditions.checkState(
				!(alternatives.size() != graph.nodes().size() || !(alternatives.containsAll(graph.nodes()))),
				"An alternative must not be deleted from the set");
		return MutableLinearSetDecorator.given(alternatives);
	}

	@Override
	public Voter getVoter() {
		return voter;
	}

	@Override
	public Graph<Alternative> asGraph() {
		return ImmutableGraph.copyOf(Graphs.transitiveClosure(graph));
	}
	
	@Override
	public void swap(Alternative alternative1, Alternative alternative2) {
		LOGGER.debug("MutablePreferenceImpl Swap");
		Preconditions.checkNotNull(alternative1);
		Preconditions.checkNotNull(alternative2);
		
		Alternative a1 = alternative1;
		Alternative a2 = alternative2;

		if (list.indexOf(alternative1) > list.indexOf(alternative2)) {
			a1 = alternative2;
			a2 = alternative1;
		}
		
		List<Alternative> subList = list.subList(list.indexOf(a1)+1, list.indexOf(a2));
		
		for(Alternative a : subList) {
			graph.removeEdge(a1,a);
			graph.putEdge(a,a1);
			graph.removeEdge(a,a2);
			graph.putEdge(a2,a);
		}
		
		graph.removeEdge(a1,a2);
		graph.putEdge(a2,a1);
		
		Collections.swap(list, list.indexOf(alternative1), list.indexOf(alternative2));
	}

	@Override
	public int hashCode() {
		return Objects.hash(voter, graph, alternatives, list);
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof MutableLinearPreferenceImpl)) {
			return false;
		}
		if (this == o2) {
			return true;
		}
		MutableLinearPreferenceImpl mlp2 = (MutableLinearPreferenceImpl) o2;
		return voter.equals(mlp2.voter) && graph.equals(mlp2.graph) && alternatives.equals(mlp2.alternatives) && list.equals(mlp2.list);			
	}

	@Override
	public String toString() {
		return "MutableLinearPreferenceImpl [voter=" + voter + ", graph=" + graph + ", alternatives=" + alternatives
				+ ", list=" + list + ", delegate=" + delegate + "]";
	}

}
