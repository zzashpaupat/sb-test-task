package org.covidapp;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SmokeTest {

	private static final Logger logger = LoggerFactory.getLogger(SmokeTest.class);

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		logger.info(context.getApplicationName() + " loaded!");
	}

}
