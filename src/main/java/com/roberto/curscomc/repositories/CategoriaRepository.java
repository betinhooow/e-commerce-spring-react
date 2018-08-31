package com.roberto.curscomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roberto.curscomc.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>
/*Integer = atributo identificador, no caso o ID da categoria              */ {
	
}
