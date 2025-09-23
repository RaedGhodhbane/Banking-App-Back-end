package com.app.accountservice.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationDTO {
    private Long accountId;
    private double amount;
}
