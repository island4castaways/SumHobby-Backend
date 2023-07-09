package com.example.sumhobby.dto;

import com.example.sumhobby.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private String userTk, userId, password, userName, phone, email;
	private int teacher;
	
	public UserDTO(final UserEntity entity) {
		this.userTk = entity.getUserTk();
		this.userId = entity.getUserId();
		this.password = entity.getPassword();
		this.userName = entity.getUserName();
		this.phone = entity.getPhone();
		this.email = entity.getEmail();
		this.teacher = Integer.parseInt(entity.getTeacher().toString());
	}

}