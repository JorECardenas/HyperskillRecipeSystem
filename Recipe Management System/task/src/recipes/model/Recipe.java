package recipes.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RECIPES")
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "name")
    @NotBlank
    String name;

    @Column(name = "description")
    @NotBlank
    String description;

    @Column(name = "ingredients")
    List<String> ingredients = new ArrayList<>();

    @Column(name = "directions")
    List<String> directions = new ArrayList<>();

    public Recipe(RecipeDTO dto){
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.ingredients = dto.getIngredients();
        this.directions = dto.getDirections();
    }


    public RecipeIdDTO getIdDTO(){
        return new RecipeIdDTO(id);
    }


}
