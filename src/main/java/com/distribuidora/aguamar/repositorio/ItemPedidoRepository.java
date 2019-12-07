package com.distribuidora.aguamar.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.distribuidora.aguamar.modelos.ItemPedido;
import com.distribuidora.aguamar.modelos.Pedido;

public interface ItemPedidoRepository extends CrudRepository<ItemPedido, String>{

	Iterable<ItemPedido> findByPedido(Pedido pedido);
	
}
