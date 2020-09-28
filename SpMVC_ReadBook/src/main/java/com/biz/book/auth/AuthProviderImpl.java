package com.biz.book.auth;

import java.util.ArrayList;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
/*
 * 
 * member table에서 user 정보를 select하여 권한을 관리하는 클래스
 * 
 * Spring security에서 사용자 인증, 권한부여 절차
 * 사용자가 login form에 username, password를 입력한 후 로그인을 시도하면
 * 1. AuthenticationProvider 인터페이스를 구현한 AuthProviderImpl 객체를 찾는다
 * 2. authenticate() method를 호출한다.
 * 		이때 spring security는 authentication 객체에 로그인 한 username, password를 실어서 전달한다.
 * 
 *  3. authentication 객체에서 principal(username), credential(password) 값을 getter 하여
 *  	username과 password를 추출한다.
 *  
 *  4. UserDetailService 인터페이스를 구현한 UserDetailServiceImpl 객체를 호출하여
 *  	UserDetails 정보를 요청한다..
 *  
 *  
 *  5.	UserDetailsServiceImpl은 username에 해당하는 사용자 정보를
 *  	user(member) table에서 select하여 return 한다.
 *  	UsernameNotFoundException을 발생시켜 spring security에게 사용자 정보가 없음을 통보한다
 *  
 *  6.  UserDetailsServiceImpl로 부터 return받은 UserDetails에 담겨있는
 *  	password와 사용자가 login 한 password를 비교한다.
 *  	만약 password가 다르면
 *  	BadCredentialsException 을 강제로 발생시켜 spring scurity에게 통보한다
 *  
 *  7. username과 password가 확인되었다 이제 사용자 정보에 enable 값을 검사하여
 *  	사용가능한 사용자 정보인지를 알아본다.
 * 
 */
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biz.book.model.AuthorityVO;
import com.biz.book.model.UserDetailsVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AuthProviderImpl implements AuthenticationProvider {
	
	@Autowired
	@Qualifier("userDetailServiceV1")
	
	private UserDetailsService userService;
	
		@Autowired
		private PasswordEncoder passwordEncoder;
	/*
	 * 사용자가 로그인을 수행했을때 username과, password를 주입받아서
	 * 정상적인 사용자 인가를 검사하는 method
	 * 
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// 로그인한 사용자의 username과 password를 authentication 객체로부터 getter
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		// 로그인 인증하는 부분...
		// 1. UserDetailService에 username을 전달하고 DB부터 사용자정보를
		// select한 결과를 userVO에 받는다.
		UserDetailsVO userVO = (UserDetailsVO) userService.loadUserByUsername(username);
		
		
		// 2. 비밀번호 검사
//		if(!password.equals(userVO.getPassword())) {
//			// 비번이 일치 하지 않으면
//			throw new BadCredentialsException("비밀번호 오류");
//			
//		}
		
		// PasswordEncoder로 암호화된 비번비교
		/*
		 * 사용자가 입력한 password 평문 문자열을 내부에서
		 * 암호화하여 DB에 저장되어 있는 암호화된 비번(userVO.getPassword())을 
		 * 비교하여 일치하는지 검사한다
		 */
		if(!passwordEncoder.matches(password, userVO.getPassword())) {
			throw new BadCredentialsException("비밀번호 오류");
						
		}
		
		// 3. 유효한 사용자 정보인가
		if(!userVO.isEnabled()) {
			throw new BadCredentialsException(username + "사용자 정보 사용불가!!");
		}
		
		
		//4. 사용자의 권한 리스트 생성 (임시)
		
		/*
		 * 가. 임시로 사용자의 권한 리스트를 생성하기 위하여
		 * AuthorityVO를 담은 List를 생성하고
		 * 권한(ROLE) 값을 지정하여 add()
		 */
		List<AuthorityVO> authList = new ArrayList<AuthorityVO>();
		authList.add(AuthorityVO.builder().m_role("ROLE_ADMIN").build());
		authList.add(AuthorityVO.builder().m_role("ROLE_USER").build());
		
		// 나. spring security의 hasRole() method에서 사용할 자료형으로 변환 
		List< GrantedAuthority> rollList = new ArrayList<>();
		
		for(AuthorityVO auth: authList) {
			rollList.add(new SimpleGrantedAuthority(auth.getM_role()));
		}
		
		
		// 로그인한 사용자에게 인증 token을 발행
		// 사용자의 detail 정보와 roll정보를 token에 같이 심어 놓는다.
		return new UsernamePasswordAuthenticationToken(userVO, null,rollList);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		//supports() 의 return 값이 false이면 ...Token을 사용하지 않곘다
		// 반드시 여기는 true로 해야된다.
		return true;
	}

}
