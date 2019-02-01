package com.roberto.curscomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.Categoria;
import com.roberto.curscomc.domain.Produto;
import com.roberto.curscomc.repositories.CategoriaRepository;
import com.roberto.curscomc.repositories.ProdutoRepository;
import com.roberto.curscomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired //faz a dependencia (rep) ser automaticamente instanciada pelo spring
	private ProdutoRepository repo;

	@Autowired //faz a dependencia (rep) ser automaticamente instanciada pelo spring
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id){
		//no java 7 seria Categoria obj = repo.finDOne
		Optional<Produto> obj = repo.findById(id); 
		
		//se o obj for nulo, estoura a exception pelo Handler e não Try catch
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
