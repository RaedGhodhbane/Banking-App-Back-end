package com.app.accountservice.mapper;

import com.app.accountservice.dtos.AccountDTO;
import com.app.accountservice.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toDTO(Account account);
    Account toEntity(AccountDTO accountDTO);
    void updateAccountFromDTO(AccountDTO dto, @MappingTarget Account account);
}
