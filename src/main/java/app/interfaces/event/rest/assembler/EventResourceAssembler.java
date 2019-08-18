package app.interfaces.event.rest.assembler;

import app.domain.event.model.Event;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class EventResourceAssembler implements ResourceAssembler<Event, Resource<Event>> {

    @Override
    public Resource<Event> toResource(Event event) {
//        EventDTO eventDTO = EventDTO.toDTO(event);
//        return new Resource<>(eventDTO,
//                linkTo(methodOn(EventManagementController.class).getEventDetail(eventDTO.getEventId())).withSelfRel(),
//                linkTo(methodOn(EventManagementController.class).getRegisteredEvents(null, null))
//                        .withRel("events"));
        return null;
    }
}
