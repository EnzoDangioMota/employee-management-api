package com.picpay.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.picpay.api.model.Funcionario;
import com.picpay.api.model.StatusFuncionario;

class FuncionarioRepositoryTests {

	private FuncionarioRepository repository;

	@BeforeEach
	void setUp() {
		repository = new FuncionarioRepository();
	}

	@Test
	void deveSalvarEListarFuncionario() {
		Funcionario funcionario = criarFuncionario(1L);

		Funcionario salvo = repository.salvar(funcionario);

		assertSame(funcionario, salvo);
		assertEquals(1, repository.buscarTodos().size());
		assertSame(funcionario, repository.buscarTodos().getFirst());
	}

	@Test
	void deveBuscarFuncionarioPorId() {
		Funcionario funcionario = criarFuncionario(1L);
		repository.salvar(funcionario);

		assertSame(funcionario, repository.buscarPorId(1L).orElseThrow());
		assertTrue(repository.buscarPorId(99L).isEmpty());
	}

	@Test
	void naoDeveSalvarIdsDuplicados() {
		repository.salvar(criarFuncionario(1L));

		assertThrows(IllegalArgumentException.class,
				() -> repository.salvar(criarFuncionario(1L)));
	}

	@Test
	void deveRemoverFuncionarioPorId() {
		repository.salvar(criarFuncionario(1L));

		assertTrue(repository.removerPorId(1L));
		assertFalse(repository.existePorId(1L));
		assertFalse(repository.removerPorId(99L));
	}

	private Funcionario criarFuncionario(Long id) {
		return new Funcionario(
				id,
				"Ana Souza",
				"ana.souza@email.com",
				"(11) 99999-9999",
				"Desenvolvedora Java",
				"Tecnologia",
				new BigDecimal("7500.00"),
				"Sao Paulo",
				StatusFuncionario.EM_ANALISE);
	}
}
