package com.example.sumhobby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.OffsetMapping.Target.ForArray.ReadOnly;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// 사용자 생성
	public UserEntity create(final UserEntity userEntity) {
		// 유효성 체크
		if (userEntity == null || userEntity.getUserId() == null) {
			throw new RuntimeException("Invalid Arguments");
		}
		final String userId = userEntity.getUserId();
		if (userRepository.existsByUserId(userId)) {
			log.warn("UserId already exits {}", userId);
			throw new RuntimeException("UserId already exits");
		}

		return userRepository.save(userEntity); // 사용자 저장 및 결과 반환...
	}

	// password 암호화 // 사용자 인증(존재여부.로그인)
	public UserEntity getByCredentials(final String userId, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByUserId(userId);

		// matches 메서드를 이용해 패스워드가 같은지 확인
		if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}

	// 이메일로 사용자 찾기
	public UserEntity findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// 아이디와 이메일로 사용자 찾기
	public UserEntity findByUserIdAndEmail(String userId, String email) {
		return userRepository.findByUserIdAndEmail(userId, email);
	}

	// 비밀번호 확인
	public boolean checkPassword(UserEntity user, String password) {
		// 비밀번호 확인 로직 구현
		// 예시로 BCryptPasswordEncoder를 사용한 비밀번호 확인
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(password, user.getPassword());
	}

	// Password 수정
	public Boolean pwmodify(final String userId, final String oldpassword, final String newpassword) {
		UserEntity user = userRepository.findByUserId(userId);
		if (user != null && user.getPassword().equals(oldpassword)) {
			user.setPassword(newpassword);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	// 사용자 정보 수정
	public UserEntity modify(UserEntity user, UserDTO userDTO) {
		// id로 엔티티를 찾고 그 entity의 userId가 session의 userId와 같으면 수정한 부분 저장 후 성공
		if (user != null) {
			user.setUserId(userDTO.getUserId());
			user.setUserName(userDTO.getUserName());
			user.setPhone(userDTO.getPhone());
			user.setEmail(userDTO.getEmail());
			userRepository.save(user);
			return user;
		}
		return user;
	}

	// 사용자 정보 삭제
	public Boolean deleteUser(final String userId, final String password) {
		UserEntity user = userRepository.findByUserId(userId);
		if (user != null && user.getPassword().equals(password)) {
			userRepository.delete(user);
			return true;
		}
		return false;
	}
	
	public UserEntity retrieveUser(String userTk) {
		return userRepository.findById(userTk).get();
	}
	

}