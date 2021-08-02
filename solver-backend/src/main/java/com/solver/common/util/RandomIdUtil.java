package com.solver.common.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomIdUtil {
	public static String makeRandomId(int length) {
		//'0' 부터  'z'까지의 범위
		int leftLimit = 48;
		int rightLimit = 122;
		//생성할 id 길이
		int targetStringLength = length;
		Random random = new Random();

		String id = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();
		
		System.out.println("make id : "+id);
		
		return id;
	}
}
