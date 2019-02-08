package com.roberto.curscomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.roberto.curscomc.domain.Cidade;
import com.roberto.curscomc.domain.Cliente;
import com.roberto.curscomc.domain.Endereco;
import com.roberto.curscomc.domain.enums.TipoCliente;
import com.roberto.curscomc.dto.ClienteDTO;
import com.roberto.curscomc.dto.ClienteNewDTO;
import com.roberto.curscomc.repositories.ClienteRepository;
import com.roberto.curscomc.repositories.EnderecoRepository;
import com.roberto.curscomc.services.exception.DataIntegrityException;
import com.roberto.curscomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //faz a dependencia (rep) ser automaticamente instanciada pelo spring
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Cliente find(Integer id){
		//no java 7 seria Cliente obj = repo.finOne
		Optional<Cliente> obj = repo.findById(id); 
		
		//se o obj for nulo, estoura a exception pelo Handler e não Try catch
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: "+ Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj){
		obj.setId(null); //coloca id nullo, pq se acaso tiver valor, ele atualiza ao inves de insere
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj){
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id){
		find(id);
		try{
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possivel excluir, pois há entidades relacionadas -> TO DO.");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO){
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO){
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2()!=null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3()!=null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getNome());
	}
}
