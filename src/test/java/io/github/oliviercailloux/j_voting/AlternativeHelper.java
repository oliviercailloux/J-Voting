package io.github.oliviercailloux.j_voting;

import com.google.common.collect.ImmutableSet;

public class AlternativeHelper {

    public static Alternative a1 = Alternative.withId(1);
    public static Alternative a2 = Alternative.withId(2);
    public static Alternative a3 = Alternative.withId(3);
    public static Alternative a4 = Alternative.withId(4);
    public static Alternative a5 = Alternative.withId(5);
    public static ImmutableSet<Alternative> a12 = ImmutableSet.of(a1, a2);
    public static ImmutableSet<Alternative> a34 = ImmutableSet.of(a3, a4);
}
