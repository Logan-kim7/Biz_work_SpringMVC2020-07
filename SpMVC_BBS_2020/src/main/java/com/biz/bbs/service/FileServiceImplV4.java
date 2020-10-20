package com.biz.bbs.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
 * 파일을 upload 프로젝트에서 외부에 파일을 공개하기 위해 폴더를 개방해 두면 
 * 폴더를 외부에서 접근하여 해킹을 수행하는 경우들이 발생할수있다.
 * 특히, 프로젝트 구조를 알게되는경우 같은 이름의 파일을 엉뚱한 파일로 
 * 업로드하여 원래 저장되어 있던 파일을 변형하는 일들이 발생할수 있다.
 *  
 * 파일을 업로드 할때 원래파일 이름(originalName)을 감추고
 * 서버에서 별도의 파일이름을 생성하여 저장해 주는 것이 좋다.
 * 
 */
@Slf4j
@RequiredArgsConstructor
@Service("fileServiceV4")
public class FileServiceImplV4 extends FileServiceImplV1 {

	
	@Override
	public String fileUp(MultipartFile file) {
		
		
		// 서버가 재가동 되면 업로드 된 이미지파일이 삭제 된다
		// 서버가 재가동 되어도 이미지파일이 지워지지 않게 이미지파일이 저장될 주소값을 지정해놓고
		// 폴더가 없으면 폴더를 생성해라 라는 코드 
		String rootFolder="C:/bizwork/workspace/upload";
		File dir = new File(rootFolder); 
		if(!dir.exists()) {
			dir.mkdirs(); 
		/*
		 *  폴더주소가 통째로 없을때 mkdirs 를쓰고 ex)"C:/bizwork/workspace 이주소는 있고 upload 주소만 없으면 mkdir 만써도 생성이 가능하다
		 *  그러하기때문에 보편적으로 그냥 mkdirs를 사용한다.
		 */
		}
		//원본 파일 이름	
		String originalfileName = file.getOriginalFilename();
		
		/*
		 * 원본 파일이름을 임의의 값을 부착한 변형된 파일 이름으로 바꾸기 
		 * UUID: 범용 고유식별자로 같은 값이 생성되지 않는다 파일 원본 파일이름을 숨기기위해 사용된다.
		 * 1. UUID값을 생성하고
		 * 2. 원본 파일 이름에 UUID를 부착하기
		 * 3. UUID값이 부착된 파일이름은 서버에 업로드가 될것이고 
		 * 만약 해커가 해당파일의 이름을 알게되어 동일한 이름의 파일을 만들어서 업로드하면, 다시 새로운 UUID가 부착되어
		 * 원래저장된 파일을 훼손 시키지않고 보호할수있다.
		 */
		
		String strUUID = UUID.randomUUID().toString();
		String saveFileName = strUUID + originalfileName;
		
		// server의 저장폴더 + 파일이름을 합성하여 파일 저장 준비
		File saveFile = new File(rootFolder, saveFileName);
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return saveFileName;
	}
	
	
	
	
}
