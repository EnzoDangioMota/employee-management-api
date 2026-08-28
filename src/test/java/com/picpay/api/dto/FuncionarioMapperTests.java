package com.picpay.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.picpay.api.model.Funcionario;
import com.picpay.api.model.StatusFuncionario;

class FuncionarioMapperTests {

	private FuncionarioMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = new FuncionarioMapper();
	}

	@Test
	void deveConverterModelParaDTO() {
		Funcionario funcionario = criarFuncionario();

		FuncionarioDTO dto = mapper.paraDTO(funcionario);

		assertEquals(funcionario.getId(), dto.id());
		assertEquals(funcionario.getNome(), dto.nome());
		assertEquals(funcionario.getEmail(), dto.email());
		assertEquals(funcionario.getTelefone(), dto.telefone());
		assertEquals(funcionario.getCargo(), dto.cargo());
		assertEquals(funcionario.getDepartamento(), dto.departamento());
		assertEquals(funcionario.getSalario(), dto.salario());
		assertEquals(funcionario.getCidade(), dto.cidade());
		assertEquals(funcionario.getStatus(), dto.status());
	}

	@Test
	void deveConverterDTOParaModel() {
		Funcionario funcionario = criarFuncionario();
		FuncionarioDTO dto = mapper.paraDTO(funcionario);

		Funcionario convertido = mapper.paraModel(dto);

		assertEquals(dto.id(), convertido.getId());
		assertEquals(dto.nome(), convertido.getNome());
		assertEquals(dto.email(), convertido.getEmail());
		assertEquals(dto.telefone(), convertido.getTelefone());
		assertEquals(dto.cargo(), convertido.getCargo());
		assertEquals(dto.departamento(), convertido.getDepartamento());
		assertEquals(dto.salario(), convertido.getSalario());
		assertEquals(dto.cidade(), convertido.getCidade());
		assertEquals(dto.status(), convertido.getStatus());
	}

	@Test
	void naoDeveConverterValoresNulos() {
		assertThrows(NullPointerException.class, () -> mapper.paraDTO(null));
		assertThrows(NullPointerException.class, () -> mapper.paraModel(null));
	}

	private Funcionario criarFuncionario() {
		return new Funcionario(
				1L,
				"Ana Souza",
				"ana.souza@email.com",
				"(11) 99999-9999",
				"Desenvolvedora Java",
				"Tecnologia",
				new BigDecimal("7500.00"),
				"São Paulo",
				StatusFuncionario.EM_ANALISE);
	}
}
