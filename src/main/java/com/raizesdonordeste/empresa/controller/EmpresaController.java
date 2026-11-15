package com.raizesdonordeste.empresa.controller;

import com.raizesdonordeste.empresa.dto.EmpresaRequest;
import com.raizesdonordeste.empresa.dto.EmpresaResponse;
import com.raizesdonordeste.empresa.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/api/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }
    @PostMapping
    public ResponseEntity<EmpresaResponse> criar(@RequestBody @Valid EmpresaRequest empresaRequest){
        EmpresaResponse empresaResponse= empresaService.criar(empresaRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(empresaResponse.id())
                .toUri();
        return ResponseEntity.created(uri).body(empresaResponse);
    }

    @GetMapping
    public ResponseEntity<Page<EmpresaResponse>> listar(@PageableDefault(size = 10, sort = "id") Pageable pageable){
        Page<EmpresaResponse> page = empresaService.buscarTodas(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> buscar(@PathVariable Long id){
        EmpresaResponse empresaResponse = empresaService.buscarPorId(id);
        return ResponseEntity.ok(empresaResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid EmpresaRequest empresaRequest){
        EmpresaResponse empresaResponse = empresaService.atualizar(id, empresaRequest);
        return ResponseEntity.ok(empresaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        empresaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
