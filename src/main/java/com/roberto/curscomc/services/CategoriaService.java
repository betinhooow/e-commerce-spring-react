package com.roberto.curscomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.Categoria;
import com.roberto.curscomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired //faz a dependencia (rep) ser automaticamente instanciada pelo spring
	private CategoriaRepository repo;
	
	public Categoria find(Integer id){
		Optional<Categoria> obj = repo.findById(id); 
		//no java 7 seria Categoria obj = repo.finOne
		
		return obj.orElse(null);
	}
	
}
