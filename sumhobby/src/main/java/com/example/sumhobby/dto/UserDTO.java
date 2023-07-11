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
   //token 로그인할 때 생성되는 값
   private String token, userTk, userId, password, userName, phone, email, role, authProvider;
   
   public UserDTO(final UserEntity entity) {
      this.userTk = entity.getUserTk();//pk값
      this.userId = entity.getUserId();
      this.password = entity.getPassword();
      this.userName = entity.getUserName();
      this.phone = entity.getPhone();
      this.email = entity.getEmail();
      this.role = entity.getRole();
      this.authProvider = entity.getAuthProvider();
   }

}