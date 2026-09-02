package com.picpay.api.service;

import com.picpay.api.dto.FuncionarioDTO;
import com.picpay.api.exception.ResourceNotFoundException;
import com.picpay.api.model.Funcionario;
import com.picpay.api.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<FuncionarioDTO> findAll() {
        return funcionarioRepository.buscarTodos().stream()
                .map(this::toDTO)
                .toList();
    }

    public FuncionarioDTO findById(Long id) {
        Funcionario funcionario = funcionarioRepository.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado: " + id));
        return toDTO(funcionario);
    }

    public FuncionarioDTO save(FuncionarioDTO dto) {
        Funcionario funcionario = toEntity(dto);
        return toDTO(funcionarioRepository.salvar(funcionario));
    }

    private FuncionarioDTO toDTO(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getTelefone(),
                funcionario.getCargo(),
                funcionario.getDepartamento(),
                funcionario.getSalario(),
                funcionario.getCidade(),
                funcionario.getStatus()
        );
    }

    private Funcionario toEntity(FuncionarioDTO dto) {
        return new Funcionario(
                null,
                dto.getNome(),
                dto.getEmail(),
                dto.getTelefone(),
                dto.getCargo(),
                dto.getDepartamento(),
                dto.getSalario(),
                dto.getCidade(),
                dto.getStatus()
        );
    }

}
