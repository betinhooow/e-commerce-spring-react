package com.roberto.curscomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.Categoria;
import com.roberto.curscomc.repositories.CategoriaRepository;
import com.roberto.curscomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired //faz a dependencia (rep) ser automaticamente instanciada pelo spring
	private CategoriaRepository repo;
	
	public Categoria find(Integer id){
		//no java 7 seria Categoria obj = repo.finOne
		Optional<Categoria> obj = repo.findById(id); 
		
		//se o obj for nulo, estoura a exception pelo Handler e não Try catch
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj){
		obj.setId(null); //coloca id nullo, pq se acaso tiver valor, ele atualiza ao inves de insere
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj){
		find(obj.getId());
		return repo.save(obj);
	}
}
