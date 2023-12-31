package com.example.sumhobby.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "review")
public class ReviewEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer revNum;
	
	@Column(nullable = false)
	private double revRate;

	@Column(nullable = false)
	private String revContent;
	
	@CreationTimestamp
	private Timestamp revDate;
	
	@ManyToOne
	@JoinColumn(name = "userTk", referencedColumnName = "userTk")
	private UserEntity userRef;
	
	@ManyToOne
	@JoinColumn(name = "classNum", referencedColumnName = "classNum")
	private ClassEntity classRef;

}
