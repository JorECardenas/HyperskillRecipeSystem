package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import recipes.model.RecipeDTO;
import recipes.model.RecipeIdDTO;
import recipes.service.RecipeService;

@RestController("/api")
public class RecipeController {

    RecipeService service;

    @Autowired
    public RecipeController(RecipeService s){
        this.service = s;
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<RecipeIdDTO> saveRecipe(@RequestBody RecipeDTO recipe){
        RecipeIdDTO id = service.put(recipe);

        return new ResponseEntity<>(id, HttpStatus.OK);

    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id){
        RecipeDTO dto = service.get(id);

        if(dto != null){
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id){
        boolean deleted = service.delete(id);


        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }


}
