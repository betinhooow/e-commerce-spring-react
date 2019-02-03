package com.roberto.curscomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.ItemPedido;
import com.roberto.curscomc.domain.PagamentoComBoleto;
import com.roberto.curscomc.domain.Pedido;
import com.roberto.curscomc.domain.enums.EstadoPagamento;
import com.roberto.curscomc.repositories.ItemPedidoRepository;
import com.roberto.curscomc.repositories.PagamentoRepository;
import com.roberto.curscomc.repositories.PedidoRepository;
import com.roberto.curscomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired //faz a dependencia (rep) ser automaticamente instanciada pelo spring
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	public Pedido find(Integer id){
		//no java 7 seria Categoria obj = repo.finOne
		Optional<Pedido> obj = repo.findById(id); 
		
		//se o obj for nulo, estoura a exception pelo Handler e não Try catch
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		//se for uma instancia de boleto, "gera o boleto" em pagto, setando uma data de vencimento
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		repo.save(obj);//salvando o pedido no banco
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
	
}
