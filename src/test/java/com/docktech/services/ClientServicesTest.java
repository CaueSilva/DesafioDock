package com.docktech.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ClientServicesTest {
/*
	private ClientServices services;
	
	@MockBean
	private ClientRepository repo;
	
	@BeforeEach
	public void setUp() {
		this.services = new ClientServices(repo);
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
	
	private Client returnNewClient() {
		return new Client (1, "12345678910", "Caio Jorge");
	}
*/	
}
