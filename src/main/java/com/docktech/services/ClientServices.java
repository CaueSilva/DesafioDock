package com.docktech.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.docktech.services.exceptions.ObjectNotFoundException;
import com.docktech.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.docktech.domain.Client;
import com.docktech.dto.ClientDTO;
import com.docktech.repository.ClientRepository;

@Service
public class ClientServices {
	
	@Autowired
	private ClientRepository repo;
	
	@Transactional
	public Client insert(Client client) {
		client.setClientId(null);
		try {
			client = repo.save(client);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("CPF já existente na base.");
		}
		return client;
	}
	
	public Client findById(Integer id) {
		Optional<Client> client = repo.findById(id);
		return client.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente ID "+id+" não encontrado."));
	}
	
	public List<Client> findAllClients(){
		return repo.findAll();
	}
	
	
	public Client fromDTO(ClientDTO dto) {
		return new Client(null, dto.getCpf(), dto.getName());
	}
	
}
