package com.ouath.example.clientcredentialsserver;

import com.ouath.example.clientcredentialsserver.entity.OauthClientDetails;
import com.ouath.example.clientcredentialsserver.repository.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

	@Component
	public static class AppStartRunner implements ApplicationRunner {
		@Autowired
		OauthClientDetailsRepository repo;

		@Autowired
		PasswordEncoder encoder;
		@Override
		public void run(ApplicationArguments args) throws Exception {
			OauthClientDetails clientDetails = OauthClientDetails.builder()
					.clientId("clientapp")
					.resourceIds(null)
					.clientSecret(encoder.encode("1234"))
					.scope("read_profile,read_posts")
					.authorizedGrantTypes("authorization_code")
					.webServerRedirectUri("http://localhost:9000/callback")
					.authorities(null)
					.accessTokenValidity(300)
					.refreshTokenValidity(-1)
					.additionalInformation(null)
					.autoapprove("false")
					.build();
			repo.save(clientDetails);
		}
	}

}
