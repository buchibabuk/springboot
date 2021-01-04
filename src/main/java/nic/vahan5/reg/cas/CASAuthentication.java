/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan5.reg.cas;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Ramesh
 */
@Service
public class CASAuthentication {

	@Value("${cas.server.validation}")
	private String CAS_SERVICE_VALIDATE;

	@Value("${cas.service.url}")
	private String CAS_SERVICE_ID;

	private final WebClient client = WebClient.builder()
											  .baseUrl("")
											  .build();

	// Validate the Service ticket in CAS Server
	public boolean casServiceValidate(String serviceTicket) {

		String validation = client.get()
				.uri(CAS_SERVICE_VALIDATE + "service=" + CAS_SERVICE_ID + "&ticket=" + serviceTicket.trim())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		
		System.out.println("***********************" + validation + "***************************");
		if (validation.indexOf("authenticationFailure") < 0) {
			return true;
		}
		return false;
	}
}
