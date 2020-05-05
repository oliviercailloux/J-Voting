package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ForwardingSet;

import io.github.oliviercailloux.j_voting.Alternative;

public class MutableLinearSetDecorator extends ForwardingSet<Alternative> {
	
	protected Set<Alternative> delegate;

	@Override
	protected Set<Alternative> delegate() {
		return delegate;
	}
	
	@Override
	public boolean add(Alternative a) {
		return false;
		//throw new IOException("You are trying to modify a Set<Alternative> from MutableLinearPreference");
	}
	
	@Override
	public boolean addAll(Collection<? extends Alternative> c) {
		return false;
		//throw new IOException("You are trying to modify a Set<Alternative> from MutableLinearPreference");
	}
	
	

}
