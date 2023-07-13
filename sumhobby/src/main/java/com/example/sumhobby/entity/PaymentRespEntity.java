package com.example.sumhobby.entity;

import java.util.List;

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
@Table(name = "paymentResp")
public class PaymentRespEntity {
	
	@Id
	private String orderId;
	
	private Integer amount;
	
	private String paymentKey;
	
	private String orderName;
	
	private String requestedAt;
	//시간 - Timestamp로 할 수 있을지 모르겠음 출력값: 2023-07-13T21:16:56+09:00
	
	private Boolean isSuccessful;
	
	@OneToMany(mappedBy = "paymentRespRef")
	@EqualsAndHashCode.Exclude
	private List<PaymentEntity> payments;
	
	
}
