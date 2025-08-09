package de.featjar.feature.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ShellSession {
	
	private static class StoredElement<T> {
		private Class<T> type;
		private T element;
		
		public StoredElement(Class<T> type, T element) {
			this.type = type;
			this.element = element;
		}
		
	}
	
	private final Map<String, StoredElement<?>> elements;	
	
	@SuppressWarnings("unchecked")
	public <T> Optional<T> get(String key, Class<T> type) {
		StoredElement<?> storedElement = elements.get(key);
		
		if(storedElement == null) {
			return Optional.empty();
		}		
		if (storedElement.type == type) {
			return Optional.of((T) storedElement.type.cast(storedElement.element));
		} else {
			throw new RuntimeException("???"); // TODO Result<T> von Problem addProblem
		}
	}	
	
	public Optional<String> getType(String key) {
		return Optional.ofNullable(elements.get(key)).map(e -> e.type.getSimpleName());
	}
	
	public Optional<Object> getElement(String key) {
		return Optional.ofNullable(elements.get(key)).map(e -> e.element);
	}
	
	public ShellSession() {		
        elements = new LinkedHashMap<>();        
	}

	public <T> void put(String key, T element, Class<T> type) {
		elements.put(key, new StoredElement<T>(type, element));		
	}
	
	public Optional<?> remove(String key) {
		return Optional.ofNullable(elements.remove(key));		
	}
	
	public void clear() {
		elements.clear();
	}
	
	public int getSize() {
		return elements.size();
	}
	
	public Optional<?> getKeySet() {
		return Optional.of(elements.keySet());
	}
	
	public void printKeySet() {
		// TODO sort, group (type or name)
		elements.keySet().forEach(ks -> System.out.println(ks + " (" + getType(ks).orElse("?") + ")"));
		// TODO use always entrySet
//		elements.keySet().stream().map(s -> getType(s).orElse("?")).sorted().forEach(s -> System.out.println(s));
	}
}
