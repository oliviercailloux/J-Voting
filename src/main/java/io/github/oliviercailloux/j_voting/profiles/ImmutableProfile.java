package io.github.oliviercailloux.j_voting.profiles;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;

/**
 * This class is immutable. Represents a Complete Profile.
 */
public class ImmutableProfile extends ImmutableProfileI implements Profile {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableProfile.class.getName());

	/**
	 * Factory method for ImmutableProfile
	 * 
	 * @param votes <code> not null</code>
	 * @return new ImmutableProfile
	 */
	public static ImmutableProfile createImmutableProfile(Map<Voter, ? extends OldCompletePreferenceImpl> votes) {
		LOGGER.debug("Factory ImmutableProfile");
		Preconditions.checkNotNull(votes);
		return new ImmutableProfile(votes);
	}

	private ImmutableProfile(Map<Voter, ? extends OldCompletePreferenceImpl> votes) {
		super(checkCompleteMap(votes));
	}

	@Override
	public Set<Alternative> getAlternatives() {
		LOGGER.debug("getAlternatives:");
		OldCompletePreferenceImpl p = votes.values().iterator().next();
		return OldCompletePreferenceImpl.toAlternativeSet(p.getPreferencesNonStrict());
	}
}
