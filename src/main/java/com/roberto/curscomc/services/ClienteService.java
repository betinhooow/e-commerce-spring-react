package com.roberto.curscomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.Cliente;
import com.roberto.curscomc.repositories.ClienteRepository;
import com.roberto.curscomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //faz a dependencia (rep) ser automaticamente instanciada pelo spring
	private ClienteRepository repo;
	
	public Cliente find(Integer id){
		//no java 7 seria Cliente obj = repo.finOne
		Optional<Cliente> obj = repo.findById(id); 
		
		//se o obj for nulo, estoura a exception pelo Handler e não Try catch
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Cliente.class.getName()));
	}
	
}
