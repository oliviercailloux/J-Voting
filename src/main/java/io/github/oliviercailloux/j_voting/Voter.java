package io.github.oliviercailloux.j_voting;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * This class is immutable Contains an integer being the id of the voter
 */
public class Voter implements Comparable<Voter> {
	private static final Logger LOGGER = LoggerFactory.getLogger(Voter.class.getName());

	public static final Voter ZERO = new Voter(0);

	public static Voter v1 = withId(1);

	public static Voter v2 = withId(2);

	public static Voter v3 = withId(3);

	public static Voter v4 = withId(4);

	/**
	 * Factory method for Voter
	 *
	 * @param id <code>not null</code>
	 * @return a new Voter
	 */
	public static Voter withId(int id) {
		checkNotNull(id);
		return new Voter(id);
	}

	private int id;

	private Voter(int id) {
		this.id = id;
	}

	/**
	 * @return the id of the object Voter
	 */
	public int getId() {
		return id;
	}

	/**
	 *
	 * @param v2 not <code> null </code>
	 * @return an integer : 0 if the voters have the same id, &lt 0 if the calling
	 *         voter is smaller than the parameter, else &gt 0.
	 */
	@Override
	public int compareTo(@SuppressWarnings("hiding") Voter v2) {
		LOGGER.debug("compare:");
		Preconditions.checkNotNull(v2);
		LOGGER.debug("calling voter : v1 {}, parameter v2 {}", this.getId(), v2.getId());
		return this.getId() - v2.getId();
	}

	/**
	 * @param o2 <code> not null</code>
	 * @return whether two voters are equal, ie have the same id.
	 */
	@Override
	public boolean equals(Object o2) {
		if (this == o2) {
			return true;
		}
		if (o2 == null) {
			return false;
		}
		if (this.getClass() != o2.getClass()) {
			return false;
		}
		Voter voter = (Voter) o2;
		return this.getId() == voter.getId();
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).addValue(id).toString();
	}
}
