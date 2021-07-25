package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.dto.user.UserPostDto;
import com.luv2code.travelchecker.dto.user.UserPutDto;

public interface UserMapper extends Create<User, UserPostDto>, Update<User, UserPutDto>, View<User, UserGetDto> {

}
