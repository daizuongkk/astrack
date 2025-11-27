package repository;

import java.util.List;

public interface ITypeRepository {
	List<String> loadTypes(String username);

	void saveTypes(String username, List<String> types);
}