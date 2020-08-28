package io.github.oliviercailloux.j_voting.profiles;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.OldCompletePreferenceImpl;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.profiles.management.ProfileBuilder;

/**
 * This class is immutable. Represents an Incomplete Profile.
 */
public class ImmutableProfileI implements ProfileI {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableProfileI.class.getName());
	protected Map<Voter, ? extends OldCompletePreferenceImpl> votes;

	protected ImmutableProfileI(Map<Voter, ? extends OldCompletePreferenceImpl> votes) {
		LOGGER.debug("ImmutableProfileI constructor");
		this.votes = votes;
	}

	@Override
	public OldCompletePreferenceImpl getPreference(Voter v) {
		LOGGER.debug("getPreference:");
		Preconditions.checkNotNull(v);
		LOGGER.debug("parameter voter : {}", v);
		if (votes.containsKey(v)) {
			return votes.get(v);
		}
		throw new NoSuchElementException("Voter " + v + "is not in the map !");
	}

	@Override
	public int getMaxSizeOfPreference() {
		LOGGER.debug("getMaxSizeOfPreference");
		int maxSize = 0;
		Collection<? extends OldCompletePreferenceImpl> pref = votes.values();
		for (OldCompletePreferenceImpl p : pref) {
			if (maxSize < p.size()) {
				maxSize = p.size();
			}
		}
		LOGGER.debug("biggest Preference has size : {}", maxSize);
		return maxSize;
	}

	@Override
	public Map<Voter, ? extends OldCompletePreferenceImpl> getProfile() {
		LOGGER.debug("getProfile:");
		return votes;
	}

	@Override
	public NavigableSet<Voter> getAllVoters() {
		LOGGER.debug("getAllVoters:");
		NavigableSet<Voter> keys = new TreeSet<>();
		for (Voter v : votes.keySet()) {
			keys.add(v);
		}
		LOGGER.debug("all voter : {}", keys);
		return keys;
	}

	@Override
	public int getNbVoters() {
		LOGGER.debug("getNbVoters:");
		return getAllVoters().size();
	}

	@Override
	public int getSumVoteCount() {
		LOGGER.debug("getSumCount:");
		return getAllVoters().size();
	}

	@Override
	public Set<OldCompletePreferenceImpl> getUniquePreferences() {
		LOGGER.debug("getUniquePreferences");
		Set<OldCompletePreferenceImpl> unique = new LinkedHashSet<>();
		for (OldCompletePreferenceImpl pref : votes.values()) {
			LOGGER.debug("next preference : {}", pref);
			unique.add(pref);
		}
		return unique;
	}

	@Override
	public int getNbUniquePreferences() {
		LOGGER.debug("getNbUniquePreferences:");
		return getUniquePreferences().size();
	}

	@Override
	public boolean isComplete() {
		LOGGER.debug("isComplete");
		OldCompletePreferenceImpl pref = votes.values().iterator().next();
		LOGGER.debug("first preferences :{}", pref);
		for (OldCompletePreferenceImpl p : votes.values()) {
			if (!p.hasSameAlternatives(pref)) {
				LOGGER.debug("Profile incomplete.");
				return false;
			}
		}
		LOGGER.debug("Profile is complete.");
		return true;
	}

	@Override
	public boolean isStrict() {
		LOGGER.debug("isStrict:");
		for (OldCompletePreferenceImpl p : votes.values()) {
			if (!p.isStrict()) {
				LOGGER.debug("non strict");
				return false;
			}
		}
		LOGGER.debug("strict");
		return true;
	}

	@Override
	public int getNbVoterForPreference(OldCompletePreferenceImpl p) {
		LOGGER.debug("getnbVoterByPreference:");
		Preconditions.checkNotNull(p);
		LOGGER.debug("parameter preference: {}", p);
		int nb = 0;
		for (OldCompletePreferenceImpl p1 : votes.values()) {
			if (p.equals(p1)) {
				nb++;
			}
		}
		LOGGER.debug("result: {}", nb);
		return nb;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (this.getClass() != o.getClass())
			return false;
		ImmutableProfileI immu = (ImmutableProfileI) o;
		return this.getAllVoters().equals(immu.getAllVoters());
	}

	@Override
	public int hashCode() {
		return Objects.hash(votes);
	}

	@Override
	public ProfileI restrictProfile() {
		LOGGER.debug("StricterProfile : ");
		ProfileBuilder profileBuilder = ProfileBuilder.createProfileBuilder(this);
		if (isComplete()) {
			if (isStrict()) {
				LOGGER.debug("strict complete profile");
				return profileBuilder.createStrictProfile();
			}
			LOGGER.debug("non strict complete profile");
			return profileBuilder.createProfile();
		}
		if (isStrict()) {
			LOGGER.debug("strict incomplete profile");
			return profileBuilder.createStrictProfileI();
		}
		LOGGER.debug("non strict incomplete profile");
		return this;
	}

	/**
	 * 
	 * @param map not <code> null </code>
	 * @return the map if and only if it represents a complete profile. If it is
	 *         incomplete, it throws an IllegalArgumentException.
	 */
	public static Map<Voter, ? extends OldCompletePreferenceImpl> checkCompleteMap(
			Map<Voter, ? extends OldCompletePreferenceImpl> map) {
		LOGGER.debug("checkCompleteMap:");
		Preconditions.checkNotNull(map);
		if (!createImmutableProfileI(map).isComplete()) {
			throw new IllegalArgumentException("map is incomplete");
		}
		return map;
	}

	/**
	 * 
	 * @param map not <code> null </code>
	 * @return the map if and only if it represents a strict profile. If it is not
	 *         strict, it throws an IllegalArgumentException.
	 */
	public static Map<Voter, ? extends OldCompletePreferenceImpl> checkStrictMap(
			Map<Voter, ? extends OldCompletePreferenceImpl> map) {
		LOGGER.debug("checkstrictMap:");
		Preconditions.checkNotNull(map);
		if (!createImmutableProfileI(map).isStrict()) {
			throw new IllegalArgumentException("map is not strict");
		}
		return map;
	}

	/**
	 * Factory method for ImmutableProfileI
	 * 
	 * @param votes <code>not null</code>
	 * @return new ImmutableProfileI
	 */
	public static ImmutableProfileI createImmutableProfileI(Map<Voter, ? extends OldCompletePreferenceImpl> votes) {
		LOGGER.debug("Factory ImmutableProfileI");
		Preconditions.checkNotNull(votes);
		return new ImmutableProfileI(votes);
	}

	@Override
	public int getNbAlternatives() {
		LOGGER.debug("getNbAlternatives");
		return getAlternatives().size();
	}

	@Override
	public Set<Alternative> getAlternatives() {
		LOGGER.debug("getAlternatives");
		Set<Alternative> set = new HashSet<>();
		for (OldCompletePreferenceImpl pref : getUniquePreferences()) {
			for (Alternative a : OldCompletePreferenceImpl.toAlternativeSet(pref.getPreferencesNonStrict())) {
				set.add(a);
			}
		}
		return set;
	}

	@Override
	public String getFormat() {
		LOGGER.debug("getFormat : ");
		if (isComplete()) {
			if (isStrict()) {
				LOGGER.debug("strict complete profile");
				return "soc";
			}
			LOGGER.debug("non strict complete profile");
			return "toc";
		}
		if (isStrict()) {
			LOGGER.debug("strict incomplete profile");
			return "soi";
		}
		LOGGER.debug("non strict incomplete profile");
		return "toi";
	}
}
