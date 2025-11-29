package com.SecurityApp.SpringSecurityApplication;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.ProdReadyFeaturesApplication;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

@SpringBootTest(classes = ProdReadyFeaturesApplication.class)
class SpringSecurityApplicationTests {


    @Autowired
    private JwtService jwtService;

	@Test
	void contextLoads() {


        UserEntity user = new UserEntity(4L, "janeDoe@gmail.com", "1234");

        String token = jwtService.generateJwtToken(user);
        System.out.println(token);

        Long id = jwtService.getUserIdFromJwtToken(token);
        System.out.println(id);

	}

}
