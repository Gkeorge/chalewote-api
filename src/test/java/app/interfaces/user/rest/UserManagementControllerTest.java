package app.interfaces.user.rest;

import app.domain.user.model.User;
import app.domain.user.model.UserDetails;
import app.domain.user.model.UserRepository;
import app.interfaces.user.dto.SignUpUser;
import app.interfaces.user.rest.assembler.UserResourceAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserManagementController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class UserManagementControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserResourceAssembler userResourceAssembler;

    @Autowired
    private MockMvc mockMvc;


    /**
     * Get all registered users returns all registered users.
     */
    @Test
    void getAllRegisteredUsers() {
    }

    /**
     * Get registered user returns registered user
     *
     * @throws Exception
     */
    @Test
    void getRegisteredUserShouldReturnRegisteredUser() throws Exception {
        User user = setup();
        final UUID userId = user.getUserId();

        given(this.userRepository.loadUserByUserId(userId)).willReturn(user);
        given(this.userResourceAssembler.toResource(user)).willReturn(createUserResource(user));

        getRegisteredUserWithValidUserId(userId);
    }

    private Resource<User> createUserResource(User user) {
        return new Resource<>(user,
                linkTo(methodOn(UserManagementController.class).getRegisteredUser(user.getUserId())).withSelfRel(),
                linkTo(methodOn(UserManagementController.class).getAllRegisteredUsers(null,
                        null))
                        .withRel("users"));
    }

    /**
     * Get unregistered user returns a 404
     *
     * @throws Exception
     */
    @Test
    void getUnRegisteredUserReturnsNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        given(this.userRepository.loadUserByUserId(userId))
                .willThrow(new EntityNotFoundException("User not found"));

        getRegisteredUserWithInvalidUserId(userId);
    }


    /**
     * Register a new user with valid details, registers user
     */
    @Test
    void registerUserWithValidDetailsShouldRegisterUser() throws Exception {
        signUpUser(new SignUpUser("gorkofi@gmail.com", "12334343"), status().isCreated());
    }


    /**
     * Register a new user with invalid details returns a bad request
     */
    @Test
    void registerUserWithInValidDetailsReturnsBadRequest() throws Exception {
        signUpUser(new SignUpUser("gorkofi", "12334343"), status().isBadRequest());
    }


    /**
     * Update registered user with valid details, updates user
     */
    @Test
    void updateRegisteredUserWithValidDetailsShouldUpdateUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserDetails userDetails = UserDetails.builder().firstName("George").lastName("Nanor").build();
        User user = User.builder().userId(userId).userDetails(userDetails).build();
        given(userRepository.updateUserDetails(any(UUID.class), any(UserDetails.class))).willReturn(user);
        given(userResourceAssembler.toResource(user)).willReturn(createUserResource(user));
        this.mockMvc.perform(put("/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(userDetails)))
                .andExpect(handler().handlerType(UserManagementController.class))
                .andExpect(handler().methodName("updateRegisteredUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.userDetails.firstName").value("George"))
                .andExpect(jsonPath("$.userDetails.lastName").value("Nanor"))
                .andDo(document("user"));
    }


    /**
     * Update registered user with invalid details, returns a bad request
     */
    @Test
    void updateRegisteredUserWithInvalidDetailsShouldReturnBadReqeust() {
    }


    /**
     * Delete registered user deletes user
     */
    @Test
    void deleteRegisteredUserShouldDeleteUser() {
    }


    /**
     * Delete unregistered user, returns not found
     * Could be anything actually, 204, 200, 404 since delete is expected to be idempotent
     */
    @Test
    void deleteUnRegisteredUserShouldReturnNotFound() {
    }

    private void getRegisteredUserWithValidUserId(UUID userId) throws Exception {
        this.mockMvc.perform(get("/users/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(handler().handlerType(UserManagementController.class))
                .andExpect(handler().methodName("getRegisteredUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.emailAddress").value("gorkofi@gmail.com"))
                .andExpect(jsonPath("$.links").hasJsonPath())
                .andDo(document("user"));
    }

    private void getRegisteredUserWithInvalidUserId(UUID userId) throws Exception {
        this.mockMvc.perform(get("/users/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(handler().handlerType(UserManagementController.class))
                .andExpect(handler().methodName("getRegisteredUser"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8));
    }

    private User setup() {
        return User.builder().emailAddress("gorkofi@gmail.com").password("123425634").userId(UUID.randomUUID()).build();
    }

    private void signUpUser(SignUpUser user, ResultMatcher resultMatcher) throws Exception {
        this.mockMvc.perform(post("/users")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(resultMatcher);
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}