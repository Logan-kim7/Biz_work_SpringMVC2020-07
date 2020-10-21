package com.biz.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biz.bbs.mapper.BBsDao;
import com.biz.bbs.model.BBsVO;

@Service("bbsServiceV1")
public class BBsServiceImplV1 implements BBsService {
	
	@Autowired
	protected BBsDao bbsDao;
	
	
	@Autowired
	@Qualifier("fileServiceV4")
	protected FileService fileService;

	@Override
	public List<BBsVO> selectAll() {

		return bbsDao.selectAll();
	}

	@Override
	public void insert(BBsVO bbsVO, MultipartFile file) {

		// 파일업로드후
		String fileName = fileService.fileUp(file);
		// VO에 파일을담고
		bbsVO.setB_file(fileName);
		bbsDao.insert(bbsVO);
	}

	@Override
	public BBsVO findBySeq(long long_seq) {
		
		
		
		return bbsDao.findBySeq(long_seq);
	}

	@Override
	public int delete(long long_seq) {
		/*
		 * 첨부파일이 있는 게시판의 데이터를 삭제할때는
		 * 1. seq에 해당하는 VO를 dao에서 findBySeq() 하고
		 * 
		 * 2. 파일일음을 findDelete()에 보내서 파일을 먼저 삭제하고
		 * 3. 게시판 데이터를 삭제  
		 * 
		 * 
		 */
		BBsVO bbsVO = bbsDao.findBySeq(long_seq);
		
		String b_file = bbsVO.getB_file();
		if(b_file != null) {
			//fileService.fileDelete(bbsVO.getB_file());
			fileService.fileDelete(b_file);
		}
		return bbsDao.delete(long_seq);
	}

	@Override
	public void insert(BBsVO bbsVO) {
		// TODO Auto-generated method stub
	}

}
