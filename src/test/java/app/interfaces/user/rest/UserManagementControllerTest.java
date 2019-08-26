package app.interfaces.user.rest;

import app.domain.user.model.User;
import app.domain.user.model.UserDetails;
import app.domain.user.model.UserRepository;
import app.interfaces.user.dto.SignUpUser;
import app.interfaces.user.rest.assembler.UserResourceAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserManagementController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
@DisplayName("User Management Test 😱")
public class UserManagementControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserResourceAssembler userResourceAssembler;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getAllRegisteredUsers() {
    }

    @Test
    void getRegisteredUserShouldReturnRegisteredUser() throws Exception {
        User user = setup();
        final UUID userId = user.getUserId();

        given(this.userRepository.loadUserByUserId(userId)).willReturn(user);
        given(this.userResourceAssembler.toResource(user)).willReturn(createUserResource(user));

        getRegisteredUserWithValidUserId(userId);
    }

    private User setup() {
        return User.builder().emailAddress("gorkofi@gmail.com").password("123425634").userId(UUID.randomUUID()).build();
    }

    private Resource<User> createUserResource(User user) {
        return new Resource<>(user,
                linkTo(methodOn(UserManagementController.class).getRegisteredUser(user.getUserId())).withSelfRel(),
                linkTo(methodOn(UserManagementController.class).getAllRegisteredUsers(null,
                        null))
                        .withRel("users"));
    }

    private void getRegisteredUserWithValidUserId(UUID userId) throws Exception {
        getContent(userId, status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.emailAddress").value("gorkofi@gmail.com"))
                .andExpect(jsonPath("$.links").hasJsonPath())
                .andDo(document("user"));
    }

    private ResultActions getContent(UUID userId, ResultMatcher resultMatcher) throws Exception {
        return this.mockMvc.perform(get("/users/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(handler().handlerType(UserManagementController.class))
                .andExpect(handler().methodName("getRegisteredUser"))
                .andExpect(resultMatcher);
    }

    @Test
    void getUnRegisteredUserReturnsNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        given(this.userRepository.loadUserByUserId(userId))
                .willThrow(new EntityNotFoundException("User not found"));

        getRegisteredUserWithInvalidUserId(userId);
    }

    private void getRegisteredUserWithInvalidUserId(UUID userId) throws Exception {
        getContent(userId, status().isNotFound());
    }

    @Test
    void registerUserWithValidDetailsShouldRegisterUser() throws Exception {
        given(userRepository.signUpUser("gorkofi@gmail.com", "12334343")).willReturn(UUID.randomUUID());
        ResultActions resultActions = signUpUser(new SignUpUser("gorkofi@gmail.com", "12334343"), status().isCreated());
        resultActions.andExpect(header().exists("Location"));
    }

    private ResultActions signUpUser(SignUpUser user, ResultMatcher resultMatcher) throws Exception {
        return this.mockMvc.perform(post("/users")
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

    @Test
    void registerUserWithInValidDetailsReturnsBadRequest() throws Exception {
        signUpUser(new SignUpUser("gorkofi", "12334343"), status().isBadRequest());
    }

    @Test
    void updateRegisteredUserWithValidDetailsShouldUpdateUser() throws Exception {
        User user = setupDetailsForUserUpdate("George", "Nanor");
        postContent(user.getUserId(), user.getUserDetails())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.userDetails.firstName").value("George"))
                .andExpect(jsonPath("$.userDetails.lastName").value("Nanor"))
                .andDo(document("user"));
        Mockito.verify(userRepository, times(1))
                .updateUserDetails(any(UUID.class), any(UserDetails.class));
        verifyNoMoreInteractions(userRepository);
    }

    private User setupDetailsForUserUpdate(String firstName, String lastName) {
        UUID userId = UUID.randomUUID();
        UserDetails userDetails = UserDetails.builder().firstName(firstName).lastName(lastName).build();
        User user = User.builder().userId(userId).userDetails(userDetails).build();
        given(userRepository.updateUserDetails(any(UUID.class), any(UserDetails.class))).willReturn(user);
        given(userResourceAssembler.toResource(user)).willReturn(createUserResource(user));
        return user;
    }

    private ResultActions postContent(UUID userId, UserDetails userDetails) throws Exception {
        return this.mockMvc.perform(put("/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(userDetails)))
                .andExpect(handler().handlerType(UserManagementController.class))
                .andExpect(handler().methodName("updateRegisteredUser"));
    }


    /**
     * Update registered user with invalid details, returns a bad request
     */
    @Test
    void updateRegisteredUserWithInvalidDetailsShouldReturnBadReqeust() throws Exception {
        User user = setupDetailsForUserUpdate("@$%$@", "#@$#@$");
        postContent(user.getUserId(), user.getUserDetails())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteRegisteredUserShouldDeleteUser() throws Exception {
        UUID userId = UUID.randomUUID();
        doNothing().when(userRepository).deleteUser(userId);
        deleteContent(userId, status().isNoContent());
    }

    private void deleteContent(UUID userId, ResultMatcher resultMatcher) throws Exception {
        this.mockMvc.perform(delete("/users/{userId}", userId))
                .andDo(print())
                .andExpect(handler().handlerType(UserManagementController.class))
                .andExpect(handler().methodName("deleteRegisteredUser"))
                .andExpect(resultMatcher);
    }

    @Test
    void deleteUnRegisteredUserShouldReturnNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        doThrow(new EntityNotFoundException("User not found")).when(userRepository).deleteUser(userId);
        deleteContent(userId, status().isNotFound());
    }


}
