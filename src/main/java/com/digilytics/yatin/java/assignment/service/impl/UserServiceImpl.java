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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.digilytics.yatin.java.assignment.dao.jpa.RoleJpaDao;
import com.digilytics.yatin.java.assignment.dao.jpa.UserJpaDao;
import com.digilytics.yatin.java.assignment.dao.jpa.UserRoleJpaDao;
import com.digilytics.yatin.java.assignment.entity.user.User;
import com.digilytics.yatin.java.assignment.ex.ValidationException;
import com.digilytics.yatin.java.assignment.service.FileStorageService;
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

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private static String errors = null;

	/**
	 * this method is used to create file and if file already exist return the file
	 * 
	 * @param path
	 * @param fileName
	 * @return {@link FileOutputStream}
	 */
	private static FileOutputStream createErrorFile(String path, String fileName, String[] row) {

		FileOutputStream errorFile = null;
		try {
			errorFile = new FileOutputStream(path + fileName, true);
			String s = "";

			for (int i = 0; i < row.length; i++) {
				s = s + row[i] + ", ";
			}

			errorFile.write(s.getBytes());
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

	@Override
	public UserResponse save(UserRequest request) {
		User user = request.toEntity();
		user.setPassword(userPasswordEncoder.encode("12345"));
		user = userJpaDao.save(user);
		for (Long roleId : request.getRoleIds()) {
			userRoleJpaDao.save(user.getId(), roleId);

		}

		return fetch(user, request.getRoleIds());
	}

	/**
	 * this method is used to validate the user request
	 * 
	 * @param row
	 * @param idNameMap
	 * @return
	 * @throws IOException
	 */
	private UserRequest validateUserRequest(String[] row, Map<String, Long> idNameMap, Set<String> storedEmails)
			throws IOException {

		UserRequest userRequest = new UserRequest();
		Boolean flag = false;
		FileOutputStream errorFile = null;
		String tempErrors = null;

		if (row.length < 3 || row[0].equals("") || row[1].equals("") || row[2].isEmpty()) {
			errorFile = createErrorFile(errorFilePath, "errorFile.csv", row);
			tempErrors = "Data is missing";
			errors = tempErrors;
			errorFile.write(tempErrors.getBytes());
			errorFile.write(10);
			errorFile.close();
			return null;
		}

		userRequest.setEmail(row[0]);
		userRequest.setName(row[1]);
		List<String> requestRoles = Arrays.asList(row[2].split("#"));
		Set<Long> roleIds = new HashSet<Long>();

		// Long userId = userJpaDao.findIdByEmailAndActive(userRequest.getEmail(),
		// true);

		if (storedEmails.contains(userRequest.getEmail())) {
			// throw new ValidationException(String.format("This user's email (%s) already
			// exists", request.getEmail()));

			if (flag == false) {
				errorFile = createErrorFile(errorFilePath, "errorFile.csv", row);
				flag = true;
			}
			tempErrors = "Email already exist";

		}

		for (String requestRole : requestRoles) {
			if (idNameMap.containsKey(requestRole)) {
				roleIds.add(idNameMap.get(requestRole));
			} else {
				if (flag == false) {
					errorFile = createErrorFile(errorFilePath, "errorFile.csv", row);
					tempErrors = String.format("Invaid Role %s", requestRole);
					flag = true;
				} else {
					tempErrors = tempErrors + "#" + String.format("Invaid Role %s ", requestRole);
				}

			}
		}

		if (tempErrors != null) {
			errors = tempErrors;
			errorFile.write(tempErrors.getBytes());
			errorFile.write(10);
			errorFile.close();
			return null;
		}
		userRequest.setRoleIds(roleIds);
		return userRequest;
	}

	private DigilyticsUserResponse readDataFromCsvFile(MultipartFile file) {
		int rowParsed = 0, rowFailed = 0;

		try {
			InputStreamReader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			List<String[]> rows = csvReader.readAll();

			List<RoleResponse> roleResponseList = roleDao.findAllByActiveTrue();
			Map<String, Long> idNameMap = new HashMap<String, Long>();

			for (RoleResponse roleResponse : roleResponseList) {
				idNameMap.put(roleResponse.getName(), roleResponse.getId());
			}

			Set<String> storedEmails = userJpaDao.findEmailAndActive(true);
			for (String[] row : rows) {
				UserRequest request = validateUserRequest(row, idNameMap, storedEmails);
				if (request != null) {
					UserResponse response = save(request);
					storedEmails.add(response.getEmail());
					if (response != null) {
						logger.info("user successfully saved", response.getId());
						rowParsed++;
					}

				} else {
					rowFailed++;
				}

			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		if (errors == null)
			return new DigilyticsUserResponse(rowParsed, rowFailed);
		else {
			return new DigilyticsUserResponse(rowParsed, rowFailed, "/download/errorFile.csv");
		}

	}

	@Override
	public DigilyticsUserResponse registerFromFile(MultipartFile file) {

		errors = null;

		if (file == null || file.isEmpty() || file.getSize() == 0)
			throw new ValidationException("Please upload valid file.");

		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!extension.equalsIgnoreCase("csv")) {
			throw new ValidationException("please upload a csv file");
		}

		return readDataFromCsvFile(file);
	}

	@Override
	public Resource downloadErrorFileAsResource(String file) {

		return FileStorageService.fetchFile(errorFilePath + file);
	}

}
