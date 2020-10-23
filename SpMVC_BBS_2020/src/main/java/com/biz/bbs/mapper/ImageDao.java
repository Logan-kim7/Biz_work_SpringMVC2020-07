package com.biz.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.bbs.model.ImageVO;
import com.biz.bbs.sql.ImageSQL;

public interface ImageDao {
	
	@Select("SELECT * FROM tbl_images WHERE i_b_seq = #{i_b_seq}")
	public List<ImageVO> findBySeq(long i_b_seq);
	
	// 지금까지 배우기로는 변수명을 1개만 보냈었다 그런데 변수가 2개이상을 보낼때는 앞에
	// @Param 을붙이고 변수명을 각각 써줘야한다.
	@InsertProvider(type= ImageSQL.class, method="insert")
	public int insert(@Param("vo") ImageVO imageVO, @Param("b_seq") long b_seq);
	
	public int insert_list(List<ImageVO>images, long b_seq);
	
	

}
