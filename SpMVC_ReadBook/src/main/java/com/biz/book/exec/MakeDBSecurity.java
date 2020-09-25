package com.biz.book.exec;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import javax.print.PrintServiceLookup;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/*
 * jsyspt 패키지의 StandardPBEStringEncryptor 클래스를 사용하여
 * DB 접속용 username password를 암호화 하여
 * 문자열을 추출 
 * 
 */
public class MakeDBSecurity {
	
	public static void main(String[] args) {
		
		String propsFile = "./src/main/webapp/WEB-INF/spring/db.connect.properties";
		
		StandardPBEStringEncryptor pbe = new StandardPBEStringEncryptor();
		Scanner scan = new Scanner(System.in);
		
		Map<String,String> envList = System.getenv();
		
		String saltPassoword = envList.get("BIZ.NET");
		
		if(saltPassoword == null) {
			System.out.println("BIZ.NET 환경변수 설정값이 없습니다. 확인하세요");
			return;
		}
		System.out.printf("BIZ.NET 환경변수 ===> %s\n",saltPassoword);
		
		System.out.print("DB UserName ==>  ");
		String userName = scan.nextLine();
		
		
		System.out.print("DB PassWord ==>  ");
		String password  = scan.nextLine();
		
		// 암호화를 위해서 PBE.. 객체 값 세팅
		pbe.setAlgorithm("PBEWithMD5AndDES");

		// salt : 암호화를 할때 사용할 key 문자열
		/*
		 * salt 값을 일반 문자열로 사용하면
		 * 소스코드에 문자열이 노출되어 각종 값들을 암호화 하는
		 * 용도로 사용하는데 문제가 발생할 수 있다.
		 * 
		 *  문자열을 바로 사용하지 않고 시스템(컴퓨터운영체제)에 환경변수를 저장해 두고
		 *  환경변수를 가져다 salt 비밀번호로 사용을 한다.
		 *  
		 *  BIZ.CON 이라는 문자열이 노출되어도
		 *  네트워크를 통해서 salt암호를 추출하기가 매우 어려워진다.
		 *  따라서 암호화 하는 효과를 좀더 배가 시키리수 있다.
		 *  
		 *	pbe.setUserName(envList.get("BIZ.COM"));
		 *	pbe.setPassword(envList.get("BIZ.NET"));
		 */
		
		pbe.setPassword(saltPassoword);
		
		String encUserName = pbe.encrypt(userName);
		String encPassword = pbe.encrypt(password);
		
		String saveUserName = String.format("oracle.username=ENC(%s)",encUserName);
		String savePassword = String.format("oracle.userpassword=ENC(%s)",encPassword);
		
		try {
			PrintWriter out = new PrintWriter(propsFile);
			out.println(saveUserName);
			out.println(savePassword);
			out.flush();			
			out.close();			
		} catch (FileNotFoundException e) {
			
			// TODO: handle exception
		}
		scan.close();
		System.out.println("생성완료 ====>");
				
				
//		for(int i =0; i<10 ; i++) {
//			//평문을 암호화하여  암호문 추출 : encryption
//			String encText = pbe.encrypt(planText);
//			System.out.printf("P :%s, E :%s\n",planText,encText);
//			
//			//암호문을 복호화하여 평문 추출 : Decsryption
//			String dencText = pbe.decrypt(encText);
//			System.out.printf("E :%s, P :%s\n",dencText,planText);
//			
//		}
		
		
		
		
		
	}

}
