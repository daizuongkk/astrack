package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import repository.ITypeRepository;

public class TypeService {
	private final ITypeRepository typeRepository;
	private final String username;

	public TypeService(ITypeRepository typeRepository, String username) {
		this.typeRepository = typeRepository;
		this.username = username;
	}

	public List<String> getAllTypes() {
		try {
			List<String> persisted = typeRepository.loadTypes(username);
			return new ArrayList<>(persisted);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public void addType(String type) {
		if (type == null || type.isBlank())
			return;
		try {
			List<String> current = new ArrayList<>(typeRepository.loadTypes(username));
			Set<String> set = new HashSet<>(current);
			if (!set.contains(type)) {
				current.add(type);
				typeRepository.saveTypes(username, current);
			}
		} catch (Exception e) {
		}
	}

	public void removeType(String type) {
		if (type == null)
			return;
		try {
			List<String> current = new ArrayList<>(typeRepository.loadTypes(username));
			if (current.remove(type)) {
				typeRepository.saveTypes(username, current);
			}
		} catch (Exception e) {
		}
	}
}
