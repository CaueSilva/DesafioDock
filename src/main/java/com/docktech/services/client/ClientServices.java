package com.docktech.services.client;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.docktech.domain.client.Client;
import com.docktech.dto.client.ClientDTO;
import com.docktech.logger.ApplicationLogger;
import com.docktech.model.client.ClientPageRequest;
import com.docktech.repository.ClientRepository;
import com.docktech.services.exceptions.DataBaseException;
import com.docktech.services.exceptions.DataIntegrityException;
import com.docktech.services.exceptions.ObjectNotFoundException;
import com.docktech.services.security.SecurityServices;

@Service
public class ClientServices {

	@Autowired
	private ClientRepository repo;
	
	@Autowired
	private SecurityServices securityServices;

	private static ApplicationLogger logger = new ApplicationLogger(ClientServices.class);

	public ClientServices(ClientRepository repo) {
		this.repo = repo;
	}

	@Transactional
	public Client insert(Client client) {
		logger.logMessage("Iniciando inserção de cliente.");
		
		if (clientAlreadyExists(client.getCpf())) {
			throw new DataIntegrityException("CPF já existente na base.");
		}
		
		securityServices.checkIfIsTheSameCustomer(client.getCpf());

		try {
			client = repo.save(client);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
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
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}
		
		securityServices.checkIfIsTheSameCustomer(client.get().getCpf());

		return client.orElseThrow(() -> new ObjectNotFoundException("Cliente ID " + id + " não encontrado."));

	}

	public Page<Client> findAllClients(ClientPageRequest clientPageRequest) {

		logger.logMessage("Iniciando busca de todos os clientes na base.");
		Page<Client> list;
		
		PageRequest pageRequest = PageRequest.of(clientPageRequest.getPage(), clientPageRequest.getLinesPerPage(), Direction.valueOf(clientPageRequest.getDirection()), clientPageRequest.getOrderBy());
		
		try {
			list = repo.findAll(pageRequest);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}
		
		logger.logMessage("Busca concluída com sucesso.");
		return list;

	}

	public Client fromDTO(ClientDTO dto) {
		return new Client(null, dto.getCpf(), dto.getName());
	}
	

}
