package com.digilytics.yatin.java.assignment.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.digilytics.yatin.java.assignment.dao.jpa.RoleJpaDao;
import com.digilytics.yatin.java.assignment.dao.jpa.UserJpaDao;
import com.digilytics.yatin.java.assignment.dao.jpa.UserRoleJpaDao;
import com.digilytics.yatin.java.assignment.entity.user.User;
import com.digilytics.yatin.java.assignment.ex.ValidationException;
import com.digilytics.yatin.java.assignment.service.UserService;
import com.digilytics.yatin.java.assignment.view.role.RoleResponse;
import com.digilytics.yatin.java.assignment.view.user.DigilyticsUserResponse;
import com.digilytics.yatin.java.assignment.view.user.UserRequest;
import com.digilytics.yatin.java.assignment.view.user.UserResponse;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserJpaDao userJpaDao;

	@Autowired
	private RoleJpaDao roleDao;

	@Autowired
	private UserRoleJpaDao userRoleJpaDao;

	@Value("${file.upload-dir}")
	private String errorFilePath;

	private static PasswordEncoder userPasswordEncoder = new BCryptPasswordEncoder();

	static int rowParsed = 0;
	static int rowFailed = 0;
	static String errors = null;

	public static FileOutputStream createErrorFile(String path) {

		FileOutputStream errorFile = null;
		try {
			errorFile = new FileOutputStream(path, true);
			// true/false in 2nd arg indicate that will continue inside file otherwise it'll
			// create a new file and write it and previous data is lost
		} catch (IOException e) {
			System.err.println(e.getMessage() + "Error File Not Created");
		}
		return errorFile;

	}

	/**
	 * this method used to fetch user response using user entity and roleIds
	 *
	 * @param user
	 * @param roleIds
	 * @return {@link UserResponse}
	 */
	private UserResponse fetch(User user, Set<Long> roleIds) {
		UserResponse response = UserResponse.get(user);
		response.setRoles(new HashSet<>());
		RoleResponse roleResponse;
		for (Long roleId : roleIds) {
			roleResponse = roleDao.findResponseById(roleId);
			response.getRoles().add(roleResponse);
		}
		return response;
	}

	/**
	 * this method used to validate user request like username already exist or not
	 *
	 * @param request
	 */
//	private void validate(UserRequest request) {
//
//		FileOutputStream errorFile = createErrorFile(errorFilePath);
//		if (userJpaDao.existsByUsername(request.getEmail().toString())) {
//			// throw new ValidationException(String.format("This user (%s) already exist",
//			// request.getEmail()));
//			rowFailed++;
//			return;
//		} else if (userJpaDao.findIdByEmailAndActive(request.getEmail(), true) != null) {
//			// throw new ValidationException(String.format("This user's email (%s) already
//			// exists", request.getEmail()));
//			rowFailed++;
//			return;
//		}
//
//	}

	@Override
	public UserResponse save(UserRequest request) {
		// validate(request);
		User user = request.toEntity();
		user.setPassword(userPasswordEncoder.encode("12345"));
		user = userJpaDao.save(user);
		for (Long roleId : request.getRoleIds()) {
			userRoleJpaDao.save(user.getId(), roleId);
		}

		return fetch(user, request.getRoleIds());
	}

	private UserRequest validateUserRequest(String[] row, Map<String, Long> idNameMap) throws IOException {

		UserRequest userRequest = new UserRequest();

//		if (row == null) {
//			// throw new ValidationException(String.format("Row is empty"));
//			rowFailed++;
//		}

		userRequest.setEmail(row[0]);
		userRequest.setName(row[1]);
		List<String> requestRoles = Arrays.asList(row[2].split("#"));
		Set<Long> roleIds = new HashSet<Long>();
		Boolean flag = false;
		FileOutputStream errorFile = null;
		String tempErrors = null;

		if (userJpaDao.findIdByEmailAndActive(userRequest.getEmail(), true) != null) {
			// throw new ValidationException(String.format("This user's email (%s) already
			// exists", request.getEmail()));
			rowFailed++;

			if (flag == false) {
				errorFile = createErrorFile(errorFilePath);
				String s = userRequest.getName() + "," + userRequest.getEmail() + "," + requestRoles.toString();
				errorFile.write(s.getBytes());
				tempErrors = "Invalid Email ";
				flag = true;
			}

		}

		for (String requestRole : requestRoles) {
			if (idNameMap.containsKey(requestRole)) {
				roleIds.add(idNameMap.get(requestRole));
			} else {
				rowFailed++;
				errorFile = createErrorFile(errorFilePath);
				if (flag == false) {
					String s = userRequest.getName() + "," + userRequest.getEmail() + "," + requestRoles.toString();
					errorFile.write(s.getBytes());
					tempErrors = String.format("Invaid Role %s", requestRole);
					flag = true;
				} else {
					tempErrors = tempErrors + "#" + String.format("Invaid Role %s ", requestRole);
				}
				errorFile.write(tempErrors.getBytes());
			}
		}

		if (tempErrors != null) {
			errors = tempErrors;
			return null;
		}
		userRequest.setRoleIds(roleIds);
		return userRequest;
	}

	private DigilyticsUserResponse readDataFromCsvFile(MultipartFile file) {
		try {
			InputStreamReader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			List<String[]> rows = csvReader.readAll();

			List<RoleResponse> roleResponseList = roleDao.findAllByActiveTrue();
			Map<String, Long> idNameMap = new HashMap<String, Long>();

			for (RoleResponse roleResponse : roleResponseList) {
				idNameMap.put(roleResponse.getName(), roleResponse.getId());
			}

			for (String[] row : rows) {
				UserRequest request = validateUserRequest(row, idNameMap);
				if (request != null) {
					UserResponse response = save(request);
					if (response.getId() != null) {
						rowParsed++;
					} else {
						rowFailed++;
					}

				}

			}

		} catch (Exception e) {

		}
		if (errors == null)
			return new DigilyticsUserResponse(rowParsed, rowFailed);
		else
			return new DigilyticsUserResponse(rowParsed, rowFailed, "/download/errorFilePath");

	}

	@Override
	public DigilyticsUserResponse registerFromFile(MultipartFile file) {
		rowParsed = 0;
		rowFailed = 0;
		errors = null;

		if (file == null || file.isEmpty() || file.getSize() == 0)
			throw new ValidationException("Please upload valid file.");

		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!extension.equalsIgnoreCase("csv")) {
			throw new ValidationException("please upload a csv file");
		}

		return readDataFromCsvFile(file);
	}

}
