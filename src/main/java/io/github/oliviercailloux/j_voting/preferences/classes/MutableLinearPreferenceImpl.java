package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
    	this.list = new LinkedList<>();
    	
    	Iterator<Alternative> itSet = alternatives.iterator();
		while (itSet.hasNext() == true) {
			list.add(itSet.next());
		}
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

    	for (Alternative a : prefGraph.nodes()) {    			
    		if ((prefGraph.successors(a).size() == 0) && (prefGraph.predecessors(a).size() == 0)) 
    			throw new IllegalArgumentException("There are no edges between all alternatives");
    	}
    	for (Alternative a1 : prefGraph.nodes()) {
            for (Alternative a2 : prefGraph.successors(a1)) {
                if (Graphs.transitiveClosure(prefGraph).hasEdgeConnecting(a2,a1) && !a2.equals(a1)) {
                    throw new IllegalArgumentException("The alternatives " + a1 + " and " + a2 + " cannot be ex-eaquo.");
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

        if ((graph.successors(a).size() != 0) && (graph.predecessors(a).size() != 0)) {	
        	Set<Alternative> setPred = graph.predecessors(a);
    		Iterator<Alternative> itPred = setPred.iterator();
    		Alternative pred = itPred.next();
    				
    		Set<Alternative> setSucc = graph.successors(a);
    		Iterator<Alternative> itSucc = setSucc.iterator();
    		if (itSucc.hasNext() == true) 
    			graph.putEdge(pred,  itSucc.next());
		}
            
        graph.removeNode(a);
        list.remove(a);
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
			throw new IllegalArgumentException("The alternatives must be differents.");
		
		Alternative pred1 = null;
		Alternative pred2 = null;
		Alternative succ1 = null;
		Alternative succ2 = null;
		
		Set<Alternative> setSucc1 = graph.successors(alternative1);
		Iterator<Alternative> itSucc1 = setSucc1.iterator();
		if (itSucc1.hasNext() == true) 
			succ1 = itSucc1.next();
		
		Set<Alternative> setSucc2 = graph.successors(alternative2);
		Iterator<Alternative> itSucc2 = setSucc2.iterator();
		if (itSucc2.hasNext() == true) 
			succ2 = itSucc2.next();
		
		Set<Alternative> setPred1 = graph.predecessors(alternative1);
		Iterator<Alternative> itPred1 = setPred1.iterator();
		if (itPred1.hasNext() == true) 
			pred1 = itPred1.next();
				
		Set<Alternative> setPred2 = graph.predecessors(alternative2);
		Iterator<Alternative> itPred2 = setPred2.iterator();
		if (itPred2.hasNext() == true) 
			pred2 = itPred2.next();		
		
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
	
}
