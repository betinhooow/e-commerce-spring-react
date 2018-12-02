package com.roberto.curscomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roberto.curscomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>
/*Integer = atributo identificador, no caso o ID da categoria              */ {
	
}
