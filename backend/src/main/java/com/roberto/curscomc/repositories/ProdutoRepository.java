package com.roberto.curscomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roberto.curscomc.domain.Categoria;
import com.roberto.curscomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>
/*Integer = atributo identificador, no caso o ID da categoria              */ {

	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	
}
