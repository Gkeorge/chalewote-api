package app.domain.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@AllArgsConstructor
@Getter
@Embeddable
public class Location {

    private LocationType type;

    @Embedded
    private Address address;

    Location createOnlineLocation() {
        this.type = LocationType.ONLINE;
        return new Location(type, new Address.AddressBuilder().build());
    }

    Location createAddressLocation(Address address) {
        this.type = LocationType.PLACE;
        this.address = address;
        return new Location(type, address);
    }

    public static enum LocationType {
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


