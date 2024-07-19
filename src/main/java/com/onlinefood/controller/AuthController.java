package com.onlinefood.controller;

import java.util.Collection;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinefood.Service.CustomerUserDetailsService;
import com.onlinefood.config.JwtProvider;
import com.onlinefood.entity.Cart;
import com.onlinefood.entity.USER_ROLE;
import com.onlinefood.entity.User;
import com.onlinefood.repository.CartRepository;
import com.onlinefood.repository.UserRepository;
import com.onlinefood.request.LoginRequest;
import com.onlinefood.response.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired	
	private CartRepository cartRepository;
	
	@PostMapping("/signup")
	
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws Exception	
	{
		User isEmailExixt=userRepository.findByEmail(user.getEmail());
		if(isEmailExixt !=null)
		{
			throw new Exception("Email is already used with another account");
		}
		User createdUser= new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser=userRepository.save(createdUser);
		
		Cart cart=new Cart();
		cart.setCustomer(savedUser);
		cartRepository.save(cart);
		
		Authentication authentication =new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register Success");
		authResponse.setRole(savedUser.getRole());
		
		
		
		return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest request)
	{
		
		String userName=request.getEmail();
		String password=request.getPassword();
		
		Authentication authentication=authenticate(userName,password);
		Collection<? extends GrantedAuthority>authorities=authentication.getAuthorities();
		String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
		
String jwt=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Success");
	
		authResponse.setRole(USER_ROLE.valueOf(role));
		
		
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
	}

	private Authentication authenticate(String userName, String password) {
		UserDetails details=customerUserDetailsService.loadUserByUsername(userName);
		
		if(details==null)
		{
			throw new BadCredentialsException("Invalid Username...");
		}
		if(!passwordEncoder.matches(password, details.getPassword()))
		{
			throw new BadCredentialsException("Invalid Password...");
		}
		
		return new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
	}
}
