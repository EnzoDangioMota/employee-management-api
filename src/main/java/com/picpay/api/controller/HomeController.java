package com.picpay.api.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.picpay.api.dto.FuncionarioDTO;
import com.picpay.api.model.Funcionario;
import com.picpay.api.model.StatusFuncionario;
import com.picpay.api.repository.FuncionarioRepository;
import com.picpay.api.service.FuncionarioService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    private final FuncionarioRepository repository;
    private final FuncionarioService service;

    public HomeController(FuncionarioRepository repository, FuncionarioService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public String dashboard(@RequestParam(required = false) String acao, Model model) {
        List<Funcionario> funcionarios = repository.buscarTodos();
        prepareModel(model, funcionarios);
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("funcionarioDTO", new FuncionarioDTO());
        model.addAttribute("busca", "");
        model.addAttribute("abrirModal", "cadastrar".equals(acao));
        return "index";
    }

    @GetMapping("/painel/funcionarios")
    public String funcionarios(@RequestParam(defaultValue = "") String busca,
            @RequestParam(required = false) StatusFuncionario status, Model model) {
        String termo = busca.trim().toLowerCase();
        List<Funcionario> filtrados = repository.buscarTodos().stream()
                .filter(f -> termo.isBlank() || contains(f.getNome(), termo) || contains(f.getEmail(), termo)
                        || contains(f.getCargo(), termo) || contains(f.getDepartamento(), termo))
                .filter(f -> status == null || f.getStatus() == status)
                .toList();
        model.addAttribute("funcionarios", filtrados);
        model.addAttribute("totalEncontrado", filtrados.size());
        model.addAttribute("statusDisponiveis", StatusFuncionario.values());
        model.addAttribute("busca", busca);
        model.addAttribute("statusSelecionado", status);
        return "funcionarios";
    }

    @GetMapping("/indicadores")
    public String indicadores(Model model) {
        List<Funcionario> funcionarios = repository.buscarTodos();
        prepareModel(model, funcionarios);
        Map<String, Long> departamentos = new LinkedHashMap<>();
        funcionarios.stream().map(Funcionario::getDepartamento).filter(d -> d != null && !d.isBlank())
                .forEach(d -> departamentos.merge(d, 1L, Long::sum));
        BigDecimal soma = funcionarios.stream().map(Funcionario::getSalario).filter(s -> s != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long informados = funcionarios.stream().filter(f -> f.getSalario() != null).count();
        model.addAttribute("reprovados", count(funcionarios, StatusFuncionario.REPROVADO));
        model.addAttribute("percentualAnalise", percentage(funcionarios, StatusFuncionario.EM_ANALISE));
        model.addAttribute("percentualAprovados", percentage(funcionarios, StatusFuncionario.APROVADO));
        model.addAttribute("percentualReprovados", percentage(funcionarios, StatusFuncionario.REPROVADO));
        model.addAttribute("percentualContratados", percentage(funcionarios, StatusFuncionario.CONTRATADO));
        model.addAttribute("taxaContratacao", percentage(funcionarios, StatusFuncionario.CONTRATADO));
        model.addAttribute("totalDepartamentos", departamentos.size());
        model.addAttribute("departamentos", departamentos);
        model.addAttribute("salarioMedio", informados == 0 ? BigDecimal.ZERO : soma.divide(BigDecimal.valueOf(informados), 2, RoundingMode.HALF_UP));
        return "indicadores";
    }

    @PostMapping("/painel/funcionarios")
    public String cadastrar(@Valid @ModelAttribute FuncionarioDTO funcionarioDTO, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                service.save(funcionarioDTO);
                return "redirect:/painel/funcionarios";
            } catch (IllegalArgumentException exception) {
                result.rejectValue("id", "id.duplicado", exception.getMessage());
            }
        }
        List<Funcionario> funcionarios = repository.buscarTodos();
        prepareModel(model, funcionarios);
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("abrirModal", true);
        return "index";
    }

    @PostMapping("/painel/funcionarios/remover")
    public String remover(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/painel/funcionarios";
    }

    @GetMapping("/painel/funcionarios/{id}/editar")
    public String telaEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("funcionarioDTO", service.findById(id));
        model.addAttribute("statusDisponiveis", StatusFuncionario.values());
        return "editar-funcionario";
    }

    @PutMapping("/painel/funcionarios/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute FuncionarioDTO funcionarioDTO) {
        service.update(id, funcionarioDTO);
        return "redirect:/painel/funcionarios";
    }

    @PatchMapping("/painel/funcionarios/{id}")
    public String atualizarParcialmente(@PathVariable Long id, @ModelAttribute FuncionarioDTO funcionarioDTO) {
        service.partialUpdate(id, funcionarioDTO);
        return "redirect:/painel/funcionarios/" + id + "/editar";
    }

    private void prepareModel(Model model, List<Funcionario> funcionarios) {
        model.addAttribute("statusDisponiveis", StatusFuncionario.values());
        model.addAttribute("totalCandidatos", funcionarios.size());
        model.addAttribute("emAnalise", count(funcionarios, StatusFuncionario.EM_ANALISE));
        model.addAttribute("aprovados", count(funcionarios, StatusFuncionario.APROVADO));
        model.addAttribute("contratados", count(funcionarios, StatusFuncionario.CONTRATADO));
    }

    private long count(List<Funcionario> funcionarios, StatusFuncionario status) {
        return funcionarios.stream().filter(f -> f.getStatus() == status).count();
    }

    private long percentage(List<Funcionario> funcionarios, StatusFuncionario status) {
        return funcionarios.isEmpty() ? 0 : Math.round(count(funcionarios, status) * 100.0 / funcionarios.size());
    }

    private boolean contains(String value, String term) {
        return value != null && value.toLowerCase().contains(term);
    }
}
