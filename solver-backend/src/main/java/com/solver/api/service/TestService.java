package com.solver.api.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

//	private final AmazonS3Client amazonS3Client;
//	
//	@Value("hese-board")
//	private String bucket;
//
//	public FileUploadDTO fileUpload(MultipartFile upload, Principal principal) throws IOException {
//		File uploadFile = convert(upload)
//				.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환실패."));
//		return upload(uploadFile, "static");
//	}
//
//	private FileUploadDTO upload(File uploadFile, String dir) {
//		String sourceName = uploadFile.getName();
//		String sourceExt = FilenameUtils.getExtension(sourceName).toLowerCase();
//		String fileName = dir + "/" + LocalDateTime.now().toString().concat(".").concat(sourceExt);
//		String uploadImageUrl = putS3(uploadFile, fileName);
//		removeNewFile(uploadFile);
//		return FileUploadDTO.builder().uploaded(true).fileName(fileName).url(uploadImageUrl).build();
//	}
//
//	private void removeNewFile(File targetFile) {
//		if (targetFile.delete()) {
//			log.info("파일이 삭제되었습니다.");
//		} else {
//			log.info("파일이 삭제되지 못했습니다.");
//		}
//	}
//
//	private String putS3(File uploadFile, String fileName) {
//		try {
//			amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
//					.withCannedAcl(CannedAccessControlList.PublicRead));
//		} catch (Exception e) {
//			log.error("이미지 s3 업로드 실패");
//			log.error(e.getMessage());
//			removeNewFile(uploadFile);
//			throw new RuntimeException();
//		}
//		return amazonS3Client.getUrl(bucket, fileName).toString();
//	}
//
//	private Optional<File> convert(MultipartFile file) throws IOException {
//		File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//		if (convertFile.createNewFile()) {
//			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//				fos.write(file.getBytes());
//			}
//			return Optional.of(convertFile);
//		}
//		return Optional.empty();
//	}

}
