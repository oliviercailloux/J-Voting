package io.github.oliviercailloux.j_voting.profiles;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.CompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;

/**
 * This class is immutable. Represents a Complete Profile.
 */
public class ImmutableProfile extends ImmutableProfileI implements Profile {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(ImmutableProfile.class.getName());

    public ImmutableProfile(Map<Voter, ? extends CompletePreferenceImpl> votes) {
        super(checkCompleteMap(votes));
    }

    @Override
    public Set<Alternative> getAlternatives() {
        LOGGER.debug("getAlternatives:");
        CompletePreferenceImpl p = votes.values().iterator().next();
        return CompletePreferenceImpl.toAlternativeSet(p.getPreferencesNonStrict());
    }
}
