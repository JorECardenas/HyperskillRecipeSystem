package recipes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import recipes.model.auth.AppUser;
import recipes.model.recipe.Recipe;
import recipes.model.recipe.RecipeDTO;
import recipes.model.recipe.RecipeIdDTO;
import recipes.model.recipe.RecipeInputDTO;
import recipes.repository.RecipeRepository;

@Service
public class RecipeService {

    RecipeRepository repo;

    @Autowired
    public RecipeService(RecipeRepository repo) {
        this.repo = repo;
    }

    public RecipeIdDTO add(RecipeInputDTO dto, AppUser user) {


        Recipe newRecipe = new Recipe(dto, user);

        Recipe rec = repo.save(newRecipe);

        return new RecipeIdDTO(rec.getId());
    }

    public Recipe get(Long id){
        Optional<Recipe> info = repo.findById(id);

        return info.orElseThrow();
    }

    public boolean delete(Long id){
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    public boolean update(RecipeInputDTO dto, Long id, AppUser user){

        if(repo.findById(id).isEmpty()){
            return false;
        }

        Recipe newRecipe = new Recipe(dto, id, user);

        repo.updateRecipe(newRecipe);

        return true;

    }

    public List<RecipeDTO> search(String cat, String name){
        if(cat == null){
            return repo.findAllByNameContainingIgnoreCaseOrderByDateDesc(name).stream().map(Recipe::toDTO).toList();
        }

        return repo.findByCategoryIgnoreCaseOrderByDateDesc(cat).stream().map(Recipe::toDTO).toList();


    }


}
