package com.example.hethongthuongmaidientu.Config;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JWT {
	 @Value("${secretkey}")
	private  String serectkey;
	
	
	
	public  String getSerectkey() {
		return serectkey;
	}

	public  void setSerectkey(String serectkey) {
		serectkey = serectkey;
	}

	public String createToken(String sdt, String role) {
		JWSHeader header= new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet claim= new JWTClaimsSet.Builder()
				.subject(sdt+"")
				.issuer("localhost:8080/nienluannganh")
				.issueTime(new Date())
				.expirationTime(new Date(Instant.now().plus(5,ChronoUnit.HOURS).toEpochMilli()))
				.claim("scope", role)
				
				.build();
		Payload payload= new Payload(claim.toJSONObject());
		JWSObject jwt= new JWSObject(header, payload);
		try {
			jwt.sign(new MACSigner(serectkey.getBytes()));
			return jwt.serialize();
		} catch (KeyLengthException e) {
			e.printStackTrace();
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	public boolean testtoken(String token) throws JOSEException, ParseException {
		JWSVerifier jw= new MACVerifier(serectkey.getBytes());
		SignedJWT s= SignedJWT.parse(token);
		return s.verify(jw);
	}
	
	
}
