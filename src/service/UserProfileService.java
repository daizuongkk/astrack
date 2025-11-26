package service;

import exception.ValidationException;
import model.UserProfile;
import repository.IProfileRepository;
import validator.InputValidator;

public class UserProfileService {

	private final IProfileRepository profileRepository;

	public UserProfileService(IProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	public void saveProfile(String username, UserProfile profile) {
		validateProfile(profile);
		profileRepository.saveProfile(username, profile);
	}

	public UserProfile loadProfile(String username) {
		return profileRepository.loadProfile(username);
	}

	private void validateProfile(UserProfile profile) {
		// if (!InputValidator.isNotBlank(profile.getFullName())) {
		// throw new ValidationException("Vui lòng nhập họ và tên!");
		// }

		// if (!InputValidator.isNotBlank(profile.getEmail())) {
		// throw new ValidationException("Vui lòng nhập email!");
		// }
		if (!InputValidator.isNotBlank(profile.getEmail())) {

		}
		if (!InputValidator.isValidEmail(profile.getEmail())) {
			throw new ValidationException("Email không hợp lệ!");
		}

		if (!InputValidator.isNotBlank(profile.getPhone())) {
			throw new ValidationException("Vui lòng nhập số điện thoại!");
		}

		if (!InputValidator.isValidPhone(profile.getPhone())) {
			throw new ValidationException("Số điện thoại không hợp lệ!");
		}
	}
}
