package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;
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
    
    private MutableLinearPreferenceImpl(Voter voter, MutableGraph<Alternative> prefGraph) {
    	this.voter = voter;
    	graph = prefGraph; 
    	alternatives = graph.nodes();
    }

    
    @Override
	public Set<Alternative> changeOrder(Set<Alternative> alternatives) {
		
		return null;
	}

	@Override
	public void deleteAlternative(Alternative a) {
		LOGGER.debug("MutablePreferenceImpl addAlternative");
        Preconditions.checkNotNull(a);
        graph.removeNode(a);
		
	}
	
	@Override
	public MutableGraph<Alternative> asMutableGraph() {
		return graph;
	}

	@Override
	public void addAlternative(Alternative alternative) {
		
		
	}

	@Override
	public void addEquivalence(Alternative a1, Alternative a2) {
		
		
	}

	@Override
	public void setAsLeastAsGood(Alternative a1, Alternative a2) {
		
		
	}

	@Override
	public Graph<Alternative> asGraph() {
		return graph;
		
		
	}

	@Override
	public Set<Alternative> getAlternatives() {
		return alternatives;
		
	}

	@Override
	public Voter getVoter() {
	
		return null;
	}

	
    
	
   
	
	
}
