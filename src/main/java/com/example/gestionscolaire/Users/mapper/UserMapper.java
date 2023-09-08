package com.example.gestionscolaire.Users.mapper;


import com.example.gestionscolaire.Users.dto.UserResDto;
import com.example.gestionscolaire.Users.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    UserResDto userToUserResDto(Users users);
//    @Mapping(source = "typeAccount", target = )
//    Users userReqDtoToUsers(UserReqDto userReqDto);
}
