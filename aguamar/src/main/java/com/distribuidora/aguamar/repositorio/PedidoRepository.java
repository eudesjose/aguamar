package com.distribuidora.aguamar.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.distribuidora.aguamar.modelos.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, String>{

	Pedido findByIdPedido(int idPedido);
}
