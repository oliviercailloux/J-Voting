package io.github.oliviercailloux.y2018.j_voting;

/**
 *A StrictProfile represents a complete StrictProfile. The preferences are strict. The preferences are about the same alternatives exactly.
 *
 */
public interface StrictProfile extends StrictProfileI,Profile{

	/**
	 * 
	 * @param v a voter not <code>null</code>
	 * @return the StrictPreference of the voter v in the profile.
	 */
	@Override
	public StrictPreference getPreference(Voter v);
}
