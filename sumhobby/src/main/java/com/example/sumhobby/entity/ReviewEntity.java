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
	
	private Timestamp revDate;
	
	@ManyToOne
	@JoinColumn(name = "user_tk", referencedColumnName = "user_tk")
	private UserEntity userRef;
	
	@ManyToOne
	@JoinColumn(name = "class_num", referencedColumnName = "class_num")
	private ClassEntity classRef;

}
