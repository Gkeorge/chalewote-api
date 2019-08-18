package app.domain.user.model;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetails {

    private String firstName;

    private String lastName;

    private String homePhone;

    private String cellPhone;

    private String jobTitle;

    private String company;

    private String website;

    private String blog;

    @AttributeOverrides({
            @AttributeOverride(name = "address",
                    column = @Column(name = "home_address")),
            @AttributeOverride(name = "address2",
                    column = @Column(name = "home_address2")),
            @AttributeOverride(name = "city",
                    column = @Column(name = "home_city")),
            @AttributeOverride(name = "country",
                    column = @Column(name = "home_country")),
            @AttributeOverride(name = "postalCode",
                    column = @Column(name = "home_postal_code")),
            @AttributeOverride(name = "state",
                    column = @Column(name = "home_state"))
    })
    @Embedded
    private Address homeAddress;

    @AttributeOverrides({
            @AttributeOverride(name = "address",
                    column = @Column(name = "work_address")),
            @AttributeOverride(name = "address2",
                    column = @Column(name = "work_address2")),
            @AttributeOverride(name = "city",
                    column = @Column(name = "work_city")),
            @AttributeOverride(name = "country",
                    column = @Column(name = "work_country")),
            @AttributeOverride(name = "postalCode",
                    column = @Column(name = "work_postal_code")),
            @AttributeOverride(name = "state",
                    column = @Column(name = "work_state"))
    })
    @Embedded
    private Address workAddress;

    @AttributeOverrides({
            @AttributeOverride(name = "address",
                    column = @Column(name = "shipping_address")),
            @AttributeOverride(name = "address2",
                    column = @Column(name = "shipping_address2")),
            @AttributeOverride(name = "city",
                    column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "country",
                    column = @Column(name = "shipping_country")),
            @AttributeOverride(name = "postalCode",
                    column = @Column(name = "shipping_postal_code")),
            @AttributeOverride(name = "state",
                    column = @Column(name = "shipping_state"))
    })
    @Embedded
    private Address shippingAddress;


    @AttributeOverrides({
            @AttributeOverride(name = "address",
                    column = @Column(name = "billing_address")),
            @AttributeOverride(name = "address2",
                    column = @Column(name = "billing_address2")),
            @AttributeOverride(name = "city",
                    column = @Column(name = "billing_city")),
            @AttributeOverride(name = "country",
                    column = @Column(name = "billing_country")),
            @AttributeOverride(name = "postalCode",
                    column = @Column(name = "billing_postal_code")),
            @AttributeOverride(name = "state",
                    column = @Column(name = "billing_state"))
    })
    @Embedded
    private Address billingAdress;

}
