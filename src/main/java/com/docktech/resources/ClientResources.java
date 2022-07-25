package com.docktech.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.docktech.domain.Client;
import com.docktech.dto.ClientDTO;
import com.docktech.services.ClientServices;

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
	public ResponseEntity<List<ClientDTO>> findAllClients(){
		List<Client> clientList = service.findAllClients();
		List<ClientDTO> clientListDto = clientList
				.stream()
				.map(client -> new ClientDTO(client))
				.collect(Collectors.toList()); 
		return ResponseEntity.ok().body(clientListDto);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Client> insertClient(@Valid @RequestBody ClientDTO clientDto){
		Client clientObj = service.fromDTO(clientDto);
		clientObj = service.insert(clientObj);
		return ResponseEntity.ok().body(clientObj);
	}
	
}
