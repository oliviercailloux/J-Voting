package io.github.oliviercailloux.j_voting.profiles.analysis;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.j_voting.CompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.ImmutableProfileI;

/**
 * This class is immutable. This class provides a result for an election that is
 * necessarily the preference of the dictator (a Voter).
 */
public class Dictator implements SocialWelfareFunction {

    private Voter dictatorname;
    private static final Logger LOGGER = LoggerFactory
                    .getLogger(Borda.class.getName());

    public Dictator(Voter v) {
        LOGGER.debug("Dictator");
        Preconditions.checkNotNull(v);
        dictatorname = v;
    }

    /**
     * 
     * @param profile
     * @return the dictator's preference
     */
    @Override
    public CompletePreferenceImpl getSocietyPreference(ImmutableProfileI profile) {
        LOGGER.debug("getSocietyStrictPreference");
        Preconditions.checkNotNull(profile);
        Preconditions.checkArgument(profile.getProfile().containsKey(dictatorname));
        LOGGER.debug("parameter profile : {}", profile);
        LOGGER.debug("Dictator : {}", dictatorname);
        LOGGER.debug("return preference : {}", profile.getPreference(dictatorname));
        return profile.getPreference(dictatorname);
    }

    public Voter getDictator() {
        LOGGER.debug("getDictator");
        return dictatorname;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictatorname);
    }

    /**
     * @param o1 not <code>null</code>
     * @return true if both Dictators have the same voter as dictator.
     */
    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // Check not null
        if (o == null)
            return false;
        // Check class type and cast o
        if (this.getClass() != o.getClass())
            return false;
        Dictator dict = (Dictator) o;
        // check field
        return this.getDictator() == dict.getDictator();
    }
}
