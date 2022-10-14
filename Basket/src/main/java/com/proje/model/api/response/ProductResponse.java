package com.proje.model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) //diger taraftan istemedigimiz json bilgisi gelirse(address gibi) önemseme.
public class ProductResponse {

    private Long id;

    private String productName;

}
