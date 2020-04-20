package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class MutableLinearPreferenceImpl implements MutableLinearPreference{
	
	protected Voter voter;
    protected MutableGraph<Alternative> graph;
    protected Set<Alternative> alternatives;
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MutableLinearPreferenceImpl.class.getName());
    
    private MutableLinearPreferenceImpl(Voter voter, MutableGraph<Alternative> prefGraph) {
    	this.voter = voter;
    	this.graph = prefGraph; 
    	this.alternatives = graph.nodes();
    }
    
    /**
     * @param pref  is a mutable graph of alternatives representing the
     *              preference. This graph has no cycle.
     * @param voter is the Voter associated to the Preference.
     * @return the mutable linear preference
     */
    public static MutableLinearPreference given(Voter voter, MutableGraph<Alternative> prefGraph) {
    	LOGGER.debug("MutableLinearPreferenceImpl given");
    	Preconditions.checkNotNull(voter);
    	Preconditions.checkNotNull(prefGraph);
    	boolean testComplete = true;
    	for (Alternative a : prefGraph.nodes()) {
    		if (testComplete == false)
    			throw new IllegalArgumentException("There are no edges between all alternatives");
    		if (prefGraph.successors(a).size() == 0)
    			testComplete = false;
    	}
    	
    	if (Graphs.hasCycle(prefGraph))
			throw new IllegalArgumentException("The preference has a cycle");
    		
    	for (Alternative a1 : prefGraph.nodes()) {
            for (Alternative a2 : prefGraph.successors(a1)) {
                if (Graphs.transitiveClosure(prefGraph).hasEdgeConnecting(a2,
                                a1) && !a2.equals(a1)) {
                    throw new IllegalArgumentException("The alternatives " + a1
                                    + " and " + a2 + " cannot be ex-eaquo.");
                }
            }
        }
    	return new MutableLinearPreferenceImpl(voter, prefGraph);
    }
    
    @Override
	public void changeOrder(MutableGraph<Alternative> newGraph) {
    	LOGGER.debug("MutableLinearPreferenceImpl changeOrder");
    	Preconditions.checkNotNull(newGraph);
    	graph = newGraph;
    	alternatives = newGraph.nodes();
	}

	@Override
	public void deleteAlternative(Alternative a) {	
		LOGGER.debug("MutableLinearPreferenceImpl deleteAlternative");
        Preconditions.checkNotNull(a);
        graph.removeNode(a);
		
	}
	
	@Override
	public void addAlternative(Alternative a) {
		LOGGER.debug("MutablePreferenceImpl addAlternative");
        Preconditions.checkNotNull(a);
        graph.addNode(a);
        for (Alternative ai : graph.nodes()) {
        	if (graph.successors(a).size() == 0)
    			graph.putEdge(ai, a);
    	}
		
	}
	
	/**
     * @return an immutable set of all alternatives of the preference
     * 
     */
	@Override
	public Set<Alternative> getAlternatives() {	
		LOGGER.debug("MutableLinearPreferenceImpl getAlternatives");	
		if (alternatives.size() != graph.nodes().size() || !(alternatives.containsAll(graph.nodes()))) {        	
			throw new IllegalStateException("An alternative must not be deleted from the set");
        }		
		return ImmutableSet.copyOf(alternatives);   
	}

	@Override
	public Voter getVoter() {
		return voter;
	}
	
	@Override
	public Graph<Alternative> asGraph() {
		return ImmutableGraph.copyOf(Graphs.transitiveClosure(graph));
	}

	
	
   
	
	
}
