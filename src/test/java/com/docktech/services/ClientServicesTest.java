package com.docktech.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.docktech.domain.client.Client;
import com.docktech.repository.ClientRepository;
import com.docktech.services.client.ClientServices;
import com.docktech.services.exceptions.DataBaseException;
import com.docktech.services.exceptions.DataIntegrityException;
import com.docktech.services.exceptions.ObjectNotFoundException;
import com.docktech.services.security.SecurityServices;

@ExtendWith(SpringExtension.class)
public class ClientServicesTest {

	private ClientServices services;
	
	@MockBean
	private ClientRepository repo;
	
	@MockBean
	private SecurityServices securityServices; 
	
	@BeforeEach
	public void setUp() {
		this.services = new ClientServices(repo, securityServices);
	}
	
	@Test
	@DisplayName("Deve salvar um cliente com sucesso")
	public void saveClientTest() {
		Client client = returnNewClient();
		Mockito.when(services.insert(client)).thenReturn(client);
		
		Client savedClient = services.insert(client);
		
		assertThat(savedClient.getCpf()).isEqualTo(client.getCpf());
		assertThat(savedClient.getName()).isEqualTo(client.getName());
	}
	
	@Test
	@DisplayName("Deve dar erro ao inserir CPF de cliente já existente")
	public void mustReturnErrorCreatingDuplicatedClient() {
		Client client = returnNewClient();
		
		Mockito.when(repo.existsByCpf(Mockito.anyString())).thenReturn(true);
		Mockito.when(repo.save(Mockito.any(Client.class))).thenThrow(DataIntegrityException.class);
		
		Throwable t = Assertions.catchThrowable(() -> services.insert(client));
		assertThat(t).isInstanceOf(DataIntegrityException.class);
	}
	
	@Test
	@DisplayName("Deve dar erro na base de dados ao salvar cliente")
	public void mustReturnDataBaseErrorOnClientSave() {
		Client client = returnNewClient();
		
		Mockito.when(services.insert(client)).thenThrow(DataBaseException.class);
		
		Throwable t = Assertions.catchThrowable(() -> services.insert(client));
		assertThat(t).isInstanceOf(DataBaseException.class);
	}
	
	@Test
	@DisplayName("Deve retornar um cliente existente pelo ID")
	public void mustReturnSavedClientById() {
		Client client = returnNewClient();
		Integer id = 1;
		client.setClientId(id);
		
		Mockito.when(repo.findById(id)).thenReturn(Optional.of(client));
		
		Client clientFound = services.findById(id);
		
		assertThat(clientFound).isNotNull();
		assertThat(clientFound.getClientId()).isEqualTo(id);
		assertThat(clientFound.getCpf()).isEqualTo(client.getCpf());
		assertThat(clientFound.getName()).isEqualTo(client.getName());
	}
	
	@Test
	@DisplayName("Deve retornar ObjectNotFoundException ao tentar buscar cliente inexistente por ID")
	public void mustReturnErrorOnSearchByInexistentClient() {
		Integer id = 1;
		
		Mockito.when(repo.findById(id)).thenReturn(Optional.empty());
		
		Throwable t = Assertions.catchThrowable(() -> services.findById(id));
		assertThat(t).isInstanceOf(ObjectNotFoundException.class);
	}
	
	@Test
	@DisplayName("Deve dar erro na base de dados ao buscar cliente")
	public void mustReturnDataBaseErrorWhenSearchingForClient() {
		Integer id = 1;
		Client client = returnNewClient();
		client.setClientId(id);
		
		Mockito.when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(client));
		Mockito.when(services.findById(Mockito.anyInt())).thenThrow(DataBaseException.class);
				
		Throwable t = Assertions.catchThrowable(() -> services.findById(id));
		assertThat(t).isInstanceOf(DataBaseException.class);
	}

	private Client returnNewClient() {
		return new Client (1, "12345678910", "Caio Jorge");
	}
	
}
