package com.nttdata.bootcamp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.repository.Usuario;
import com.nttdata.bootcamp.repository.UsuarioBuilder;

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
		this.counterAdd = Counter.builder("usuarios.add").description("Invocaciones nuevo usuario").register(registry);
		this.counterDel = Counter.builder("usuarios.del").description("Invocaciones borrar usuario").register(registry);
	}
	
	@GetMapping("/usuarios")
	public List<Usuario> verClientes() {
		counterConsulta.increment();
		return usuarios;
	}
	
	@GetMapping("/usuarios/nuevo/{nombre}")
	public String nuevoCliente(@PathVariable(value="nombre") String nombre) {
		counterAdd.increment();
		usuarios.add(new UsuarioBuilder().nombre(nombre).build());
		return "Añadido " + nombre;
	}
	
	@GetMapping("/usuarios/borrar/{nombre}")
	public String borrarCliente(@PathVariable(value="nombre") String nombre) {
		counterDel.increment();
		boolean borrado = false;
		for (Usuario u : usuarios) {
			if (u.getNombre().equals(nombre)) {
				borrado = usuarios.remove(u);
				break;
			}
		}
		
		if (borrado)
			return "Usuario borrado: " + nombre;
		else
			return "No existe dicho usuario";
	}
}
