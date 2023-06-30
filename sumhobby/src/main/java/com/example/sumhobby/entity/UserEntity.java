package com.example.sumhobby.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
	
	@Id
	private String userTk;
	
	@Column(unique = true, nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String password, phone;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	// 일반 사용자 0, 강사 신청한 상태 = 1, 강사로 변경 후 = 2
	private Integer teacher;
	
	//사용자가 구독한 강의
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<SubscribeEntity> subscribes;
	
	@OneToMany(mappedBy = "user")
	@EqualsAndHashCode.Exclude
	private List<QuestionEntity> questions;
	
	@OneToMany(mappedBy = "user")
	@EqualsAndHashCode.Exclude
	private List<ReviewEntity> reviews;
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<InquiryEntity> inquiries;
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<CartEntity> carts;
	
	//사용자가 강사로 등록된 강의
	@OneToMany(mappedBy = "user")
	@EqualsAndHashCode.Exclude
	private List<ClassEntity> classes;

}
