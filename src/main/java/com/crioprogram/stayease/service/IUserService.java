package com.crioprogram.stayease.service;

import com.crioprogram.stayease.dto.ResponseDTO;
import com.crioprogram.stayease.dto.UserDTO;

public interface IUserService {

    ResponseDTO userRegistration(UserDTO userDTO);
    ResponseDTO userLogin(UserDTO userDTO);


}
