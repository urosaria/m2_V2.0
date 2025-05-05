package jungjin.user.service;

import java.util.Arrays;
import java.util.HashSet;

import jakarta.persistence.EntityNotFoundException;
import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.repository.RoleRepository;
import jungjin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	RoleRepository roleRepository;

	public Page<User> listUser(int page) {
		PageRequest request = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createDate"));
		return this.userRepository.findAll((Pageable)request);
	}

	public Page<User> listSearchTextUser(String searchCondition, String searchText, int page, int size) {
		PageRequest request = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createDate"));
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
		return this.userRepository.findById(num).orElse(null);
	}


	@Transactional
	public User updateUser(Long num, User updateUser) {
		updateUser.setPassword(this.bCryptPasswordEncoder.encode(updateUser.getPassword()));

		User user = this.userRepository.findById(num).orElse(null);
		if (user == null) {
			throw new EntityNotFoundException("User with id " + num + " not found.");
		}

		user.update(updateUser);
		return this.userRepository.save(user);
	}

	@Transactional
	public User deleteUser(Long num) {
		User deleteUser = this.userRepository.findById(num).orElse(null);
		if (deleteUser == null) {
			throw new EntityNotFoundException("User with id " + num + " not found.");
		}
		deleteUser.delete(deleteUser);
		return this.userRepository.save(deleteUser);
	}

	public User findById(String username) {
		return this.userRepository.findById(username);
	}
}
