package com.docktech.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docktech.domain.Client;
import com.docktech.dto.ClientDTO;
import com.docktech.logger.ApplicationLogger;
import com.docktech.repository.ClientRepository;
import com.docktech.services.exceptions.DataBaseException;
import com.docktech.services.exceptions.DataIntegrityException;
import com.docktech.services.exceptions.ObjectNotFoundException;

@Service
public class ClientServices {

	@Autowired
	private ClientRepository repo;

	private static ApplicationLogger logger = new ApplicationLogger(ClientServices.class);

	public ClientServices(ClientRepository repo) {
		this.repo = repo;
	}

	@Transactional
	public Client insert(Client client) {

		logger.logMessage("Iniciando inserção de cliente.");
		client.setClientId(null);
		if (clientAlreadyExists(client.getCpf())) {
			throw new DataIntegrityException("CPF já existente na base.");
		}

		try {
			client = repo.save(client);
		} catch (Throwable e) {
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
		}

		logger.logMessage("Cliente salvo com sucesso.");
		return client;

	}

	private boolean clientAlreadyExists(String cpf) {
		return repo.existsByCpf(cpf);
	}

	public Client findById(Integer id) {

		logger.logMessage("Iniciando busca de Cliente ID " + id + ".");
		Optional<Client> client;

		try {
			client = repo.findById(id);
		} catch (Throwable e) {
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
		}

		return client.orElseThrow(() -> new ObjectNotFoundException("Cliente ID " + id + " não encontrado."));

	}

	public List<Client> findAllClients() {

		logger.logMessage("Iniciando busca de todos os clientes na base.");
		List<Client> list = new ArrayList<>();
		
		try {
			list = repo.findAll();
		} catch (Throwable e) {
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
		}
		
		logger.logMessage("Busca concluída com sucesso.");
		return list;

	}

	public Client fromDTO(ClientDTO dto) {
		return new Client(null, dto.getCpf(), dto.getName());
	}

}
