package io.github.oliviercailloux.j_voting;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * 
 * Immutable class. A Preference represents a list of alternatives, sorted by
 * order of preference. Two alternatives can be equally ranked. There cannot be
 * twice the same alternative.
 *
 */
public class OldCompletePreferenceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(OldCompletePreferenceImpl.class.getName());
	protected List<Set<Alternative>> preference;

	/**
	 * @param preferences <code>not null</code> a list of sets of alternatives. In a
	 *                    set, the alternatives are equally ranked. The sets are
	 *                    sorted by preference in the list. If an alternative is
	 *                    present several times, an IllegalArgumentException is
	 *                    thrown.
	 */
	protected OldCompletePreferenceImpl(List<Set<Alternative>> preference) {
		LOGGER.debug("Preference Factory");
		this.preference = preference;
	}

	/**
	 * 
	 * @param position not <code>null</code>
	 * @return the alternative at the position given in the strict preference
	 */
	public Alternative getAlternative(Integer position) throws IndexOutOfBoundsException {
		LOGGER.debug("getAlternative");
		Preconditions.checkNotNull(position);
		if (position >= preference.size()) {
			throw new IndexOutOfBoundsException("This position doesn't exist in the Preference");
		}
		return preference.get(position).iterator().next();
	}

	/**
	 * @return the preference of alternatives
	 */
	public List<Set<Alternative>> getPreferencesNonStrict() {
		LOGGER.debug("getPreferencesNonStrict :");
		return preference;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Set<Alternative> set : preference) {
			s.append("{");
			for (Alternative alter : set) {
				s.append(alter.getId() + ",");
			}
			s.delete(s.length() - 1, s.length()).append("},");
		}
		s.delete(s.length() - 1, s.length());
		return s.toString();
	}

	/**
	 * @return the size of the Preference, i.e. the number of alternatives in the
	 *         Preference
	 */
	public int size() {
		LOGGER.debug("size :");
		return size(preference);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (this.getClass() != o.getClass())
			return false;
		OldCompletePreferenceImpl pref = (OldCompletePreferenceImpl) o;
		return this.preference.equals(pref.preference);
	}

	@Override
	public int hashCode() {
		return Objects.hash(preference);
	}

	/**
	 * @param alter <code>not null</code>
	 * @return whether the preference contains the alternative given as parameter
	 */
	public boolean contains(Alternative alter) {
		LOGGER.debug("contains:");
		Preconditions.checkNotNull(alter);
		LOGGER.debug("parameter alternative : {}", alter);
		return (toAlternativeSet(preference).contains(alter));
	}

	/**
	 * @param p <code>not null</code>
	 * @return whether the preferences are about the same alternatives exactly (not
	 *         necessarily in the same order).
	 */
	public boolean hasSameAlternatives(OldCompletePreferenceImpl p) {
		LOGGER.debug("hasSameAlternatives:");
		Preconditions.checkNotNull(p);
		LOGGER.debug("parameter preference : {}", p);
		return (this.isIncludedIn(p) && p.isIncludedIn(this));
	}

	/**
	 * @param p <code>not null</code>
	 * @return whether the parameter preference contains all the alternatives in the
	 *         calling preference
	 */
	public boolean isIncludedIn(OldCompletePreferenceImpl p) {
		LOGGER.debug("isIncludedIn:");
		Preconditions.checkNotNull(p);
		LOGGER.debug("parameter preference : {}", p);
		for (Alternative alter : toAlternativeSet(preference)) {
			if (!p.contains(alter)) {
				LOGGER.debug("return false");
				return false;
			}
		}
		LOGGER.debug("return true");
		return true;
	}

	/**
	 * 
	 * @param alter not <code>null</code>. If the alternative is not in the
	 *              preference, it throws an IllegalArgumentException.
	 * @return the rank of the alternative given in the Preference.
	 */
	public int getAlternativeRank(Alternative alter) {
		LOGGER.debug("getAlternativeRank:");
		Preconditions.checkNotNull(alter);
		if (!this.contains(alter)) {
			throw new IllegalArgumentException("Alternative not in the set");
		}
		int rank = 1;
		for (Set<Alternative> set : preference) {
			if (set.contains(alter)) {
				LOGGER.debug("alternative rank : {}", rank);
				break;
			}
			rank++;
		}
		return rank;
	}

	/**
	 * 
	 * @param preferences not <code> null </code> a list of sets of alternatives
	 * @return a set of alternatives containing all the alternatives of the list of
	 *         set of alternative given. If an alternative appears several times in
	 *         the list of sets, it appears only once in the new set.
	 */
	public static Set<Alternative> toAlternativeSet(List<Set<Alternative>> preference) {
		LOGGER.debug("toAlternativeSet:");
		Preconditions.checkNotNull(preference);
		Set<Alternative> set = new LinkedHashSet<>();
		for (Set<Alternative> sets : preference) {
			for (Alternative alter : sets) {
				if (!set.contains(alter)) {
					set.add(alter);
				}
			}
		}
		LOGGER.debug("set : {}", set);
		return set;
	}

	/**
	 * 
	 * @param list not <code> null </code>
	 * @return the size of a list of alternative sets
	 */
	public static int size(List<Set<Alternative>> list) {
		LOGGER.debug("list set alternative size:");
		Preconditions.checkNotNull(list);
		int size = 0;
		for (Set<Alternative> set : list) {
			size += set.size();
		}
		LOGGER.debug("size = {}", size);
		return size;
	}

	/**
	 * Factory method for CompletePreferenceImpl
	 *
	 * @param preference <code>not null</code> and all alternatives differents
	 * @return a new CompletePreferenceImpl
	 */
	public static OldCompletePreferenceImpl createCompletePreferenceImpl(List<Set<Alternative>> preference) {
		LOGGER.debug("Preference Factory");
		Preconditions.checkNotNull(preference);
		LOGGER.debug("parameter : {}", preference);
		if (toAlternativeSet(preference).size() != size(preference)) {
			LOGGER.debug("alternative several times in the preference");
			throw new IllegalArgumentException("A preference cannot contain several times the same alternative.");
		}
		return new OldCompletePreferenceImpl(preference);
	}

	/**
	 * 
	 * @return true if the Preference is Strict (without several alternatives having
	 *         the same rank)
	 */
	public boolean isStrict() {
		LOGGER.debug("isStrict:");
		return (preference.size() == size(preference));
	}

	/**
	 * 
	 * @return the StrictPreference built from the preference if the preference is
	 *         strict. If the preference is not strict it throws an
	 *         IllegalArgumentException.
	 */
	public OldLinearPreferenceImpl toStrictPreference() {
		LOGGER.debug("toStrictPreference");
		if (!isStrict()) {
			throw new IllegalArgumentException("the preference is not strict.");
		}
		List<Alternative> list = new ArrayList<>();
		for (Set<Alternative> set : preference) {
			for (Alternative a : set) {
				list.add(a);
			}
		}
		LOGGER.debug("list : {}", list);
		return OldLinearPreferenceImpl.createStrictCompletePreferenceImpl(list);
	}
}
