package com.fredrikpedersen.orakelqueuesystem.serviceLayer;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.DomainEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.DTO;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

public interface CrudService<T extends DTO, S extends DomainEntity, ID extends Long> {

    List<T> findAll();
    T createNew(T dtoObject);
    T edit(T dtoObject, ID id);
    void deleteById(ID id);

    T saveAndReturnDTO(final S domainObject);
}
