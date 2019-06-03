package io.github.oliviercailloux.j_voting.preferences.interfaces;

import java.util.Set;

import com.google.common.graph.Graph;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;

/**
 *
 * Preference Interface is used to define the order chosen by a Voter between
 * some alternatives.
 *
 * A preference is empty iff it contains no alternative, equivalently, iff it
 * contains no edge (an edge between <i>a</i> and <i>b</i> means that <i>a</i>
 * is at least as good as <i>b</i>.
 *
 * @see io.github.oliviercailloux.j_voting.Voter
 *
 */
public interface Preference {
    
    /**
     * @return the Graph corresponding to the Preference<br><br>
     * The returned graph reads through this object: if this object is mutable,
     * any modification to this object modifies the returned graph,
     * and conversely.<br><br>
     *
     * In the graph : a realtion from a to be means "a is at least as good as b"
     *
     */
    public Graph<Alternative> asGraph();

    /**
     * @return alternatives's set<br><br>
     * The returned set reads through this object: if this object is mutable,
     * any modification to this object modifies the returned graph,
     * and conversely.
     */
    public Set<Alternative> getAlternatives();

    /**
     *
     * @return <code>voter</code> of the preference
     * Returns the voter 0 if no specific voter is associated to this preference
     */
    public Voter getVoter();
}
