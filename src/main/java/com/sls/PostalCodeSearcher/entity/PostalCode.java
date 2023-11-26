package com.sls.PostalCodeSearcher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostalCode {
    @Id
    private Long id;
    private String postalCode;
    private String country;
    private String state;
    private String city;
    private String area;


}
