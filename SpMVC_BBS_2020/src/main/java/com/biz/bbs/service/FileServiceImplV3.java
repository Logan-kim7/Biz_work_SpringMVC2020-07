package com.biz.bbs.service;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service("fileServiceV3")
public class FileServiceImplV3 extends FileServiceImplV1 {

	
	@Override
	public String fileUp(MultipartFile file) {
		
		
		// 서버가 재가동 되면 업로드 된 이미지파일이 삭제 된다
		// 서버가 재가동 되어도 이미지파일이 지워지지 않게 이미지파일이 저장될 주소값을 지정해놓고
		// 폴더가 없으면 폴더를 생성해라 라는 코드 
		String rootFolder="~/upload";
		File dir = new File(rootFolder); 
		if(!dir.exists()) {
			dir.mkdirs(); 
		/*
		 *  폴더주소가 통째로 없을때 mkdirs 를쓰고 ex)"C:/bizwork/workspace 이주소는 있고 upload 주소만 없으면 mkdir 만써도 생성이 가능하다
		 *  그러하기때문에 보편적으로 그냥 mkdirs를 사용한다.
		 */
			
			
		String fileName = file.getOriginalFilename();
		// server의 저장폴더 + 파일이름을 합성하여 파일 저장 준비
		
		File saveFile = new File(rootFolder, fileName);
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return null;
	}
	
	
	
	
}
