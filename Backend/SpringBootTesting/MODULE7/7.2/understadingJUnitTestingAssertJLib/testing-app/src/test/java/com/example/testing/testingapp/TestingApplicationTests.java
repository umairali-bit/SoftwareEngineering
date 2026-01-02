package com.example.testing.testingapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j

@SpringBootTest
class TestingApplicationTests {

    @BeforeEach
    void setUp () {
        log.info("running beforeEach");
    }

    @AfterEach
    void tearDown () {
        log.info("running afterEach");
    }

    @BeforeAll
    static void beforeAll () {
        log.info("running beforeAll");
    }

    @AfterAll
    static void tearDownAll () {
        log.info("running afterAll");
    }


	@Test
	void contextLoads() {
        log.info("test is running");
	}

//  Assertions.assertEquals(expected, actual)
    @Test
    void testSum() {
        int result = 2 + 3;

    Assertions.assertEquals(5, result);

    }


    @Test
    @DisplayName("secondTest")
    void displayName() {
        log.info("the other test is running");

    }

    @Test
    @Disabled
    void disableTest() {

    }



}
