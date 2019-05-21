package io.github.oliviercailloux.j_voting.profiles.analysis;

import io.github.oliviercailloux.j_voting.CompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.profiles.ImmutableProfileI;

public interface SocialWelfareFunction {

    /**
     * 
     * @param profile not <code>null</code>
     * @return a Preference with the society's preference from the profile. This
     *         Preference cannot be empty.
     */
    public CompletePreferenceImpl getSocietyPreference(ImmutableProfileI profile);
}
