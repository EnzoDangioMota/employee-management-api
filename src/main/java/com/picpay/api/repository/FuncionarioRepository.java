package com.picpay.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.picpay.api.model.Funcionario;

@Repository
public class FuncionarioRepository {

	private final List<Funcionario> funcionarios = new ArrayList<>();

	public synchronized Funcionario salvar(Funcionario funcionario) {
		Objects.requireNonNull(funcionario, "O funcionário não pode ser nulo");
		Objects.requireNonNull(funcionario.getId(), "O ID do funcionário não pode ser nulo");

		if (existePorId(funcionario.getId())) {
			throw new IllegalArgumentException("Já existe um funcionário com o ID informado");
		}

		funcionarios.add(funcionario);
		return funcionario;
	}

	public synchronized Funcionario atualizar(Funcionario funcionario) {
		Objects.requireNonNull(funcionario, "O funcionario nao pode ser nulo");
		Objects.requireNonNull(funcionario.getId(), "O ID do funcionario nao pode ser nulo");

		for (int indice = 0; indice < funcionarios.size(); indice++) {
			if (Objects.equals(funcionarios.get(indice).getId(), funcionario.getId())) {
				funcionarios.set(indice, funcionario);
				return funcionario;
			}
		}

		throw new IllegalArgumentException("Funcionario nao encontrado para atualizacao");
	}

	public synchronized List<Funcionario> buscarTodos() {
		return List.copyOf(funcionarios);
	}

	public synchronized Optional<Funcionario> buscarPorId(Long id) {
		return funcionarios.stream()
				.filter(funcionario -> Objects.equals(funcionario.getId(), id))
				.findFirst();
	}

	public synchronized boolean existePorId(Long id) {
		return funcionarios.stream()
				.anyMatch(funcionario -> Objects.equals(funcionario.getId(), id));
	}

	public synchronized boolean removerPorId(Long id) {
		return funcionarios.removeIf(funcionario -> Objects.equals(funcionario.getId(), id));
	}
}
