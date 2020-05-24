package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

		Iterator<Alternative> itList = list.iterator();
		Alternative a = itList.next();
		Alternative a2;
		
		while (itList.hasNext() == true) {
			a2 = itList.next();
			graph.putEdge(a, a2);
			a = a2;
		}
		
		this.alternatives = graph.nodes();
		this.graph = (MutableGraph<Alternative>) Graphs.transitiveClosure(graph);
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

		Alternative temp;
		Iterator<Alternative> itTemp;

		int initRank = list.indexOf(alternative);
		if (initRank - rank > 0) { // swap à gauche
			for (int i = initRank; i > rank; i--) {
				itTemp = graph.predecessors(alternative).iterator();
				temp = itTemp.next();
				swap(temp, alternative);
			}
		} else if (initRank - rank < 0) { // swap à droite
			for (int i = initRank; i < rank; i++) {
				itTemp = graph.successors(alternative).iterator();
				temp = itTemp.next();
				swap(alternative, temp);
			}
		}
	}

	@Override
	public void removeAlternative(Alternative a) {
		LOGGER.debug("MutableLinearPreferenceImpl deleteAlternative");
		Preconditions.checkNotNull(a);

		if ((graph.successors(a).size() != 0) && (graph.predecessors(a).size() != 0)) {
			Set<Alternative> setPred = graph.predecessors(a);
			Iterator<Alternative> itPred = setPred.iterator();
			Alternative pred = itPred.next();

			Set<Alternative> setSucc = graph.successors(a);
			Iterator<Alternative> itSucc = setSucc.iterator();
			if (itSucc.hasNext() == true)
				graph.putEdge(pred, itSucc.next());
		}

		graph.removeNode(a);
		list.remove(a);
		alternatives.remove(a);
	}

	@Override
	public void addAlternative(Alternative a) {
		LOGGER.debug("MutablePreferenceImpl addAlternative");
		Preconditions.checkNotNull(a);

		for (Alternative ai : graph.nodes()) {
			if (graph.successors(ai).size() == 0) {
				graph.putEdge(ai, a);
			}
		}
		
		list.add(a);
		alternatives.add(a);
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alternatives == null) ? 0 : alternatives.hashCode());
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((voter == null) ? 0 : voter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MutableLinearPreferenceImpl other = (MutableLinearPreferenceImpl) obj;
		if (alternatives == null) {
			if (other.alternatives != null)
				return false;
		} else if (!alternatives.equals(other.alternatives))
			return false;
		if (graph == null) {
			if (other.graph != null)
				return false;
		} else if (!graph.equals(other.graph))
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (voter == null) {
			if (other.voter != null)
				return false;
		} else if (!voter.equals(other.voter))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MutableLinearPreferenceImpl [voter=" + voter + ", graph=" + graph + ", alternatives=" + alternatives
				+ ", list=" + list + ", delegate=" + delegate + "]";
	}

}
