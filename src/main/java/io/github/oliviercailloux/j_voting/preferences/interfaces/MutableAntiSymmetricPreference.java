package io.github.oliviercailloux.j_voting.preferences.interfaces;

import io.github.oliviercailloux.j_voting.Alternative;

/**
 * a mutable anti-symmetric preference is an anti-symmetric preference (without
 * equal alternatives) in which some alternatives can be added.
 */
public interface MutableAntiSymmetricPreference
                extends AntiSymmetricPreference {

    /**
     * adds a single alternative to the preference as a singleton.
     * 
     * @param a must be not <code> null </code>
     */
    public void addAlternative(Alternative a);

    /**
     * addStrictPreference adds to the preference "a1>a2";
     * 
     * @param a1 must be not <code> null </code>
     * @param a2 must be not <code> null </code>
     */
    public void addStrictPreference(Alternative a1, Alternative a2);
}
