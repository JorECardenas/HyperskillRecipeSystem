package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.model.auth.AppUser;
import recipes.model.auth.AppUserAdapter;
import recipes.model.auth.AuthRequest;
import recipes.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    UserRepository repo;
    PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }


    public void register(AuthRequest acc){

        if(repo.findByEmail(acc.getEmail()) != null){
            throw new DuplicateKeyException("Username already exists");
        }

        String encoded = encoder.encode(acc.getPassword());

        AppUser user = new AppUser(acc, encoded);


        repo.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            AppUser acc = repo.findByEmail(username);

            if(acc == null){
                throw new UsernameNotFoundException("Username not found");
            }

            return new AppUserAdapter(acc);

        } catch (Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }




    }

    public AppUser getUser(String email){
        return repo.findByEmail(email);
    }



}
