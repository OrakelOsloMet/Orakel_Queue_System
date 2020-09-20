package com.fredrikpedersen.orakelqueuesystem.serviceLayer;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.DomainEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.DTO;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 22:03
 */
public interface CrudService<T extends DTO, S extends DomainEntity> {

    List<T> findAll();
    T createNew(final T dtoObject);
    void delete(T dtoObject);

    T saveAndReturnDTO(final S domainObject);
}
