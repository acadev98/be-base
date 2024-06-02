package com.acadev.baseprojectname.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acadev.baseprojectname.database.entity.Users;
import com.acadev.baseprojectname.database.repository.UsersRepository;
import com.acadev.baseprojectname.handler.exception.ApiException;
import com.acadev.baseprojectname.model.request.SignInRequest;
import com.acadev.baseprojectname.model.request.SignUpRequest;
import com.acadev.baseprojectname.model.response.SignInResponse;
import com.acadev.baseprojectname.model.response.UserInfoResponse;
import com.acadev.baseprojectname.service.UserService;
import com.acadev.baseprojectname.utils.JwtHelper;
import com.acadev.baseprojectname.utils.enums.ApiMessage;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public String echo() {
		return "user echo message service";
	}
	
	@Transactional
	public void signup(SignUpRequest request) {
		Optional<Users> existingUser = repository.findByMail(request.getMail());
		if (existingUser.isPresent()) {
			throw new ApiException(ApiMessage.EMAIL_USER_ALREADY_EXISTS);
		}
		
		String hashedPassword = passwordEncoder.encode(request.getPassword());
		
		Users user = Users.builder()
				.name(request.getName())
				.lastname(request.getLastname())
				.username(request.getUsername())
				.mail(request.getMail())
				.password(hashedPassword)
				.build();
		
		repository.save(user);
	}
	
	public SignInResponse signin(SignInRequest request) {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword()));
	    } catch (Exception e) {
			throw new ApiException(ApiMessage.CREDENTIALS_INCORRECT);
	    }
		
		String token = JwtHelper.generateToken(request.getMail());
		return SignInResponse.builder().email(request.getMail()).token(token).build();
	}

	public UserInfoResponse userinfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Optional<Users> existingUser = repository.findByMail(userDetails.getUsername());
		
		if (existingUser.isEmpty()) {
			throw new ApiException(ApiMessage.CONTENT_NOT_FOUND);
		} else {
			Users user = existingUser.get();
			
			UserInfoResponse response = UserInfoResponse.builder()
				.name(user.getName())
				.lastname(user.getLastname())
				.username(user.getUsername())
				.email(user.getMail())
				.build();

	        return response;
		}
        
	}
	
}
