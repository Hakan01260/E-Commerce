package com.proje.mapper;

import com.proje.model.api.response.AddressResponse;
import com.proje.model.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressResponseMapper {

    public AddressResponse map(Address address){
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getCity());
    }

}
