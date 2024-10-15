package recipes.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeInputDTO {

    String name;
    String description;
    String category;
    List<String> ingredients;
    List<String> directions;

    public RecipeInputDTO(Recipe r){
        this.name = r.getName();
        this.description = r.getDescription();
        this.category = r.getCategory();
        this.ingredients = r.getIngredients();
        this.directions = r.getDirections();
    }
}