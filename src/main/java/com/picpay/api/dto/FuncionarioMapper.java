package com.picpay.api.dto;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.picpay.api.model.Funcionario;

@Component
public class FuncionarioMapper {

	public FuncionarioDTO paraDTO(Funcionario funcionario) {
		Objects.requireNonNull(funcionario, "O funcionário não pode ser nulo");

		return new FuncionarioDTO(
				funcionario.getId(),
				funcionario.getNome(),
				funcionario.getEmail(),
				funcionario.getTelefone(),
				funcionario.getCargo(),
				funcionario.getDepartamento(),
				funcionario.getSalario(),
				funcionario.getCidade(),
				funcionario.getStatus());
	}

	public Funcionario paraModel(FuncionarioDTO dto) {
		Objects.requireNonNull(dto, "O DTO do funcionário não pode ser nulo");

		return new Funcionario(
				dto.getId(),
				dto.getNome(),
				dto.getEmail(),
				dto.getTelefone(),
				dto.getCargo(),
				dto.getDepartamento(),
				dto.getSalario(),
				dto.getCidade(),
				dto.getStatus());
	}
}
