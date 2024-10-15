package recipes.model.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    Long id;

    @NotNull
    @Column(name = "email")
    String email;

    @NotBlank
    @Column(name = "password")
    String password;

    @Column(name = "authority")
    String authority;


    public AppUser(AuthRequest account, String encodedPassword) {
        this.email = account.getEmail();
        this.password = encodedPassword;
        this.authority = "ROLE_USER";

    }




}
