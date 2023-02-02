package com.kodzotech.compte.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatContactDto {

    private Long totalContact;
    private Long totalMesContacts;
    private Long totalNonAssigne;
}
