package com.nttdata.bootcamp.actuators;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@RestControllerEndpoint(id = "espacioDisco")
public class CustomRestActuator {
	
	@GetMapping("/")
	public ResponseEntity<String> getEspacioDisco() {
		String msg = "Espacio en disco restante: " + Math.round(Math.random()*100) + "%";
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
