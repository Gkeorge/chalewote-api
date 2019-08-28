package app.interfaces.event.rest.assembler;

import app.domain.event.model.Event;
import app.interfaces.event.rest.EventManagementController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class EventResourceAssembler implements ResourceAssembler<Event, Resource<Event>> {

    @Override
    public Resource<Event> toResource(Event event) {
        return new Resource<>(event,
                linkTo(methodOn(EventManagementController.class).getEventDetail(event.getEventId())).withSelfRel(),
                linkTo(methodOn(EventManagementController.class).getRegisteredEvents(null, null))
                        .withRel("events"));
    }
}
