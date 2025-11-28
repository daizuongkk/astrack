package service;

import exception.AuthenticationException;
import exception.ValidationException;
import repository.IUserRepository;
import validator.InputValidator;

/**
 * Service layer for authentication operations.
 */
public class AuthenticationService {

	private final IUserRepository userRepository;

	public AuthenticationService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Authenticates a user.
	 * 
	 * @param username the username
	 * @param password the password
	 * @throws ValidationException     if input is invalid
	 * @throws AuthenticationException if authentication fails
	 */
	public void authenticate(String username, String password) {
		if (!InputValidator.isNotBlank(username) || !InputValidator.isNotBlank(password)) {
			throw new ValidationException("Vui lòng nhập đầy đủ thông tin đăng nhập");
		}

		if (!userRepository.authenticate(username, password)) {
			throw new AuthenticationException("Sai tên đăng nhập hoặc mật khẩu");
		}
	}

	/**
	 * Registers a new user.
	 * 
	 * @param username        the username
	 * @param password        the password
	 * @param confirmPassword the password confirmation
	 * @throws ValidationException if validation fails
	 */
	public void register(String username, String password, String confirmPassword) {
		validateRegistrationInput(username, password, confirmPassword);

		if (userRepository.usernameExists(username)) {
			throw new ValidationException("Tên đăng nhập đã tồn tại");
		}

		userRepository.register(username, password);
	}

	private void validateRegistrationInput(String username, String password, String confirmPassword) {
		if (!InputValidator.isNotBlank(username) || !InputValidator.isNotBlank(password)
				|| !InputValidator.isNotBlank(confirmPassword)) {
			throw new ValidationException("Vui lòng nhập đầy đủ thông tin");
		}
		if (!InputValidator.isValidUsername(username, "^[a-zA-Z0-9_-]{3,16}$")) {
			throw new ValidationException("Tên đăng nhập không chứa kí tự đặc biệt");
		}
		if (!InputValidator.passwordsMatch(password, confirmPassword)) {
			throw new ValidationException("Xác nhận mật khẩu không khớp");
		}

		if (!InputValidator.isValidUsernameLength(username, 6)) {
			throw new ValidationException("Tên đăng nhập có ít nhất 6 ký tự");
		}

		if (!InputValidator.isValidPassword(password, 6)) {
			throw new ValidationException("Mật khẩu có ít nhất 6 ký tự");
		}
	}
}
