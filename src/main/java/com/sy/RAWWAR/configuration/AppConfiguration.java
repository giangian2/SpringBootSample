package com.sy.RAWWAR.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sy.RAWWAR.dto.Passcode;
import com.sy.RAWWAR.http.middleware.AuthEndpointFIlter;
import com.sy.RAWWAR.model.ApiConfig;
import com.sy.RAWWAR.model.ApiConfigFromYaml;
import com.sy.RAWWAR.repository.PasscodeRepository;

@Configuration
public class AppConfiguration {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(ApiConfig apiConfig, PasscodeRepository repo) {
		return args -> {
			System.out.println(apiConfig.getApiKey());

		};

	}

	@Bean
	public FilterRegistrationBean<AuthEndpointFIlter> emailFilter() {
		var registrationBean = new FilterRegistrationBean<AuthEndpointFIlter>();
		registrationBean.setFilter(new AuthEndpointFIlter(apiConfig()));
		registrationBean.addUrlPatterns("/auth/*");
		registrationBean.setOrder(1);

		return registrationBean;
	}

	@Bean
	public ApiConfig apiConfig() {
		return new ApiConfigFromYaml();
	}

}
