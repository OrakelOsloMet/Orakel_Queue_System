package com.orakeloslomet.services;

import com.orakeloslomet.dtos.DTO;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 08/07/2022 at 19:38
 */

public interface CrudService<T extends DTO> {

    List<T> findAll();
    T findById(Long id);
    T save(T dtoObject);
    T update(T dtoObject, Long id);
    void deleteById(Long id);
}
