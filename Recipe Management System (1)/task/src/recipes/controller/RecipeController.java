package recipes.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import recipes.model.auth.AppUser;
import recipes.model.auth.AppUserAdapter;
import recipes.model.auth.AuthRequest;
import recipes.model.recipe.Recipe;
import recipes.model.recipe.RecipeDTO;
import recipes.model.recipe.RecipeIdDTO;
import recipes.model.recipe.RecipeInputDTO;
import recipes.service.RecipeService;
import recipes.service.UserService;

import java.util.List;

@RestController
public class RecipeController {

    RecipeService service;

    UserService userService;


    @Autowired
    public RecipeController(RecipeService s, UserService u) {
        this.service = s;
        this.userService = u;
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<RecipeIdDTO> saveRecipe(@Valid @RequestBody RecipeInputDTO recipe,
                                                  @AuthenticationPrincipal AppUserAdapter auth){

        RecipeIdDTO id;
        AppUser author;

        try {
            author = userService.getUser(auth.getUsername());

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }




        try {
            id = service.add(recipe, author);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);

    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id){
        try {
            RecipeDTO dto = service.get(id).toDTO();
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id,
                                          @AuthenticationPrincipal AppUserAdapter user){

        try{
            if(!service.get(id).getUser().getEmail().equals(user.getUsername())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }


        boolean deleted = service.delete(id);

        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id,
                                          @Valid @RequestBody RecipeInputDTO recipeDTO,
                                          @AuthenticationPrincipal AppUserAdapter user){

        try{
            if(!service.get(id).getUser().getEmail().equals(user.getUsername())){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }


        try {

            AppUser author = userService.getUser(user.getUsername());

            boolean updated = service.update(recipeDTO, id, author);

            if(updated){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/api/recipe/search/")
    public ResponseEntity<List<RecipeDTO>> searchRecipe(@RequestParam(required = false, value = "") String category,
                                                        @RequestParam(required = false, value = "") String name){

        if (category == null && name == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(category != null && name != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        try {
            List<RecipeDTO> info = service.search(category, name);

            return new ResponseEntity<>(info, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }



    }


}
