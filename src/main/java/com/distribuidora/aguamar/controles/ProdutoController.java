package com.distribuidora.aguamar.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.distribuidora.aguamar.modelos.Produto;
import com.distribuidora.aguamar.repositorio.ProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@RequestMapping(value="/novoProduto", method=RequestMethod.GET)
	public String formularioNovoProduto() {	
		return "produto/formNovoProduto";
	}
	
	@RequestMapping(value="/salvarProduto", method=RequestMethod.POST)
	public String salvarProduto(Produto produto) {
		produtoRepository.save(produto);
		return "redirect:/produtos";
	}
	
	@RequestMapping(value = "/produtos", method=RequestMethod.GET)
	public ModelAndView getProdutos() {
		ModelAndView mv = new ModelAndView("produto/produtos");
		Iterable<Produto> produtos = produtoRepository.findAll();
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@RequestMapping(value = "editar/{idProduto}", method = RequestMethod.GET)
	public ModelAndView editarProduto(@PathVariable("idProduto") long idProduto) {
		Produto produto = produtoRepository.findByIdProduto(idProduto);
		ModelAndView mv = new ModelAndView("produto/editarProduto");
		mv.addObject("produto", produto);
		return mv;
	}
	
	@PostMapping("/atualizarProduto")
    public String atualizarProduto(Produto produto) {
		produtoRepository.save(produto);
        return "redirect:/produtos";
    }
	
	@RequestMapping(value="excluirProduto/{idProduto}", method = RequestMethod.GET)
	public String excluirProduto(@PathVariable("idProduto") long idProduto) {
		Produto produto = produtoRepository.findByIdProduto(idProduto);
		produtoRepository.delete(produto);
		return "redirect:/produtos";
	}

}
