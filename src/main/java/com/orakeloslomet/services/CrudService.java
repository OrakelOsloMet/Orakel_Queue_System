package com.orakeloslomet.services;

import com.orakeloslomet.persistance.models.DomainEntity;
import com.orakeloslomet.dtos.DTO;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

public interface CrudService<T extends DTO, S extends DomainEntity, ID extends Long> {

    List<T> findAll();
    T findById(ID id);
    T createNew(T dtoObject);
    T edit(T dtoObject, ID id);
    void deleteById(ID id);

    T saveAndReturnDTO(final S domainObject);
}
