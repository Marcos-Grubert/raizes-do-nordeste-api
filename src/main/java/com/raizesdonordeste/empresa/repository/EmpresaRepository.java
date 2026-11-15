package com.raizesdonordeste.empresa.repository;

import com.raizesdonordeste.empresa.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
