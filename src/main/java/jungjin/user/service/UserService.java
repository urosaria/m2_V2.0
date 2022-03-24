package jungjin.user.service;

import java.util.Arrays;
import java.util.HashSet;
import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.repository.RoleRepository;
import jungjin.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	RoleRepository roleRepository;

	public Page<User> listUser(int page) {
		PageRequest request = new PageRequest(page - 1, 10, Sort.Direction.DESC, new String[] { "createDate" });
		return this.userRepository.findAll((Pageable)request);
	}

	public Page<User> listSearchTextUser(String searchCondition, String searchText, int page, int size) {
		PageRequest request = new PageRequest(page - 1, 10, Sort.Direction.DESC, new String[] { "createDate" });
		Page<User> userList = null;
		if (searchCondition.equals("name")) {
			userList = this.userRepository.findByNameContaining(searchText, (Pageable)request);
		} else if (searchCondition.equals("id")) {
			userList = this.userRepository.findByIdContaining(searchText, (Pageable)request);
		} else if (searchCondition.equals("email")) {
			userList = this.userRepository.findByEmailContaining(searchText, (Pageable)request);
		} else if (searchCondition.equals("phone")) {
			userList = this.userRepository.findByPhoneContaining(searchText, (Pageable)request);
		}
		return userList;
	}

	public User saveUser(User user, Role roles) {
		User userCheck = this.userRepository.findById(user.getId());
		if (userCheck == null) {
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			user.setRoles(new HashSet(Arrays.asList((Object[])new Role[] { roles })));
			user.insert(user);
			user = (User)this.userRepository.save(user);
		} else {
			user = userCheck;
		}
		return user;
	}

	public User showUser(Long num) {
		return (User)this.userRepository.findOne(num);
	}

	@Transactional
	public User updateUser(Long num, User updateUser) {
		updateUser.setPassword(this.bCryptPasswordEncoder.encode(updateUser.getPassword()));
		User user = (User)this.userRepository.findOne(num);
		user.update(updateUser);
		user = (User)this.userRepository.save(user);
		return user;
	}

	@Transactional
	public User deleteUser(Long num) {
		User deleteUser = (User)this.userRepository.findOne(num);
		deleteUser.delete(deleteUser);
		deleteUser = (User)this.userRepository.save(deleteUser);
		return deleteUser;
	}

	public User findById(String username) {
		return this.userRepository.findById(username);
	}
}
