package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.SubscribeEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.SubscribeRepository;

@Service
public class SubscribeService {
	
	@Autowired
	private SubscribeRepository subsRepository;

	//id�� ������ ���� ��� ���ϱ�
//	public List<SubscribeEntity> getFindByUserId(){
//		return subsRepository.findByUserRef();
//	}
}
