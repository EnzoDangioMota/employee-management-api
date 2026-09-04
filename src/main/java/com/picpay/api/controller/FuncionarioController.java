package com.picpay.api.controller;

import com.picpay.api.dto.FuncionarioDTO;
import com.picpay.api.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public FuncionarioDTO save(@Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        return funcionarioService.save(funcionarioDTO);
    }

    @GetMapping
    public List<FuncionarioDTO> findAll() {
        return funcionarioService.findAll();
    }

    @GetMapping("/{id}")
    public FuncionarioDTO findById(@PathVariable Long id) {
        return funcionarioService.findById(id);
    }

    @PutMapping("/{id}")
    public FuncionarioDTO update(@PathVariable Long id, @Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        return funcionarioService.update(id, funcionarioDTO);
    }

    @PatchMapping("/{id}")
    public FuncionarioDTO partialUpdate(@PathVariable Long id, @RequestBody FuncionarioDTO funcionarioDTO) {
        return funcionarioService.partialUpdate(id, funcionarioDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        funcionarioService.delete(id);
    }
}
