package com.example.sumhobby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.service.UserService;

@RestController
@RequestMapping("/search")
public class UserSearchController {
	@Autowired
    private UserService userService;

    @GetMapping("/id")
    public ResponseEntity<?> findUserIdByEmail(@RequestParam("email") String email) {
        UserEntity user = userService.findByEmail(email);

        if (user != null) {
            return ResponseEntity.ok().body(user.getUserId());
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }
    @PostMapping("/password")
    public ResponseEntity<?> checkPassword(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.findByUserIdAndEmail(userDTO.getUserId(), userDTO.getEmail());

        if (user != null) {
            if (userService.checkPassword(user, userDTO.getPassword())) {
                return ResponseEntity.ok().body("Password is correct");
            } else {
                return ResponseEntity.badRequest().body("Password is incorrect");
            }
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
}
    }
