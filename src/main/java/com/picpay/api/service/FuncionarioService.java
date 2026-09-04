package com.picpay.api.service;

import com.picpay.api.dto.FuncionarioDTO;
import com.picpay.api.exception.ResourceNotFoundException;
import com.picpay.api.model.Funcionario;
import com.picpay.api.repository.FuncionarioRepository;
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

    // PUT - Atualização completa
    public FuncionarioDTO update(Long id, FuncionarioDTO dto) {

        funcionarioRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado: " + id));

        Funcionario funcionario = new Funcionario(
                id,
                dto.getNome(),
                dto.getEmail(),
                dto.getTelefone(),
                dto.getCargo(),
                dto.getDepartamento(),
                dto.getSalario(),
                dto.getCidade(),
                dto.getStatus()
        );

        return toDTO(funcionarioRepository.atualizar(funcionario));
    }


    // PATCH - Atualização parcial
    public FuncionarioDTO partialUpdate(Long id, FuncionarioDTO dto) {

        Funcionario funcionario = funcionarioRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado: " + id));

        if (dto.getNome() != null) {
            funcionario.setNome(dto.getNome());
        }

        if (dto.getEmail() != null) {
            funcionario.setEmail(dto.getEmail());
        }

        if (dto.getTelefone() != null) {
            funcionario.setTelefone(dto.getTelefone());
        }

        if (dto.getCargo() != null) {
            funcionario.setCargo(dto.getCargo());
        }

        if (dto.getDepartamento() != null) {
            funcionario.setDepartamento(dto.getDepartamento());
        }

        if (dto.getSalario() != null) {
            funcionario.setSalario(dto.getSalario());
        }

        if (dto.getCidade() != null) {
            funcionario.setCidade(dto.getCidade());
        }

        if (dto.getStatus() != null) {
            funcionario.setStatus(dto.getStatus());
        }

        return toDTO(funcionarioRepository.atualizar(funcionario));
    }


    // DELETE - Excluir funcionário
    public void delete(Long id) {

        funcionarioRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado: " + id));

        funcionarioRepository.removerPorId(id);
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
                dto.getId(),
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
