package com.roberto.curscomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.roberto.curscomc.domain.Categoria;
import com.roberto.curscomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	//ResponseEntity ja encapsula o conteudo pro rest
	public ResponseEntity<?> find(@PathVariable Integer id){
		Categoria obj = service.find(id);		
		return ResponseEntity.ok(obj);
	}
}
