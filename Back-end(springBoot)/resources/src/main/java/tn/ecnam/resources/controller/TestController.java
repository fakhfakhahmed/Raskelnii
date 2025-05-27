package tn.ecnam.resources.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
	@GetMapping("hello-admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> helloWorldAdmin(){
		return new ResponseEntity<>("hello world admin" , HttpStatus.OK);
	}
	@GetMapping("hello-user")
	@PreAuthorize("hasRole('ROLE_LIVREUR')")
	public ResponseEntity<String> helloWorldUser(){
		return new ResponseEntity<>("hello world user", HttpStatus.OK);
	}

}
