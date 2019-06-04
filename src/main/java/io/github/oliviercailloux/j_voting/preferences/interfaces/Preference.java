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
     * The returned graph reads through this object: if this object is mutable,
     * any modification to this object modifies the returned graph,
     * and conversely.
     *
     * In the graph : a relation from a to be means "a is at least as good as b".
     *
     * @return the Graph corresponding to the Preference
     *
     */
    public Graph<Alternative> asGraph();

    /**
     * The returned set reads through this object: if this object is mutable,
     * any modification to this object modifies the returned set,
     * and conversely.
     * @return alternatives's set.
     */
    public Set<Alternative> getAlternatives();

    /**
     *
     * @return <code>Voter</code> instance of the preference
     * Returns the voter 0 if no specific voter is associated to this preference
     */
    public Voter getVoter();
}
