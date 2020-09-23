package com.biz.book.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;


/*
 * BookVO 객체들의 리스트를 담을 리스트클래스
 * 
 * RestTemplate 기능을 사용하여 API 데이터를 가져오기 위한
 * 핵심 기능을 사용하기 위한 매우 중요한 model 클래스
 * API를 통해 가져올 데이터(VO)들을 담을 List를 선언하는데
 * 
 * 이때 변수의 이름은 API에서 보내주는 데이터의 이름을 참조하여 만들어야 한다.
 * 
 */
@Alias("BookList")
@XmlRootElement(name = "channel")
public class Book_JSON_Parent {
	
	public String rss;  // 디버그를 쉽게 하고 RSS 리더기만으로 이용할 수 있게 하기 위해 만드 RSS  포맷의 컨테이너이며 그외의 특별한 의미는 없다.
	public String channel; // 검색결과를 포함하는 컨테이너이다.
	public String lastBuildDate; // datetime 검색 결과를 생성한 시간
	public String total; // integer 검색 결과 문서의 총개수를 의미 
	public String start; // integer 검색 결과 문서중, 문서 문서의 시작점을 의미
	public String display; // integer 검색 결과 검색 결과의 개수이다.
	
	
	// 가장 중요한 변수 객체
	@XmlElement(name= "channel")
	public List<BookVO> items;

}
