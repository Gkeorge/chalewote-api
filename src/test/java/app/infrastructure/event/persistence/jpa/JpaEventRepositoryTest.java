package app.infrastructure.event.persistence.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class JpaEventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void findByUserIdAndEventId() {
    }

    @Test
    void findAllForUser() {
    }
}