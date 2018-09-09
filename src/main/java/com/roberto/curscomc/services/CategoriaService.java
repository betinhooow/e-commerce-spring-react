package com.roberto.curscomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.Categoria;
import com.roberto.curscomc.domain.Cliente;
import com.roberto.curscomc.dto.CategoriaDTO;
import com.roberto.curscomc.repositories.CategoriaRepository;
import com.roberto.curscomc.services.exception.DataIntegrityException;
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
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id){
		find(id);
		try{
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possivel excluir categoria que possui produtos.");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDTO){
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}

	private void updateData(Categoria newObj, Categoria obj){
		newObj.setNome(obj.getNome());
	}
}
