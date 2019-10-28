package app.interfaces.event.rest;

import app.application.EventService;
import app.domain.event.model.Event;
import app.domain.event.model.EventDetails;
import app.interfaces.event.rest.assembler.EventResourceAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EventManagementController {

    private final EventResourceAssembler eventResourceAssembler;
    private final EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<PagedResources<EventDetails>> getRegisteredEvents(Pageable pageable,
                                                                            PagedResourcesAssembler pagedResourcesAssembler) {
        Page<Event> events = eventService.getRegisteredEvents(pageable);
        return new ResponseEntity<>(pagedResourcesAssembler.toResource(events, eventResourceAssembler),
                HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}")
    public Resource<Event> getEventDetail(@PathVariable UUID eventId) {
        return eventResourceAssembler.toResource(eventService.getRegisteredEvent(eventId));
    }

    @GetMapping("/{userId}/events")
    public ResponseEntity<PagedResources<Event>> getRegisteredEventsForLoginUser(@PathVariable UUID userId,
                                                                                 Pageable pageable,
                                                                                 PagedResourcesAssembler pagedResourcesAssembler) {
        Page<Event> eventsForUser = eventService.getRegisteredEventsForUser(userId, pageable);
        return new ResponseEntity<>(pagedResourcesAssembler.toResource(eventsForUser, eventResourceAssembler),
                HttpStatus.OK);
    }

    @PostMapping("/{userId}/events")
    ResponseEntity<Resource<Event>> registerEvent(@PathVariable UUID userId,
                                                  @RequestBody @Valid EventDetails eventDetails) {
        Event registeredEvent = eventService.registerEvent(userId, eventDetails);
        return ResponseEntity
                .created(linkTo(methodOn(EventManagementController.class).getEventDetail(registeredEvent.getEventId())).toUri())
                .body(eventResourceAssembler.toResource(registeredEvent));
    }

    @PutMapping("/{userId}/events/{eventId}")
    ResponseEntity<ResourceSupport> updateRegisteredUser(@PathVariable UUID userId,
                                                         @PathVariable UUID eventId,
                                                         @RequestBody @Valid EventDetails eventDetails) {
        Event event = eventService.updateRegisteredEvent(userId, eventId, eventDetails);
        return ResponseEntity.ok(eventResourceAssembler.toResource(event));
    }

    @DeleteMapping("/{userId}/events/{eventId}")
    ResponseEntity<?> deleteRegisteredUser(@PathVariable UUID eventId) {
        eventService.deleteRegisteredEvent(eventId);
        return ResponseEntity.noContent().build();
    }

}
