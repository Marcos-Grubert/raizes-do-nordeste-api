package com.raizesdonordeste.empresa.service;

import com.raizesdonordeste.empresa.dto.EmpresaRequest;
import com.raizesdonordeste.empresa.dto.EmpresaResponse;
import com.raizesdonordeste.empresa.model.Empresa;
import com.raizesdonordeste.empresa.repository.EmpresaRepository;
import com.raizesdonordeste.shared.exception.RecursoDuplicadoException;
import com.raizesdonordeste.shared.exception.RecursoNaoEncontradoException;
import com.raizesdonordeste.shared.exception.RegraNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public EmpresaResponse criar(EmpresaRequest empresaRequest) {
        if(empresaRepository.existsByCnpj(empresaRequest.cnpj())) {
            throw new RecursoDuplicadoException("Cnpj informado já está cadastrado");
        }

        Empresa empresa = new Empresa();
        empresa.setCnpj(empresaRequest.cnpj());
        empresa.setNomeFantasia(empresaRequest.nomeFantasia());
        empresa.setRazaoSocial(empresaRequest.razaoSocial());

        Empresa salva = empresaRepository.save(empresa);
        return EmpresaResponse.from(salva);
    }

    @Transactional(readOnly = true)
    public Page<EmpresaResponse> buscarTodas(Pageable pageable){
        return empresaRepository.findAll(pageable)
                .map(EmpresaResponse::from);
    }

    @Transactional(readOnly = true)
    public EmpresaResponse buscarPorId(Long id){
        Empresa empresa = empresaRepository.findById(id).orElseThrow(()->new RecursoNaoEncontradoException("Não foi encontrado empresa com o identificador informado"));
        return EmpresaResponse.from(empresa);
    }

    @Transactional
    public EmpresaResponse atualizar(Long id, EmpresaRequest empresaRequest) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Empresa com o identificador informado não encontrado"));
        if(!empresa.getCnpj().equals(empresaRequest.cnpj())) {
            throw new RegraNegocioException("Não é permitido alterar o CNPJ da empresa");
        }

        empresa.setAtiva(empresaRequest.ativa());
        empresa.setRazaoSocial(empresaRequest.razaoSocial());
        empresa.setNomeFantasia(empresaRequest.nomeFantasia());
        empresa.setAtualizadoEm(LocalDateTime.now());
        Empresa atualizada = empresaRepository.save(empresa);
        return EmpresaResponse.from(atualizada);
    }

    @Transactional
    public void deletar(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Empresa não encontrada"));
        empresaRepository.deleteById(id);
    }
}
