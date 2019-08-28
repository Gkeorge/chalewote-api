package app.interfaces.event.rest;

import app.application.EventService;
import app.domain.event.model.Event;
import app.domain.event.model.EventDetails;
import app.domain.event.model.Location;
import app.domain.user.model.User;
import app.interfaces.event.rest.assembler.EventResourceAssembler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventManagementController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
@DisplayName("Event Management Test 😱")
class EventManagementControllerTest {

    @MockBean
    private EventService eventService;

    @MockBean
    private EventResourceAssembler eventResourceAssembler;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRegisteredEvents() {
    }

    @Test
    void getEventDetail() throws Exception {
        UUID eventId = UUID.randomUUID();
        User user = User.builder().emailAddress("gorkofi@gmail.com").password("1234345").build();
        Event event = Event.createEvent(user,
                new EventDetails.EventDetailBuilder("CIAS", new Location(Location.LocationType.ONLINE, null),
                        null, "Test").build());

        given(this.eventService.getRegisteredEvent(eventId)).willReturn(event);
        given(this.eventResourceAssembler.toResource(event)).willReturn(createUserResource(event));

        getRegisteredEventWithValidUserId(eventId);
    }

    private Resource<Event> createUserResource(Event event) {
        return new Resource<>(event,
                linkTo(methodOn(EventManagementController.class).getEventDetail(event.getEventId())).withSelfRel(),
                linkTo(methodOn(EventManagementController.class).getRegisteredEvents(null,
                        null))
                        .withRel("events"));
    }

    private void getRegisteredEventWithValidUserId(UUID eventId) throws Exception {
        getContent(eventId, status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.eventDetails.title").value("CIAS"))
                .andExpect(jsonPath("$.links").hasJsonPath())
                .andDo(document("event"));
    }

    private ResultActions getContent(UUID eventId, ResultMatcher resultMatcher) throws Exception {
        return this.mockMvc.perform(get("/users/events/{eventId}", eventId)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(handler().handlerType(EventManagementController.class))
                .andExpect(handler().methodName("getEventDetail"))
                .andExpect(resultMatcher);
    }


    @Test
    void getRegisteredEventsForLoginUser() {
    }

    @Test
    void getRegisteredEventDetailForLoginUser() {
    }

    @Test
    void registerEvent() {
    }

    @Test
    void updateRegisteredEvent() {
    }
}