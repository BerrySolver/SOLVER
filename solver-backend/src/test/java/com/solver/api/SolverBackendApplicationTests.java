package com.solver.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SolverBackendApplicationTests {

	@Test
	void contextLoads() {
		String version = org.springframework.core.SpringVersion.getVersion();
		
		System.out.println(version);
	}

}
