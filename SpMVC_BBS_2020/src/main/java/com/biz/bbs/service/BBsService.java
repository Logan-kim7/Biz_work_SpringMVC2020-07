package com.biz.bbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.model.BBsVO;

@Service
public interface BBsService {

	public List<BBsVO> selectAll();

	public void insert(BBsVO bbsVO);
	

	
	

}
