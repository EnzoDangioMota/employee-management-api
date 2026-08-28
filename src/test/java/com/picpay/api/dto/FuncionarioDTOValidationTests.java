package com.picpay.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class FuncionarioDTOValidationTests {

	private static Validator validator;

	@BeforeAll
	static void setUpValidator() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	void deveAceitarFuncionarioValido() {
		FuncionarioDTO dto = new FuncionarioDTO(
				1L,
				"Ana Souza",
				"ana.souza@email.com",
				null,
				"Desenvolvedora Java",
				null,
				new BigDecimal("7500.00"),
				null,
				null);

		assertTrue(validator.validate(dto).isEmpty());
	}

	@Test
	void deveRejeitarCamposObrigatoriosInvalidos() {
		FuncionarioDTO dto = new FuncionarioDTO(
				null,
				" ",
				"email-invalido",
				null,
				"",
				null,
				new BigDecimal("-1.00"),
				null,
				null);

		Set<ConstraintViolation<FuncionarioDTO>> violacoes = validator.validate(dto);

		assertEquals(5, violacoes.size());
		assertTrue(violacoes.stream()
				.anyMatch(violacao -> violacao.getMessage().equals("E-mail deve possuir um formato válido")));
		assertTrue(violacoes.stream()
				.anyMatch(violacao -> violacao.getMessage().equals("Salário deve ser maior ou igual a zero")));
	}
}
