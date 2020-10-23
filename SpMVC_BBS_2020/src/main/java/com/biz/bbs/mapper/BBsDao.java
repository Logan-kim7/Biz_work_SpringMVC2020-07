package com.biz.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.bbs.model.BBsVO;
import com.biz.bbs.sql.BBsSQL;

public interface BBsDao {
	@Select("SELECT * FROM tbl_bbs ORDER BY b_seq DESC")
	public List<BBsVO> selectAll();
	
	@Select("SELECT * FROM tbl_bbs WHERE b_seq = #{seq}")
	public BBsVO findBySeq(long seq);
	/*
	 *  BBsSQL 클래스에 정의된 bbs_insert method를 호출하여
	 *  SQL문을 생성하고, 여기에 코드를 추가하라
	 */
	
	
	// before = true : insert 문을시작하기전에
	// before = false : insert문을 시작한후
	//  statement = "SELECT SEQ_BBS.NEXTVAL FROM DUAL", resultType = Long.class : seq를 생성을해서 타입을 long 형식으로 만들어라
	//  public int insert(BBsVO bbsVO); : BBsVO에 bbsVO 변수명으로 담아라 
	@InsertProvider(type= BBsSQL.class, method="bbs_insert")
	@SelectKey(keyProperty="b_seq", statement = "SELECT SEQ_BBS.NEXTVAL FROM DUAL", resultType = Long.class, before = true)
	public int insert(BBsVO bbsVO);
	
	@UpdateProvider(type= BBsSQL.class, method="bbs_update")
	public int update(BBsVO bbsVO);
	
	@Select("DELETE FROM tbl_bbs WHERE b_seq = #{seq}")
	public int delete(long seq);
	
		
	
	

}
