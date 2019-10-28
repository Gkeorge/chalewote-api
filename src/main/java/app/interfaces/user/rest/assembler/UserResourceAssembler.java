package app.interfaces.user.rest.assembler;

import app.domain.user.model.User;
import app.interfaces.user.rest.UserManagementController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {

    @Override
    public Resource<User> toResource(User user) {
        return new Resource<>(user,
                linkTo(methodOn(UserManagementController.class).getRegisteredUser(user.getUserId())).withSelfRel(),
                linkTo(methodOn(UserManagementController.class).getAllRegisteredUsers(null, null))
                        .withRel("users"));
    }

}
