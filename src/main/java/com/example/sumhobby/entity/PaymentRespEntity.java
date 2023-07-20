package com.example.sumhobby.entity;

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
@Table(name = "paymentResp")
public class PaymentRespEntity {
	
	@Id
	private String orderId;
	
	private Integer amount;
	
	private String paymentKey;
	
	private String orderName;
	
	private String requestedAt;
	
	private Boolean isSuccessful;

}
