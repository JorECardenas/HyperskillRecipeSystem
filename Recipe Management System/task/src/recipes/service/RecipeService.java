package recipes.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.model.RecipeDTO;
import recipes.model.RecipeIdDTO;
import recipes.repository.RecipeRepository;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository repo;

    public RecipeIdDTO put(RecipeDTO dto){

        Recipe newRecipe = new Recipe(dto);

        Recipe rec = repo.save(newRecipe);

        return new RecipeIdDTO(rec.getId());
    }

    public RecipeDTO get(Long id){
        Optional<Recipe> info = repo.findById(id);

        return info.map(RecipeDTO::new)
                   .orElse(null);
    }

    public boolean delete(Long id){
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }

    }


}
