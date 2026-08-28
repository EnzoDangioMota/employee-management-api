package com.picpay.api.dto;

import java.math.BigDecimal;

import com.picpay.api.model.StatusFuncionario;

public record FuncionarioDTO(
		Long id,
		String nome,
		String email,
		String telefone,
		String cargo,
		String departamento,
		BigDecimal salario,
		String cidade,
		StatusFuncionario status) {
}
