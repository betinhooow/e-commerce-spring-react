package com.roberto.curscomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roberto.curscomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>
/*Integer = atributo identificador, no caso o ID da categoria              */ {
	
}
