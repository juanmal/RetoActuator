package com.nttdata.bootcamp.repository;

public class UsuarioBuilder {
	
	private Usuario u;
	
	public UsuarioBuilder() {
		u = new Usuario();
	}
	
	public Usuario build() {	
				
		return u;
	}
	
	public UsuarioBuilder nombre(String nombre) {
		u.setNombre(nombre);
		
		return this;
	}
}