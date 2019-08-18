package app.infrastructure.user.persistence.jpa;

import app.domain.user.model.User;
import app.domain.user.model.UserDetails;
import app.domain.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class DefualtUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return jpaUserRepository.findAll(pageable);
    }

    @Override
    public UUID signUpUser(String emailAddress, String password) {
        return jpaUserRepository.save(User.builder().emailAddress(emailAddress).password(password).build()).getUserId();
    }

    @Override
    public User updateUserDetails(UUID userId, UserDetails userDetails) {
        return jpaUserRepository.findById(userId).map(u -> update(u, userDetails))
                .orElseThrow(() -> new EntityNotFoundException("User not found for ID: " + userId));
    }

    private User update(User user, UserDetails userDetails) {
        return jpaUserRepository.save(buildUser(user.getUserId(), user.getEmailAddress(), user.getPassword(), userDetails));
    }

    @Override
    public User loadUserByUserId(UUID userId) {
        return jpaUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found for ID: " + userId));
    }

    @Override
    public void deleteUser(UUID userId) {
        boolean present = jpaUserRepository.findById(userId)
                .isPresent();
        if (present)
            jpaUserRepository.deleteById(userId);
        else
            throw new EntityNotFoundException("User not found for ID: " + userId);
    }

    @Override
    public boolean checkIfEmailExsits(String email) {
        return false;
    }

    @Override
    public void changePassword(UUID userId, String currentPassword, String newPassword) {
        User user = loadUserByUserId(userId);
        assertThatCurrentPasswordMatchesOrThrowException(user.getPassword(), currentPassword);
        jpaUserRepository.save(buildUser(userId, user.getEmailAddress(), newPassword, user.getUserDetails()));
    }

    private void assertThatCurrentPasswordMatchesOrThrowException(String registeredPassword, String passwordToCheck) {

    }

    @Override
    public void changeEmailAddress(UUID userId, String newEmailAddress, String password) {
        User user = loadUserByUserId(userId);
        assertThatCurrentPasswordMatchesOrThrowException(user.getPassword(), password);
        jpaUserRepository.save(buildUser(userId, newEmailAddress, password, user.getUserDetails()));
    }

    private User buildUser(UUID userId, String emailAddress, String password, UserDetails userDetails) {
        return User.builder().userId(userId).emailAddress(emailAddress)
                .password(password).userDetails(userDetails).build();
    }


}
