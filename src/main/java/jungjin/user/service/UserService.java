package jungjin.user.service;

import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.repository.RoleRepository;
import jungjin.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	RoleRepository roleRepository;

	public List<User> listUser(){
		return userRepository.findAll();
	}

	public void saveUser(User user,String[] roles) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> rolesSet = new HashSet<Role>();
		for(String role:roles){
			rolesSet.add(new Role(role));
		}
		user.setRoles(rolesSet);
		user.insert(user);
		userRepository.save(user);
	}

	public User showUser(Long num){
		return userRepository.findOne(num);
	}

	@Transactional
	public User updateUser(Long num, User updateUser){
		User user = userRepository.findOne(num);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.update(updateUser);
		user = userRepository.save(user);
		return user;
	}

	@Transactional
	public User deleteUser(Long num){
		User deleteUser = userRepository.findOne(num);
		deleteUser.delete(deleteUser);
		deleteUser = userRepository.save(deleteUser);
		return deleteUser;
	}

	public User findById(String username) {
		return userRepository.findById(username);
	}

}
