package app.domain.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@AllArgsConstructor
@Getter
@Embeddable
@NoArgsConstructor
public class Location {

    private LocationType type;

    @Embedded
    private Address address;

    public enum LocationType {
        ONLINE,
        PLACE
    }

    @Builder
    @Embeddable
    static class Address {

        private String venue;

        private String address;

        private String address2;

        private String city;

        private String zip;

        private String country;
    }
}


