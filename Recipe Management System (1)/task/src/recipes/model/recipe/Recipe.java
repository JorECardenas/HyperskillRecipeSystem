package recipes.model.recipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import recipes.model.auth.AppUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RECIPES")
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    long id;

    @Column(name = "name")
    @NotBlank
    String name;

    @Column(name = "description")
    @NotBlank
    String description;

    @Column(name = "category")
    @NotBlank
    String category;

    @Column(name = "date")
    @CreatedDate
    @UpdateTimestamp
    LocalDateTime date;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "INGREDIENTS", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "ingredients")
    List<String> ingredients = new ArrayList<>();

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "DIRECTIONS", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "directions")
    List<String> directions = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    AppUser user;


    public Recipe(RecipeInputDTO dto, AppUser user) {
        this.name = dto.getName();
        this.category = dto.getCategory();
        this.description = dto.getDescription();
        this.ingredients = dto.getIngredients();
        this.directions = dto.getDirections();

        this.user = user;
    }

    public Recipe(RecipeInputDTO dto, Long id, AppUser user) {
        this.id = id;
        this.name = dto.getName();
        this.category = dto.getCategory();
        this.description = dto.getDescription();
        this.ingredients = dto.getIngredients();
        this.directions = dto.getDirections();

        this.user = user;
    }


    public RecipeIdDTO getIdDTO(){
        return new RecipeIdDTO(id);
    }

    public RecipeDTO toDTO(){ return new RecipeDTO(this); }


}
