package com.ihab.e_commerce.rest.response.vendor;

import java.util.List;

public record VendorsResponse(List<VendorResponse> vendorResponses, Integer totalNumberOfVendors) {
}
