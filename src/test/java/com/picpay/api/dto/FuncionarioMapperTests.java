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

		assertEquals(funcionario.getId(), dto.getId());
		assertEquals(funcionario.getNome(), dto.getNome());
		assertEquals(funcionario.getEmail(), dto.getEmail());
		assertEquals(funcionario.getTelefone(), dto.getTelefone());
		assertEquals(funcionario.getCargo(), dto.getCargo());
		assertEquals(funcionario.getDepartamento(), dto.getDepartamento());
		assertEquals(funcionario.getSalario(), dto.getSalario());
		assertEquals(funcionario.getCidade(), dto.getCidade());
		assertEquals(funcionario.getStatus(), dto.getStatus());
	}

	@Test
	void deveConverterDTOParaModel() {
		Funcionario funcionario = criarFuncionario();
		FuncionarioDTO dto = mapper.paraDTO(funcionario);

		Funcionario convertido = mapper.paraModel(dto);

		assertEquals(dto.getId(), convertido.getId());
		assertEquals(dto.getNome(), convertido.getNome());
		assertEquals(dto.getEmail(), convertido.getEmail());
		assertEquals(dto.getTelefone(), convertido.getTelefone());
		assertEquals(dto.getCargo(), convertido.getCargo());
		assertEquals(dto.getDepartamento(), convertido.getDepartamento());
		assertEquals(dto.getSalario(), convertido.getSalario());
		assertEquals(dto.getCidade(), convertido.getCidade());
		assertEquals(dto.getStatus(), convertido.getStatus());
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
