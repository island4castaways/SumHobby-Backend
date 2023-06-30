package com.example.sumhobby.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin")
public class AdminEntity {
	
	@Id
	private String adminTk;
	
	@Column(unique = true, nullable = false)
	private String adminId;
	
	@Column(nullable = false)
	private String adminPw;

}
