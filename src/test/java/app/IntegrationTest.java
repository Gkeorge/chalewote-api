package app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getUserReturnsUserDetails() throws Exception {

        //arrange

        //act
//        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/23",
//                User.class);
//
//        //assert
//        assertThat(response.getStatusCode().is2xxSuccessful());
//        assertThat(response.getBody().getUsername()).isEqualTo("");
//        assertThat(response.getBody().getEmailAddress()).isEqualTo("");

    }
}
