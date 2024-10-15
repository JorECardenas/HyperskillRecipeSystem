package recipes.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.model.auth.AppUser;
import recipes.model.auth.AuthRequest;
import recipes.service.UserService;

@RestController
public class UserController {

    UserService service;
    PasswordEncoder encoder;


    @Autowired
    public UserController(UserService service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest account) {

       try {

           service.register(account);


           return new ResponseEntity<>(account.getEmail() + "has been registered.", HttpStatus.OK);

       } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }





}
