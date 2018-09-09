package com.roberto.curscomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.Cliente;
import com.roberto.curscomc.dto.ClienteDTO;
import com.roberto.curscomc.repositories.ClienteRepository;
import com.roberto.curscomc.services.exception.DataIntegrityException;
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

	public Cliente insert(Cliente obj){
		obj.setId(null); //coloca id nullo, pq se acaso tiver valor, ele atualiza ao inves de insere
		return repo.save(obj);
	}
	
	public Cliente update(Cliente obj){
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id){
		find(id);
		try{
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possivel excluir, pois há entidades relacionadas -> TO DO.");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO){
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getNome());
	}
}
