package com.ihab.e_commerce.rest.response.vendor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponse {
/* todo this class will be for public information that any user can access user or admin or even other vendor
*   but for more specific information that only admin and the vendor himself can access I will create another class*/

    private Long id;

    private String firstName;

    private String lastName;

// todo we will add rate for vendor
    // todo also the amount of profit
    // todo we could add image for vendor

}
