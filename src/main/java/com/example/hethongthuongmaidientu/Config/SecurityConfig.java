package com.example.hethongthuongmaidientu.Config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Value("${secretkey}")
	private String secRetKey;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(request ->
//			request.requestMatchers(HttpMethod.POST,"/autho/login").permitAll()
//			.requestMatchers(HttpMethod.GET,"/autho/login").permitAll()
//			.requestMatchers(HttpMethod.POST,"autho/nhanvien").permitAll()
			request.anyRequest().permitAll());
//		httpSecurity.oauth2ResourceServer(oauth2->
//			oauth2.jwt(t ->t.decoder(jwtDecoder()) )
//				);
		httpSecurity.csrf(httpscuritycrsf -> httpscuritycrsf.disable());
		return httpSecurity.build();
	}
	
	@Bean
	JwtDecoder jwtDecoder() {
	SecretKeySpec secretKeySpec= new SecretKeySpec(secRetKey.getBytes(),"HS512");
		return NimbusJwtDecoder.withSecretKey(secretKeySpec)
				.macAlgorithm(MacAlgorithm.HS512)
				.build();
	}
}
