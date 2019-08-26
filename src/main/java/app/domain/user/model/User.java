package app.domain.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;


@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class User {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID userId;

    @NotBlank
    @Email
    @Column(name = "email_address", unique = true)
    String emailAddress;

    @JsonIgnore
    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9-@#%$^()!]{6,12})$",
            message = "Password does not meet the requirements")
    private String password;

    @Embedded
    private UserDetails userDetails;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return userId != null ? userId.equals(user.userId) : user.userId == null;
    }


    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
