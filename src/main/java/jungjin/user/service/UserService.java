package jungjin.user.service;

import jungjin.user.domain.User;
import jungjin.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public List<User> listUser(){
		return userRepository.findAll();
	}

	@Transactional
	public User saveUser(User user){
		user.setAgreeYn("Y");
		LocalDateTime date = LocalDateTime.now();
		user.setCreateDate(date);
		user = userRepository.save(user);
		return user;
	}

	public User showUser(Long num){
		return userRepository.findOne(num);
	}

	@Transactional
	public User updateUser(Long num, User updateUser){
		User user = userRepository.findOne(num);
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

	public User findById(String id){
		return userRepository.findById(id);
	}

}
