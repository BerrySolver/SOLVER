package com.solver.api.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
	String uploadFolder = "upload";
	
	/*실제로 사용할 때는 이렇게 설정
	 * uploadPath = getServletContext().getReadPath("/");
	 * */
	String uploadPath = "C:" + File.separator + "Users" + File.separator + "HM" + File.separator + "pictures";
	
	/* 답변 수정 */
	@PostMapping("/image/drag")
	public Object dragImage(@RequestPart MultipartFile upload, Principal principal) throws IllegalStateException, IOException 
	{
		
		String fileName = upload.getOriginalFilename();
		
		UUID uuid = UUID.randomUUID();
		
		String extension = FilenameUtils.getExtension(fileName);
		
		String savingFileName = uuid + "." + extension;
		
		File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
		upload.transferTo(destFile);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("uploaded", 1);
		map.put("url", uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
		map.put("fileName", savingFileName);
		
		return map;
	}
}
