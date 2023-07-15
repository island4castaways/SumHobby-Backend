package com.example.sumhobby.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<UserEntity> selectAll() {
		return userRepository.findAll();
	}

	public UserEntity selectOne(String userTk) {
		return userRepository.findById(userTk).get();
	}

	public UserEntity selectOneByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	public UserEntity update(final UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	// 유효성 검사
	public boolean existsByPhone(String phone) {
		return userRepository.existsByPhone(phone);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

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

		// 전화번호 또는 이메일이 이미 존재하는지 확인
		final String phone = userEntity.getPhone();
		if (phone != null && userRepository.existsByPhone(phone)) {
			log.warn("이미 존재하는 전화번호 {}", phone);
			throw new RuntimeException("이미 존재하는 전화번호");
		}

		final String email = userEntity.getEmail();
		if (email != null && userRepository.existsByEmail(email)) {
			log.warn("이미 존재하는 이메일 {}", email);
			throw new RuntimeException("이미 존재하는 이메일");
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

	// 사용자 정보 가져오기
	public UserEntity getUserInfo(String userId) {
		return userRepository.findByUserId(userId);
	}

	// 사용자 정보 수정하기
	public void modifyUser(UserEntity user) {
		userRepository.save(user);
	}

	// 이메일로 사용자 찾기
	public UserEntity findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// 아이디와 이메일로 사용자 찾기
	public UserEntity findByUserIdAndEmail(String userId, String email) {
		return userRepository.findByUserIdAndEmail(userId, email);
	}

	public UserEntity retrieveUser(String userTk) {
		return userRepository.findById(userTk).get();
	}

	// 비밀번호 확인
	public Boolean checkPassword(UserEntity user, String password) {
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

}