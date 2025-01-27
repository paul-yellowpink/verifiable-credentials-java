package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtVerifiablePresentation extends JwtWrappingObject<VerifiablePresentation> {

	public JwtVerifiablePresentation(JWTClaimsSet payload, VerifiablePresentation payloadObject, JWSObject jwsObject, String compactSerialization) {

		super(payload, payloadObject, jwsObject, compactSerialization);
	}

	/*
	 * Factory methods
	 */

	public static JwtVerifiablePresentation fromCompactSerialization(String compactSerialization) throws ParseException {

		SignedJWT signedJWT = SignedJWT.parse(compactSerialization);

		JWTClaimsSet jwtPayload = signedJWT.getJWTClaimsSet();
		Map<String, Object> jsonObject = (Map<String, Object>) jwtPayload.getClaims().get(JwtKeywords.JWT_CLAIM_VC);
		VerifiablePresentation payloadVerifiablePresentation = VerifiablePresentation.fromJsonObject(new LinkedHashMap<>(jsonObject));

		return new JwtVerifiablePresentation(jwtPayload, payloadVerifiablePresentation, signedJWT, compactSerialization);
	}
}
