package app.interfaces.user.rest;

import app.domain.user.model.User;
import app.domain.user.model.UserDetails;
import app.domain.user.model.UserRepository;
import app.interfaces.user.dto.SignUpUser;
import app.interfaces.user.rest.assembler.UserResourceAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserManagementController {


    private final UserRepository userRepository;
    private final UserResourceAssembler userResourceAssembler;

    @GetMapping
    public ResponseEntity<PagedResources<UserDetails>> getAllRegisteredUsers(Pageable pageable,
                                                                             PagedResourcesAssembler pagedResourcesAssembler) {
        Page<User> users = userRepository.findAll(pageable);
        return new ResponseEntity<>(pagedResourcesAssembler.toResource(users, userResourceAssembler),
                HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public Resource<User> getRegisteredUser(@PathVariable UUID userId) {
        User user = userRepository.loadUserByUserId(userId);
        return this.userResourceAssembler.toResource(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    HttpHeaders registerUser(@RequestBody @Valid SignUpUser user) {
        UUID userId = userRepository.signUpUser(user.getEmailAddress(), user.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(UserManagementController.class).slash(userId).toUri());
        return headers;
    }

    @PutMapping("/{userId}")
    ResponseEntity<ResourceSupport> updateRegisteredUser(@PathVariable UUID userId,
                                                         @RequestBody @Valid UserDetails userDetails) {
        User user = userRepository.updateUserDetails(userId, userDetails);
        return ResponseEntity.ok(userResourceAssembler.toResource(user));
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<?> deleteRegisteredUser(@PathVariable UUID userId) {
        userRepository.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    ResponseEntity<Boolean> checkIfEmailExists(@PathVariable String email) {
        boolean result = userRepository.checkIfEmailExsits(email);
        if (result) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }


}
