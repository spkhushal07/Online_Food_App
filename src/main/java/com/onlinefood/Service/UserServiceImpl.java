package com.onlinefood.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinefood.config.JwtProvider;
import com.onlinefood.entity.User;
import com.onlinefood.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		User user=repository.findByEmail(email);
	return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = repository.findByEmail(email);
		if (user == null) {
			throw new Exception("User not Found");
		}
		return user;
	}

}
