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
 * default voter : voter 0
 *
 * @see io.github.oliviercailloux.j_voting.Voter
 *
 */
public interface Preference {

    /**
     * @return number of considered alternatives
     */
    public int size();

    /**
     * @return the Graph corresponding to the Preference
     */
    public Graph<Alternative> asGraph();

    /**
     * @return alternatives's set
     */
    public Set<Alternative> getAlternatives();

    /**
     *
     * @return <code>voter</code> of the preference
     */
    public Voter getVoter();
}
