package com.example.sumhobby.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

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
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String userTk;
	
	@Column(unique = true, nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String password, userName, phone;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	// �Ϲ� ����� 0, ���� ��û�� ���� = 1, ����� ���� �� = 2
	@ColumnDefault("0")
	@Builder.Default
	private Integer teacher = 0;
	
	//����ڰ� ������ ����
	@OneToMany(mappedBy = "userRef", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<SubscribeEntity> subscribes;
	
	@OneToMany(mappedBy = "userRef")
	@EqualsAndHashCode.Exclude
	private List<QuestionEntity> questions;
	
	@OneToMany(mappedBy = "userRef")
	@EqualsAndHashCode.Exclude
	private List<ReviewEntity> reviews;
	
	@OneToMany(mappedBy = "userRef", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<InquiryEntity> inquiries;
	
	@OneToMany(mappedBy = "userRef", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<CartEntity> carts;
	
	//����ڰ� ����� ��ϵ� ����
	@OneToMany(mappedBy = "userRef")
	@EqualsAndHashCode.Exclude
	private List<ClassEntity> classes;

}