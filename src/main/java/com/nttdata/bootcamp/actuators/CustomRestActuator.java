package com.nttdata.bootcamp.actuators;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
@RestControllerEndpoint(id = "espacioDisco")
public class CustomRestActuator {
	
	public Counter counter;
	
	public CustomRestActuator(MeterRegistry registry) {
		this.counter = Counter.builder("espacio.ver").description("Invocaciones consulta espacio").register(registry);
	}
	
	@GetMapping("/")
	public ResponseEntity<String> getEspacioDisco() {
		counter.increment();
		String msg = "Espacio en disco restante: " + Math.round(Math.random()*100) + "%";
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
