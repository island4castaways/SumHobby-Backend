package com.example.sumhobby.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cart")
public class CartEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartNum;
	
//	private Boolean checkout;
	
	@ManyToOne
	@JoinColumn(name = "userTk", referencedColumnName = "userTk")
	private UserEntity userRef;
	
	@ManyToOne
	@JoinColumn(name = "classNum", referencedColumnName = "classNum")
	private ClassEntity classRef;

}
