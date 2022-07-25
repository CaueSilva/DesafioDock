package com.docktech.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.docktech.domain.Client;
import com.docktech.dto.ClientDTO;
import com.docktech.repository.ClientRepository;
import com.docktech.resources.exception.FieldMessage;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientDTO> {
	
	@Autowired
	private ClientRepository repo;
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
				
		Client aux = repo.findByCpf(objDto.getCpf());
		if(aux != null) {
			list.add(new FieldMessage("cpf","CPF já existente."));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}