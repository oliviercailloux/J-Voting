package io.github.oliviercailloux.j_voting.preferences.classes;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference;
import io.github.oliviercailloux.j_voting.preferences.interfaces.Preference;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

/**
 * Implements MutablePreference interface.
 *
 *
 * The structure of a MutablePreference is a MutableGraph in which an edge represent the relation "at least as good as".
 *
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.Preference
 * @see io.github.oliviercailloux.j_voting.preferences.interfaces.MutablePreference
 */
public class MutablePreferenceImpl implements MutablePreference {
    
    protected MutableGraph<Alternative> graph;
    protected Voter voter;
    
    public MutablePreferenceImpl(Graph<Alternative> G, Voter V) {
        graph = GraphBuilder.from(G).build();
        voter = V;
    }
    
    public MutablePreferenceImpl(Set<List<Set<Alternative>>> pref, Voter V) {
        voter = V;
        graph = GraphBuilder.directed().allowsSelfLoops(true).build();
        for (List<Set<Alternative>> array : pref) {
            ArrayList<Alternative> tmp = new ArrayList<>();
            for (Set<Alternative> set : array) {
                
                // in a set of equality, adding every node to the graph
                // and in TMP list
                for (Alternative alt : set) {
                    if (!graph.nodes().contains(alt))
                        graph.addNode(alt);
                    tmp.add(alt);
                }
                
                // then create edges from every node in TMP to every node in current equality set
                for (Alternative alt : tmp) {
                    for (Alternative alt2 : set) {
                        graph.putEdge(alt, alt2);
                    }
                }
            }
        }
    }
    
    @Override public MutableGraph<Alternative> asGraph() {
        return null;
    }
    
    @Override public Set<Alternative> getAlternatives() {
        return null;
    }
    
    @Override public Voter getVoter() {
        return voter;
    }
    
    public MutableGraph<Alternative> getGraph() {
        return graph;
    }
}
