package app.interfaces.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SignUpUser {

    @Email(message = "Valid email required")
    @NotBlank(message = "Email address cannot be blank")
    private String emailAddress;

    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^([a-zA-Z0-9-@#%$^!]{6,12})$", message = "Password does not meet the requirements")
    private String password;
}
