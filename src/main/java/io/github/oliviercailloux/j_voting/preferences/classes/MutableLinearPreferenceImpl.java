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
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;

import java.util.Objects;

public class MutableLinearPreferenceImpl implements MutableLinearPreference{

	protected Voter voter;
    protected MutableGraph<Alternative> graph;
    protected Set<Alternative> alternatives;
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MutableLinearPreferenceImpl.class.getName());
    
    public MutableLinearPreferenceImpl(Voter voter, MutableGraph<Alternative> prefGraph) {
    	this.voter = voter;
    	graph = prefGraph; 
    	alternatives = graph.nodes();
    }

    //faire des constructeurs given ?? comme dans MutablePreference 
    //et mettre le constructeur au dessus en private
    
    @Override
	public Set<Alternative> changeOrder(Set<Alternative> a) {
		
		return null;
	}

	@Override
	public void deleteAlternative(Alternative a) {
		
		LOGGER.debug("MutableLinearPreferenceImpl addAlternative");
        Preconditions.checkNotNull(a);
        graph.removeNode(a);
		
	}
	
	@Override
	public void addAlternative(Alternative a) {
		LOGGER.debug("MutablePreferenceImpl addAlternative");
        Preconditions.checkNotNull(a);
        graph.addNode(a);
		
	}


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
