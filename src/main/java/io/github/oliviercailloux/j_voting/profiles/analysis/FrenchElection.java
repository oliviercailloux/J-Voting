package io.github.oliviercailloux.j_voting.profiles.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.j_voting.OldCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.profiles.ImmutableProfileI;
import io.github.oliviercailloux.j_voting.profiles.ImmutableStrictProfileI;
import io.github.oliviercailloux.j_voting.profiles.StrictProfileI;
import io.github.oliviercailloux.j_voting.profiles.management.StrictProfileBuilder;

/**
 * 
 * This class provides the result of an election, given by the french election
 * system. This means that each voter can only vote for one alternative. This
 * class can provide a result only for strict profiles.
 *
 */
public class FrenchElection implements SocialWelfareFunction {

    private static final Logger LOGGER = LoggerFactory
                    .getLogger(FrenchElection.class.getName());

    @Override
    public OldCompletePreferenceImpl getSocietyPreference(ImmutableProfileI profile) {
        LOGGER.debug("getSocietyPreference");
        Preconditions.checkNotNull(profile);
        if (!profile.isStrict()) {
            throw new IllegalArgumentException(
                            "A french election can only happen with a strict profile.");
        }
        ImmutableStrictProfileI newProf = StrictProfileBuilder.createStrictProfileBuilder((StrictProfileI) profile).createOneAlternativeProfile();
        return Borda.withScores().getSocietyPreference(newProf);
    }
}
