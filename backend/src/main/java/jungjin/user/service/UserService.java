package jungjin.user.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

//	private final UserRepository userRepository;
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
//	private final RoleRepository roleRepository;
//
//	public Page<User> listUser(int page) {
//		PageRequest request = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createDate"));
//		return userRepository.findAll((Pageable)request);
//	}
//
//	public Page<User> listSearchTextUser(String searchCondition, String searchText, int page, int size) {
//		PageRequest request = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createDate"));
//		Page<User> userList = null;
//		if (searchCondition.equals("name")) {
//			userList = userRepository.findByNameContaining(searchText, (Pageable)request);
//		} else if (searchCondition.equals("id")) {
//			userList = userRepository.findByIdContaining(searchText, (Pageable)request);
//		} else if (searchCondition.equals("email")) {
//			userList = userRepository.findByEmailContaining(searchText, (Pageable)request);
//		} else if (searchCondition.equals("phone")) {
//			userList = userRepository.findByPhoneContaining(searchText, (Pageable)request);
//		}
//		return userList;
//	}
//
//	public User saveUser(User user, Role roles) {
//		User userCheck = userRepository.findById(user.getId());
//		if (userCheck == null) {
//			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//			user.setRoles(new HashSet(Arrays.asList((Object[])new Role[] { roles })));
//			user.insert(user);
//			user = (User)userRepository.save(user);
//		} else {
//			user = userCheck;
//		}
//		return user;
//	}
//
//	public User showUser(Long num) {
//		return userRepository.findById(num).orElse(null);
//	}
//
//
//	@Transactional
//	public User updateUser(Long num, User updateUser) {
//		updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
//
//		User user = userRepository.findById(num).orElse(null);
//		if (user == null) {
//			throw new EntityNotFoundException("User with id " + num + " not found.");
//		}
//
//		user.update(updateUser);
//		return userRepository.save(user);
//	}
//
//	@Transactional
//	public User deleteUser(Long num) {
//		User deleteUser = userRepository.findById(num).orElse(null);
//		if (deleteUser == null) {
//			throw new EntityNotFoundException("User with id " + num + " not found.");
//		}
//		deleteUser.delete(deleteUser);
//		return userRepository.save(deleteUser);
//	}
//
//	public User findById(String username) {
//		return userRepository.findById(username);
//	}
}
