package com.picpay.api.controller;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.picpay.api.dto.FuncionarioDTO;
import com.picpay.api.dto.FuncionarioMapper;
import com.picpay.api.model.Funcionario;
import com.picpay.api.model.StatusFuncionario;
import com.picpay.api.repository.FuncionarioRepository;

import jakarta.validation.Valid;

@Controller
public class HomeController {
	private final FuncionarioRepository repository;
	private final FuncionarioMapper mapper;

	public HomeController(FuncionarioRepository repository, FuncionarioMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@GetMapping("/")
	public String dashboard(@RequestParam(defaultValue = "") String busca,
			@RequestParam(required = false) StatusFuncionario status,
			@RequestParam(required = false) String resultado,
			@RequestParam(required = false) String acao, Model model) {
		List<Funcionario> todos = repository.buscarTodos();
		String termo = busca.trim().toLowerCase();
		List<Funcionario> filtrados = todos.stream()
				.filter(f -> termo.isBlank() || contem(f.getNome(), termo) || contem(f.getEmail(), termo)
						|| contem(f.getCargo(), termo) || contem(f.getDepartamento(), termo))
				.filter(f -> status == null || f.getStatus() == status)
				.toList();
		prepararModel(model, todos);
		model.addAttribute("funcionarios", filtrados);
		model.addAttribute("funcionarioDTO", new FuncionarioDTO());
		model.addAttribute("busca", busca);
		model.addAttribute("statusSelecionado", status);
		model.addAttribute("abrirModal", "cadastrar".equals(acao));
		if ("cadastrado".equals(resultado)) {
			model.addAttribute("sucesso", "Funcionário cadastrado com sucesso.");
		} else if ("removido".equals(resultado)) {
			model.addAttribute("sucesso", "Funcionário removido com sucesso.");
		} else if ("nao-encontrado".equals(resultado)) {
			model.addAttribute("erro", "Funcionário não encontrado.");
		}
		return "index";
	}

	@GetMapping("/indicadores")
	public String indicadores(Model model) {
		List<Funcionario> todos = repository.buscarTodos();
		prepararModel(model, todos);

		Map<String, Long> porDepartamento = new LinkedHashMap<>();
		todos.stream()
				.map(Funcionario::getDepartamento)
				.filter(departamento -> departamento != null && !departamento.isBlank())
				.forEach(departamento -> porDepartamento.merge(departamento, 1L, Long::sum));

		BigDecimal somaSalarios = todos.stream()
				.map(Funcionario::getSalario)
				.filter(salario -> salario != null)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		long salariosInformados = todos.stream().filter(f -> f.getSalario() != null).count();

		model.addAttribute("reprovados", contar(todos, StatusFuncionario.REPROVADO));
		model.addAttribute("percentualAnalise", percentual(todos, StatusFuncionario.EM_ANALISE));
		model.addAttribute("percentualAprovados", percentual(todos, StatusFuncionario.APROVADO));
		model.addAttribute("percentualReprovados", percentual(todos, StatusFuncionario.REPROVADO));
		model.addAttribute("percentualContratados", percentual(todos, StatusFuncionario.CONTRATADO));
		model.addAttribute("taxaContratacao", percentual(todos, StatusFuncionario.CONTRATADO));
		model.addAttribute("totalDepartamentos", porDepartamento.size());
		model.addAttribute("departamentos", porDepartamento);
		model.addAttribute("salarioMedio", salariosInformados == 0 ? BigDecimal.ZERO
				: somaSalarios.divide(BigDecimal.valueOf(salariosInformados), 2, java.math.RoundingMode.HALF_UP));
		return "indicadores";
	}

	@PostMapping("/funcionarios")
	public String cadastrar(@Valid @ModelAttribute FuncionarioDTO funcionarioDTO, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			try {
				repository.salvar(mapper.paraModel(funcionarioDTO));
				return "redirect:/?resultado=cadastrado";
			} catch (IllegalArgumentException exception) {
				result.rejectValue("id", "id.duplicado", exception.getMessage());
			}
		}
		List<Funcionario> todos = repository.buscarTodos();
		prepararModel(model, todos);
		model.addAttribute("funcionarios", todos);
		model.addAttribute("abrirModal", true);
		return "index";
	}

	@PostMapping("/funcionarios/remover")
	public String remover(@RequestParam Long id) {
		boolean removido = repository.removerPorId(id);
		return removido ? "redirect:/?resultado=removido" : "redirect:/?resultado=nao-encontrado";
	}

	private void prepararModel(Model model, List<Funcionario> todos) {
		model.addAttribute("statusDisponiveis", StatusFuncionario.values());
		model.addAttribute("totalCandidatos", todos.size());
		model.addAttribute("emAnalise", contar(todos, StatusFuncionario.EM_ANALISE));
		model.addAttribute("aprovados", contar(todos, StatusFuncionario.APROVADO));
		model.addAttribute("contratados", contar(todos, StatusFuncionario.CONTRATADO));
	}

	private long contar(List<Funcionario> funcionarios, StatusFuncionario status) {
		return funcionarios.stream().filter(f -> f.getStatus() == status).count();
	}

	private long percentual(List<Funcionario> funcionarios, StatusFuncionario status) {
		return funcionarios.isEmpty() ? 0 : Math.round(contar(funcionarios, status) * 100.0 / funcionarios.size());
	}

	private boolean contem(String valor, String termo) {
		return valor != null && valor.toLowerCase().contains(termo);
	}
}
