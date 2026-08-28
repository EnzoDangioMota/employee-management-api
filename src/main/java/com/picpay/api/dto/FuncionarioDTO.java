package com.picpay.api.dto;

import java.math.BigDecimal;

import com.picpay.api.model.StatusFuncionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {

	@NotNull(message = "ID é obrigatório")
	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotBlank(message = "E-mail é obrigatório")
	@Email(message = "E-mail deve possuir um formato válido")
	private String email;

	private String telefone;

	@NotBlank(message = "Cargo é obrigatório")
	private String cargo;

	private String departamento;

	@PositiveOrZero(message = "Salário deve ser maior ou igual a zero")
	private BigDecimal salario;

	private String cidade;

	private StatusFuncionario status;
}
