package com.picpay.api.controller;

import com.picpay.api.dto.FuncionarioDTO;
import com.picpay.api.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public FuncionarioDTO salvar(@RequestBody FuncionarioDTO funcionarioDTO) {
        return funcionarioService.save(funcionarioDTO);
    }

    @GetMapping
    public List<FuncionarioDTO> buscarTodos() {
        return funcionarioService.findAll();
    }

    @GetMapping("/{id}")
    public FuncionarioDTO buscarPorId(@PathVariable Long id) {
        return funcionarioService.findById(id);
    }
}
