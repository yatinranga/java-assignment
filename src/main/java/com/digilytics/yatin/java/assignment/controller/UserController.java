package com.digilytics.yatin.java.assignment.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digilytics.yatin.java.assignment.service.UserService;
import com.digilytics.yatin.java.assignment.view.user.DigilyticsUserResponse;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/register")
	public DigilyticsUserResponse registerFromFile(@RequestParam("file") MultipartFile file) {
		return userService.registerFromFile(file);
	}

	@GetMapping("/download/{file}")
	public ResponseEntity<Resource> downloadErrorFile(@PathVariable String file, HttpServletRequest request) {

		Resource resource = userService.downloadErrorFileAsResource(file);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			// logger.info("Could not determine file type.");
		}

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

}
