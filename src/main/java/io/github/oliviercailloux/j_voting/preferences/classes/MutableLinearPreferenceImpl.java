package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

public class MutableLinearPreferenceImpl implements MutableLinearPreference {
	
	protected Voter voter;
    protected MutableGraph<Alternative> graph;
    protected Set<Alternative> alternatives;
    protected LinkedList<Alternative> list;
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MutableLinearPreferenceImpl.class.getName());
    
    private MutableLinearPreferenceImpl(Voter voter, MutableGraph<Alternative> prefGraph) {
    	this.voter = voter;
    	this.graph = prefGraph; 
    	this.alternatives = graph.nodes();
    }
    
    /**
     * @param prefGraph is a mutable graph of alternatives representing the
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
    		if (!testComplete)
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
	public void changeOrder(Alternative alternative, int rank) {
    	LOGGER.debug("MutableLinearPreferenceImpl changeOrder");
    	Preconditions.checkNotNull(alternative);
    	Preconditions.checkNotNull(rank);	
  
	}

	@Override
	public void deleteAlternative(Alternative a) {	
		LOGGER.debug("MutableLinearPreferenceImpl deleteAlternative");
        Preconditions.checkNotNull(a);
        graph.removeNode(a);	
        
        alternatives = graph.nodes();
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
        
        alternatives = graph.nodes();
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

	@Override
	public void swap(Alternative alternative1, Alternative alternative2) {
		LOGGER.debug("MutablePreferenceImpl Swap");
		Preconditions.checkNotNull(alternative1);
		Preconditions.checkNotNull(alternative2);
		
		if(alternative1.equals(alternative2)) 
			throw new IllegalArgumentException("The alternatives " + alternative1  + " and " + alternative2 + " must be differents.");

		Alternative pred1 = (Alternative)graph.predecessors(alternative1);
		Alternative pred2 = (Alternative)graph.predecessors(alternative2);
		Alternative succ1 = (Alternative)graph.successors(alternative1);
		Alternative succ2 = (Alternative)graph.successors(alternative2);
		
		graph.removeNode(alternative1);
		graph.removeNode(alternative2);
		
		if (alternative1.equals(list.getFirst())) {						
			if(alternative2.equals(list.getLast())) {				
				graph.putEdge(alternative2, succ1);
				graph.putEdge(pred2, alternative1);			
			} 
			else {
				graph.putEdge(alternative2, succ1);
				graph.putEdge(pred2, alternative1);
				graph.putEdge(alternative1,succ2);				
			}
		} 
		else if (alternative1.equals(list.getLast())) {	
			if(alternative2 == list.getFirst()) {				
				graph.putEdge(pred1,alternative2);
				graph.putEdge(alternative1,succ2);			
			} 
			else {					
				graph.putEdge(pred2,alternative1);
				graph.putEdge(alternative1,succ2);
				graph.putEdge(pred1,alternative2);
			}
		} 
		else {		
			if(alternative2 == list.getLast()) {			
				graph.putEdge(pred1,alternative2);
				graph.putEdge(alternative2,succ1);
				graph.putEdge(pred2,alternative1);	
			} 
			else if (alternative2 == list.getFirst()) {
				graph.putEdge(pred1,alternative2);
				graph.putEdge(alternative2,succ1);
				graph.putEdge(alternative1,succ2);	
			} 
			else {
				graph.putEdge(pred2,alternative1);
				graph.putEdge(alternative1,succ2);
				graph.putEdge(pred1,alternative2);
				graph.putEdge(alternative2,succ1);
			}		
		}
		
	    Collections.swap(list,list.indexOf(alternative1),list.indexOf(alternative2));
	}	
}
