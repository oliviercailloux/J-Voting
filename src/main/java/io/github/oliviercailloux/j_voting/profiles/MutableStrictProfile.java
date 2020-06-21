package io.github.oliviercailloux.j_voting.profiles;

import java.util.ArrayList;
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
 * This class is Mutable. This is a strict Profile that will be used for the GUI model.
 */
public class MutableStrictProfile {

	private Map<Voter,MutableLinearPreference> profile;
	private BiMap<Alternative,String> alternativeNames;
	private BiMap<Voter,String> voterNames;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MutableStrictProfile.class.getName());
	
	public static MutableStrictProfile given(Map<Voter, MutableLinearPreference> profile, BiMap<Alternative, String> alternativeNames, BiMap<Voter, String> voterNames) {
		LOGGER.debug("MutableLinearPreferenceImpl given");
		Preconditions.checkNotNull(profile);
		Preconditions.checkNotNull(alternativeNames);
		Preconditions.checkNotNull(voterNames);
		
		return new MutableStrictProfile(profile,alternativeNames,voterNames);
	}
	
	private MutableStrictProfile(Map<Voter, MutableLinearPreference> profile, BiMap<Alternative, String> alternativeNames, BiMap<Voter, String> voterNames) {
		super();
		this.profile = profile;
		this.alternativeNames = alternativeNames;
		this.voterNames = voterNames;
	}
	
	/**
	 * Add a new voter to the Profile. The Voter takes a default Preference which is defined according the Set<Alternative> of the other preferences. It is added with a default name.
	 * 
	 * @param v not <code> null</code> the new Voter
     * @return true if the Voter is not already in the Profile
	 */
	public boolean addVoter(Voter v) {
		
		List<Alternative> newList = new ArrayList<>();		
	    
		Set<Alternative> set = this.getAlternatives();
	    
	    for (Alternative a : set) 
	    	newList.add(a); 	
		
		MutableLinearPreference preference = MutableLinearPreferenceImpl.given(v,newList);
		profile.put(v, preference);
		
		String voterName = "Voter "+v.getId();
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
		profile.remove(v);
		voterNames.remove(v);
		return true;
	} 
	
	/**
	 * Rename a Voter in the Profile.
	 * 
	 * @param v not <code> null</code> the Voter to rename
	 * @param newName not <code> null</code> the new name
     * @return true if the name has changed after this call
	 */
	public boolean renameVoter(Voter v, String newName) {
		voterNames.replace(v, newName);
		return true;
	}
	
	/**
	 * Add a new alternative to the Profile. The alternative is added with a default name at the end of each preference of the profile.
	 * 
	 * @param a a non existing alternative
     * @return true
	 */
	public boolean addAlternative(Alternative a) {
		
		for(Voter v : getVoters()) {
			getPreference(v).addAlternative(a);
		}
		
		String alternativeName = "Alternative "+a.getId();
		
		alternativeNames.put(a, alternativeName);
		
		return true;
	}
	
	/**
	 * Remove an existing alternative to the Profile.
	 * 
	 * @param a an existing alternative
     * @return true
	 */
	public boolean removeAlternative(Alternative a) {
		
		for(Voter v : getVoters()) {
			getPreference(v).removeAlternative(a);
		}
		
		alternativeNames.remove(a);
		
		return true;
	}
	
	/**
	 * Rename an Alternative in the Profile. The new name will be used in all the Preferences.
	 * 
	 * @param a not <code> null</code> the Alternative to rename
	 * @param newName not <code> null</code> the new name
     * @return true if the name has changed after this call
	 */
	public boolean renameAlternative(Alternative a, String newName) {
		alternativeNames.replace(a, newName);
		return true;
	}
	
	/**
	 * Get a Set containing all the Voters of the Profile.
	 * 
	 * @return Set<Voter> 
	 */
	public Set<Voter> getVoters() {
		return profile.keySet();
	}
	
	/**
	 * Get a Set containing all the Alternatives of the Profile.
	 * 
	 * @return Set<Alternative> 
	 */
	public Set<Alternative> getAlternatives() {
		Set<Entry<Voter, MutableLinearPreference>> setProfile = profile.entrySet();	      
	    Iterator<Entry<Voter, MutableLinearPreference>> it = setProfile.iterator();	 	    
		return it.next().getValue().getAlternatives();
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
		return profile.equals(msp2.profile) && alternativeNames.equals(msp2.alternativeNames) && voterNames.equals(msp2.voterNames);
	}
}
