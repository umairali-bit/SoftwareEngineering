package com.example.testing.testingapp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@Slf4j

@SpringBootTest
class TestingApplicationTests {

    @BeforeEach
    void setUp() {
        log.info("running beforeEach");
    }

    @AfterEach
    void tearDown() {
        log.info("running afterEach");
    }

    @BeforeAll
    static void beforeAll() {
        log.info("running beforeAll");
    }

    @AfterAll
    static void tearDownAll() {
        log.info("running afterAll");
    }


    @Test
    void contextLoads() {
        log.info("test is running");
    }

    //  Assertions.assertEquals(expected, actual) (JUnit)
    @Test
    void testSum() {
        int result = 2 + 3;

        Assertions.assertEquals(5, result);

    }

    //  AssertJ
    @Test
    void assertj_example() {
        int sum = 2 + 3;
        assertThat(sum).isEqualTo(5).isCloseTo(6, Offset.offset(1));

        assertThat("Apple").isEqualTo("Apple")
                .startsWith("App")
                .endsWith("le")
                .hasSize(5);

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

    @Test
    void testDivideTwoNumbers_whenDenominatorIsZero_ThenArithmeticException() {

        int a = 5;
        int b = 0;

        assertThatThrownBy(() -> divideTwoNumbers(a, b))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("divide by zero");

    }


    double divideTwoNumbers(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            throw new ArithmeticException("divide by zero");
        }
    }
}






