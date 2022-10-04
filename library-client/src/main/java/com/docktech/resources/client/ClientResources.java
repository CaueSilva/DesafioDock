package com.docktech.resources.client;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.docktech.domain.client.Client;
import com.docktech.dto.client.ClientDTO;
import com.docktech.model.client.ClientPageRequest;
import com.docktech.services.client.ClientServices;

@RestController
@RequestMapping(value="/api/client")
public class ClientResources {
	
	@Autowired
	private ClientServices service;
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<ClientDTO> findClientById(@PathVariable Integer id) {
		Client client = service.findById(id);
		return ResponseEntity.ok().body(new ClientDTO(client));
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ClientDTO>> findAllClients(
				@RequestParam(value="page", defaultValue="0") Integer page,
				@RequestParam(value="linesPerPage", defaultValue="10") Integer linesPerPage,
				@RequestParam(value="direction", defaultValue = "ASC") String direction,
				@RequestParam(value="orderBy", defaultValue = "nome") String orderBy
			){
		Page<Client> clientList = service.findAllClients(new ClientPageRequest(page, linesPerPage, direction, orderBy));
		Page<ClientDTO> clientListDto = clientList.map(client -> new ClientDTO(client));
		return ResponseEntity.ok().body(clientListDto);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Client> insertClient(@Valid @RequestBody ClientDTO clientDto){
		Client clientObj = service.fromDTO(clientDto);
		clientObj = service.insert(clientObj);
		return ResponseEntity.ok().body(clientObj);
	}
	
}
