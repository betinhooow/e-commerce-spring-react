package com.roberto.curscomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.Pedido;
import com.roberto.curscomc.repositories.PedidoRepository;
import com.roberto.curscomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired //faz a dependencia (rep) ser automaticamente instanciada pelo spring
	private PedidoRepository repo;
	
	public Pedido find(Integer id){
		//no java 7 seria Categoria obj = repo.finOne
		Optional<Pedido> obj = repo.findById(id); 
		
		//se o obj for nulo, estoura a exception pelo Handler e não Try catch
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Pedido.class.getName()));
	}
	
}
