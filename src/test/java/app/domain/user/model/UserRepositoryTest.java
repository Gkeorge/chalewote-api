package app.domain.user.model;

import app.infrastructure.user.persistence.jpa.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findAll() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void updateUser() {
    }

//    @Test
//    void findUserById() {
//        User user = testEntityManager.persistFlushFind(User.createUser("gorkofi@gmail.com", "12342678"));
//        Optional<User> byId = jpaUserRepository.findById(user.getId());
//        assertAll("",
//                () -> assertTrue(byId.isPresent(), "KOLO"));
//    }

    @Test
    void deleteRegisteredUser() {
    }

    @Test
    void checkIfEmailExsits() {
    }
}