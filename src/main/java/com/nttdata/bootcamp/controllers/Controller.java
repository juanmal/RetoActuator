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
	private List<String> usuarios = new ArrayList<>();
	
	public Controller(MeterRegistry registry) {
		this.counterConsulta = Counter.builder("usuarios.ver").description("Invocaciones ver usuarios").register(registry);
		this.counterAdd = Counter.builder("usuarios.add").description("Invocaciones nuevo usuario").register(registry);
		this.counterDel = Counter.builder("usuarios.del").description("Invocaciones borrar usuario").register(registry);
	}
	
	@GetMapping("/usuarios")
	public List<String> verClientes() {
		counterConsulta.increment();
		return usuarios;
	}
	
	@GetMapping("/usuarios/nuevo/{nombre}")
	public String nuevoCliente(@PathVariable(value="nombre") String nombre) {
		counterAdd.increment();
		if (usuarios.indexOf(nombre) != -1) {
			return "Ya existe un usuario con ese nombre";
		} else {
			usuarios.add(nombre);
			return "Añadido " + nombre;		
		}
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
