package recipes.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

    String name;
    String description;
    List<String> ingredients;
    List<String> directions;

    public RecipeDTO(Recipe r){
        this.name = r.getName();
        this.description = r.getDescription();
        this.ingredients = r.getIngredients();
        this.directions = r.getDirections();
    }
}
