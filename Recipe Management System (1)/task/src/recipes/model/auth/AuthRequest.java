package recipes.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @Email(regexp = ".+[@].+[\\.].+")
    @NotBlank
    String email;

    @NotBlank
    @Size(min = 8)
    String password;



}
