package com.nttdata.bootcamp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.repository.Usuario;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class Controller {
	
	private Counter counterConsulta;
	private Counter counterAdd;
	private Counter counterDel;
	private List<Usuario> usuarios = new ArrayList<>();
	
	public Controller(MeterRegistry registry) {
		this.counterConsulta = Counter.builder("usuarios.ver").description("Invocaciones ver usuarios").register(registry);
		this.counterAdd = Counter.builder("usuarios.add").description("Invocaciones nuevo usuarios").register(registry);
	}
	
	@GetMapping("/usuarios")
	public List<Usuario> verClientes() {
		counterConsulta.increment();
		return usuarios;
	}
	
	@GetMapping("/usuarios/nuevo/{nombre}")
	public String nuevoCliente(@PathVariable(value="nombre") String nombre) {
		counterAdd.increment();
		usuarios.add(new Usuario(nombre));
		return "Añadido " + nombre;
	}
	
	@GetMapping("/usuarios/borrar/{nombre}")
	public String borrarCliente(@PathVariable(value="nombre") String nombre) {
		counterDel.increment();		
		boolean borrado = usuarios.remove(nombre);
		
		if (borrado)
			return "Usuario borrado: " + nombre;
		else
			return "No existe dicho usuario";
	}
}
