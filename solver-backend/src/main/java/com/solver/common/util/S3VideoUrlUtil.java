package com.solver.common.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class S3VideoUrlUtil {
	public static String makeS3UrlToHtml(String s3Url) {
		String s3Content = "<video data-v-6746ab92=\"\" controls=\"controls\" name=\"media\"  width=\"800px\" height=\"600px\">"
				+ "<source data-v-6746ab92=\"\" src=\""
				+ "https://solver-bucket.s3.ap-northeast-2.amazonaws.com/" + s3Url
				+ "\" type=\"video/mp4\">"
				+ "</video>";

		return s3Content;
	}

}
