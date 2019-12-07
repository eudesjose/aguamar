package com.distribuidora.aguamar.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.distribuidora.aguamar.modelos.Cliente;
import com.distribuidora.aguamar.modelos.ItemPedido;
import com.distribuidora.aguamar.modelos.Pedido;
import com.distribuidora.aguamar.modelos.Produto;
import com.distribuidora.aguamar.repositorio.ClienteRepository;
import com.distribuidora.aguamar.repositorio.ItemPedidoRepository;
import com.distribuidora.aguamar.repositorio.PedidoRepository;
import com.distribuidora.aguamar.repositorio.ProdutoRepository;

@Controller
public class PedidoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	ArrayList<ItemPedido> itens = new ArrayList<ItemPedido>();

	@RequestMapping(value = "/novoPedido", method = RequestMethod.GET)
	public String novoPedido() {
		return "pedido/formNovoPedido";
	}

	@RequestMapping(value = "/adicionarItemPedido", method = RequestMethod.POST)
	public String addItemEncomenda(@RequestParam("quantidade") int quantidade,
			@RequestParam("nomeProduto") String produto) {

		Produto prod = produtoRepository.findByNomeProduto(produto);

		ItemPedido item = new ItemPedido();
		item.setProduto(prod);
		item.setQuantidade(quantidade);
		itens.add(item);

		return "redirect:/novoPedido";
	}

	@RequestMapping(value = "/salvarPedido", method = RequestMethod.POST)
	public String salvarCliente() {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = clienteRepository.findByLogin(authentication.getName());

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);

		for (int i = 0; i < itens.size(); i++) {
			ItemPedido item = itens.get(i);
			pedidoRepository.save(pedido);
			item.setPedido(pedido);
			itemPedidoRepository.save(item);

		}
		pedido.setItens(itens);
		pedidoRepository.save(pedido);
		itens.clear();

		return "redirect:/novoPedido";
	}

	@RequestMapping(value = "/pedidos", method = RequestMethod.GET)
	public ModelAndView listaPedidos() {
		ModelAndView mv = new ModelAndView("pedido/listaPedidos");

		Iterable<Pedido> pedidos = pedidoRepository.findAll();
		Iterable<ItemPedido> itens = itemPedidoRepository.findAll();
		mv.addObject("itens", itens);
		mv.addObject("pedidos", pedidos);

		return mv;
	}

	@RequestMapping(value = "/registrarPedidoEntregue", method = RequestMethod.POST)
	public String registrarPedidoEntregue(@RequestParam("entregas") List<String> entregas) {
		for(int i=0; i<entregas.size(); i++) {
			Pedido pedido = pedidoRepository.findByIdPedido(Integer.parseInt(entregas.get(i)));
			pedidoRepository.delete(pedido);
		}
		return "redirect:/pedidos";
	}
}
