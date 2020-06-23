package io.github.oliviercailloux.j_voting.profiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;

import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;
import io.github.oliviercailloux.j_voting.Alternative;

/**
 * This class is Mutable. This is a strict Profile that will be used for the GUI
 * model. This profile forces all preferences to contain the same alternatives.
 */
public class MutableStrictProfile {

	private Map<Voter, MutableLinearPreference> profile;
	private BiMap<Alternative, String> alternativeNames;
	private BiMap<Voter, String> voterNames;

	private static final Logger LOGGER = LoggerFactory.getLogger(MutableStrictProfile.class.getName());

	public static MutableStrictProfile given(Map<Voter, MutableLinearPreference> profile,
			BiMap<Alternative, String> alternativeNames, BiMap<Voter, String> voterNames) {
		LOGGER.debug("MutableLinearPreferenceImpl given");
		return new MutableStrictProfile(profile, alternativeNames, voterNames);
	}
	
	private MutableStrictProfile(Map<Voter, MutableLinearPreference> profile,
			BiMap<Alternative, String> alternativeNames, BiMap<Voter, String> voterNames) {
		Preconditions.checkNotNull(profile);
		Preconditions.checkNotNull(alternativeNames);
		Preconditions.checkNotNull(voterNames);

		this.profile = profile;
		this.alternativeNames = alternativeNames;
		this.voterNames = voterNames;
	}
	
	public static MutableStrictProfile empty() {
		LOGGER.debug("MutableLinearPreferenceImpl empty");
		return new MutableStrictProfile();
	}
	
	private MutableStrictProfile() {
		this.profile = null;
		this.alternativeNames = null;
		this.voterNames = null;
	}

	/**
	 * Add a new voter to the Profile. The voter takes a default preference which is
	 * defined according to the alphabetical order of the alternatives in the
	 * profile. It is added with a default name. If the voter's already in the
	 * profile, the method does nothing.
	 * 
	 * @param v not <code> null</code> the new Voter
	 * @return true if the Voter is not already in the Profile
	 */
	public boolean addVoter(Voter v) {

		if (profile.containsKey(v)) {
			return false;
		}

		List<Alternative> newList = new ArrayList<>();
		List<String> alternativeNamesList = new ArrayList<>();

		for (Iterator<Entry<Alternative, String>> iterator = alternativeNames.entrySet().iterator(); iterator
				.hasNext();) {
			Entry<Alternative, String> mapentry = iterator.next();
			alternativeNamesList.add(mapentry.getValue());
		}

		Collections.sort(alternativeNamesList);

		for (String s : alternativeNamesList)
			newList.add(alternativeNames.inverse().get(s));

		MutableLinearPreference preference = MutableLinearPreferenceImpl.given(v, newList);
		profile.put(v, preference);

		String voterName = "Voter " + v.getId();
		voterNames.put(v, voterName);

		return true;
	}

	/**
	 * Remove a Voter of the Profile. Its Preference is removed too.
	 * 
	 * @param v not <code> null</code> the Voter to remove
	 * @return true if the Voter is in the Profile
	 */
	public boolean removeVoter(Voter v) {
		if ((profile.containsKey(v) && !(voterNames.containsKey(v)))
				&& (!(profile.containsKey(v)) && voterNames.containsKey(v))
				&& (!(profile.containsKey(v) || voterNames.containsKey(v)))) {
			return false;
		}

		profile.remove(v);
		voterNames.remove(v);

		return true;
	}

	/**
	 * Rename a Voter in the Profile.
	 * 
	 * @param v       not <code> null</code> the Voter to rename
	 * @param newName not <code> null</code> the new name
	 * @return true if the name has changed after this call
	 */
	public boolean renameVoter(Voter v, String newName) {

		if ((profile.containsKey(v) && !(voterNames.containsKey(v)))
				&& (!(profile.containsKey(v)) && voterNames.containsKey(v))
				&& (!(profile.containsKey(v) || voterNames.containsKey(v)))) {
			return false;
		}

		voterNames.replace(v, newName);
		return true;
	}

	/**
	 * Add a new alternative to the Profile. The alternative is added with a default
	 * name at the end of each preference of the profile.
	 * 
	 * @param a a non existing alternative
	 * @return true if the Alternative is not already in the Profile
	 */
	public boolean addAlternative(Alternative a) {

		if (alternativeNames.containsKey(a)) {
			return false;
		}

		for (Voter v : getVoters()) {
			getPreference(v).addAlternative(a);
		}

		String alternativeName = "Alternative " + a.getId();
		alternativeNames.put(a, alternativeName);

		return true;
	}

	/**
	 * Remove an existing alternative to the Profile.
	 * 
	 * @param a an existing alternative
	 * @return true if the Alternative is in the Profile
	 */
	public boolean removeAlternative(Alternative a) {

		if (!(alternativeNames.containsKey(a))) {
			return false;
		}

		for (Voter v : getVoters()) {
			getPreference(v).removeAlternative(a);
		}

		alternativeNames.remove(a);
		return true;
	}

	/**
	 * Rename an Alternative in the Profile. The new name will be used in all the
	 * Preferences.
	 * 
	 * @param a       not <code> null</code> the Alternative to rename
	 * @param newName not <code> null</code> the new name
	 * @return true if the name has changed after this call
	 */
	public boolean renameAlternative(Alternative a, String newName) {

		if (!(alternativeNames.containsKey(a))) {
			return false;
		}

		alternativeNames.replace(a, newName);
		return true;
	}

	/**
	 * Get an unmodifiable Set containing all the Voters of the Profile. <br>
	 * <br>
	 * FUTURE : If the voting set becomes editable, a decorator should be
	 * implemented to maintain the profile.
	 * 
	 * @return Set<Voter>
	 */
	public Set<Voter> getVoters() {
		return Collections.unmodifiableSet(profile.keySet());
	}

	/**
	 * Get a Set containing all the Alternatives of the Profile.
	 * 
	 * @return Set<Alternative>
	 */
	public Set<Alternative> getAlternatives() {
		Set<Entry<Voter, MutableLinearPreference>> setProfile = profile.entrySet();
		Iterator<Entry<Voter, MutableLinearPreference>> it = setProfile.iterator();
		Set<Alternative> copySet = it.next().getValue().getAlternatives();
		return copySet;
	}

	/**
	 * Get the preference of a specified Voter.
	 * 
	 * @param v not <code> null</code> the Voter chosen
	 * @return MutableLinearPreference
	 */
	public MutableLinearPreference getPreference(Voter v) {
		return profile.get(v);
	}

	@Override
	public int hashCode() {
		return Objects.hash(profile, alternativeNames, voterNames);
	}

	@Override
	public boolean equals(Object o2) {
		if (!(o2 instanceof MutableStrictProfile)) {
			return false;
		}
		if (this == o2) {
			return true;
		}
		MutableStrictProfile msp2 = (MutableStrictProfile) o2;
		return profile.equals(msp2.profile) && alternativeNames.equals(msp2.alternativeNames)
				&& voterNames.equals(msp2.voterNames);
	}
}
