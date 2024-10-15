package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
