package io.github.oliviercailloux.j_voting.preferences.classes;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingSet;

import io.github.oliviercailloux.j_voting.Alternative;

public class MutableLinearSetDecorator extends ForwardingSet<Alternative> {

	protected MutableLinearPreferenceImpl delegate;
	private static final Logger LOGGER = LoggerFactory.getLogger(MutableLinearPreferenceImpl.class.getName());

	private MutableLinearSetDecorator(MutableLinearPreferenceImpl delegate) {
		this.delegate = delegate;
	}

	public static MutableLinearSetDecorator given(MutableLinearPreferenceImpl delegate) {
		LOGGER.debug("MutableLinearSetDecorator given");
		Preconditions.checkNotNull(delegate);
		return new MutableLinearSetDecorator(delegate);
	}

	@Override
	protected Set<Alternative> delegate() {
		return delegate.alternatives;
	}

	@Override
	public boolean add(Alternative a) {
		LOGGER.debug("MutableLinearSetDecorator add");
		return delegate.addAlternative(a);
	}

	@Override
	public boolean addAll(Collection<? extends Alternative> c) {
		LOGGER.debug("MutableLinearSetDecorator addAll");
				
		for (Iterator<? extends Alternative> iterator = c.iterator(); iterator.hasNext();) {
			Alternative alternative = iterator.next();
			delegate.addAlternative(alternative);		
		}
		return true;
	}
	
	@Override
	public boolean remove(Object o) {
		LOGGER.debug("MutableLinearSetDecorator remove");
		
		if (o instanceof Alternative) {
			Alternative a = (Alternative) o;
			return delegate.removeAlternative(a);
		}
		return false;		
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		LOGGER.debug("MutableLinearSetDecorator removeAll");
		
		for (Iterator<?> iterator = c.iterator(); iterator.hasNext();) {
			Alternative alternative = (Alternative) iterator.next();
			delegate.removeAlternative(alternative);	
		}		
		return true;		
	}
	
	@Override
	public void clear() {
		LOGGER.debug("MutableLinearSetDecorator clear");
		
		int size = delegate.list.size();
		
		for( int i = 0; i < size; i++) {
			delegate.removeAlternative(delegate.list.get(0));
		}	
	}

}
