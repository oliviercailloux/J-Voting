package io.github.oliviercailloux.y2018.j_voting;

import com.google.common.collect.ImmutableSet;

public class Generator {

	public static ImmutableSet<Alternative> getAlternatives(int m) {
		final ImmutableSet.Builder<Alternative> builder = ImmutableSet.builder();
		for (int i = 1; i <= m; ++i) {
			builder.add(new Alternative(i));
		}
		return builder.build();
	}

	public static ImmutableSet<Voter> getVoters(int n) {
		final ImmutableSet.Builder<Voter> builder = ImmutableSet.builder();
		for (int i = 1; i <= n; ++i) {
			builder.add(new Voter(i));
		}
		return builder.build();
	}

}
