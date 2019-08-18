package app.domain.user.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserRepository {

    Page<User> findAll(Pageable pageable);

    UUID signUpUser(String emailAddress, String password);

    User updateUserDetails(UUID userId, UserDetails userDetails);

    User loadUserByUserId(UUID userId);

    void deleteUser(UUID userId);

    boolean checkIfEmailExsits(String email);

    void changePassword(UUID userId, String oldPassword, String newPassword);

    void changeEmailAddress(UUID userId, String emailAddress, String password);

}
