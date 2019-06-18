package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;

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
     * Static factory method creating a mutable preference from a setAlternatives of data.
     * Those datas are implemented in a graph.
     *
     * @param preference is a set of lists of sets of Alternatives representing the preference.
     *                        In the first set of lists of sets of Alternatives,
     *                        every list is a linear comparison of sets of alternatives.
     *                        (first in the least is preferred to next ones, etc.)
     *                        Those sets of alternatives contain ex-aequo alternatives.
     * @param voter           is the Voter associated to the Preference.
     * @return the mutable preference, implemented with a graph.
     * @see Voter
     * @see Preference
     * @see MutablePreference#asGraph()
     */
	public static NewMutablePreferenceImpl createNewMutablePreferenceImpl(
			Set<List<Set<Alternative>>> preference,
			Voter voter) {
		LOGGER.debug("Factory NewMutablePreferenceImpl");
		Preconditions.checkNotNull(preference);
		Preconditions.checkNotNull(voter);
		return new NewMutablePreferenceImpl(preference, voter);
	}

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

	/**
	 * Faut utiliser factory ici ? comment améliorer ? c'est en bleu pourquoi on met
	 * en param alors que cest en haut pourquoi dans classe Complete il utilise un
	 * MutableGraph 1ere ligne de la methode
	 */
	protected static MutableGraph<Alternative> createGraph(Set<List<Set<Alternative>>> pref) {
		//LOGGER.debug("PreferenceImpl preferenceGraphMaker");
		//Preconditions.checkNotNull(pref);
		MutableGraph<Alternative> currentgraph = GraphBuilder.directed().allowsSelfLoops(true).build();
		for (List<Set<Alternative>> list : pref) {
			ArrayList<Alternative> tmp = new ArrayList<>();
			for (Set<Alternative> set : list) {
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
		Set<Alternative> returnedSet = new HashSet<>();
		for (List<Set<Alternative>> list : preference) {
			for (Set<Alternative> set : list) {
				for (Alternative alternative : set) {
					returnedSet.add(alternative);
				}
			}
		}
		return returnedSet;
	}
	
	/**
	 * ya un pb
	 * dans mutablepreference ya ecrit
	 * impossible de changer l'ordre
	 * donc cest que pour ajouter des alternatives ?
	 * 
	 * et dans immutablepreference ya ecrit
	 * cannot be modified
	 * alors que dans la complete
	 * il y a addAlternative
	 * c'est quoi en fait mutable et immutable ?
	 * 
	 */
	@Override
	public Voter getVoter() {
		return this.voter;
	}

	@Override
	public MutableGraph<Alternative> asGraph() {
		return graph;
	}
	
	
	
	/**
	 * on a { [{1,2},{3}], [{4},{5,6}] }
	 * quand on add 'a', ou est ce qu'on le met ?
	 *  { [{1,2},{3}], [{4},{5,6}], a }
	 *  { [{1,2},{3}], [{4},{5,6}], [a]}
	 *  { [{1,2},{3}], [{4},{5,6}], [{a}] }
	 * 
	 */
	/**
     * Adds an alternative to the Preference.
     * This alternative is not preferred to any other of the preference
     * it is being added isolated.
     *
     * @param a is the alternative which we has to add to the preference.
     */
	public void addAlternative(Alternative a) {
		LOGGER.debug("NewMutablePreferenceImpl addAlternative");
		//Preconditions.checkNotNull(a);
		//Est ce que si je mets ca, faut mettre avec un LOGGER ?
		Set<Alternative> tmpSet = new HashSet<>();
		tmpSet.add(a);
		ArrayList<Set<Alternative>> tmpList = new ArrayList<>();
		tmpList.add(tmpSet);
		preference.add(tmpList);
		createGraph(preference);
		//on recrée le graph ?
		//on ajoute pas le noeud tout simplement pour gg tps ?
	}
	
	/**
     * Adds an edge from a1 to a2 and from a2 to a1. If one of them is not in the graph, they are added.
     * a1 and a2 are ex-aequo.
     *
     * * Graph is rearranged : a transitive closure is applied to it/
     *
     * @param a1 first alternative
     * @param a2 second alternative
     */
    public void addExAequo(Alternative a1, Alternative a2) {
        LOGGER.debug("MutablePreferenceImpl addExAequo");
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        if (!graph.nodes().contains(a1))
            addAlternative(a1);
        if (!graph.nodes().contains(a2))
            addAlternative(a2);
        graph.putEdge(a1, a2);
        graph.putEdge(a2, a1);
        graph = Graphs.copyOf(Graphs.transitiveClosure(graph));
    }

}
