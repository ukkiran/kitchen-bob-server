package de.robertdey.kitchenbob.repositories;

import de.robertdey.kitchenbob.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
