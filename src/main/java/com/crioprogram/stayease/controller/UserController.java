package com.crioprogram.stayease.controller;

import com.crioprogram.stayease.dto.ResponseDTO;
import com.crioprogram.stayease.dto.UserDTO;
import com.crioprogram.stayease.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<ResponseDTO> userRegistration(@RequestBody @Valid UserDTO userDTO) {
        ResponseDTO responseDTO = userService.userRegistration(userDTO);
        System.out.println(responseDTO.getData());
        return new ResponseEntity<>(responseDTO, responseDTO.getCode());
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody @Valid UserDTO userDTO) {
        ResponseDTO responseDTO = userService.userLogin(userDTO);
        System.out.println(responseDTO.getData());
        return new ResponseEntity<>(responseDTO, responseDTO.getCode());
    }

}
