package io.github.oliviercailloux.j_voting;

import com.google.common.collect.ImmutableSet;

public class Generator {

	public static Alternative a1 = Alternative.withId(1);
	public static Alternative a2 = Alternative.withId(2);
	public static Alternative a3 = Alternative.withId(3);
	public static Alternative a4 = Alternative.withId(4);

	public static Voter v1 = Voter.withId(1);
	public static Voter v2 = Voter.withId(2);
	public static Voter v3 = Voter.withId(3);
	public static Voter v4 = Voter.withId(4);

	public static ImmutableSet<Alternative> getAlternatives(int m) {
		final ImmutableSet.Builder<Alternative> builder = ImmutableSet.builder();
		for (int i = 1; i <= m; ++i) {
			builder.add(Alternative.withId(i));
		}
		return builder.build();
	}

	public static ImmutableSet<Voter> getVoters(int n) {
		final ImmutableSet.Builder<Voter> builder = ImmutableSet.builder();
		for (int i = 1; i <= n; ++i) {
			builder.add(Voter.withId(i));
		}
		return builder.build();
	}

	/**
	 * Create private constructor because of the static class
	 */
	private Generator() {
		throw new IllegalStateException("Utility Class");
	}
}
