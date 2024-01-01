package com.deizon.system_barbershop.domain.services;

import java.util.List;
import java.util.UUID;

public interface ServiceCRUD<T, C> {

    List<T> findAll();
    T findByID(UUID id);
    C addResource(T t);
    void remResource(UUID id);
    C updateResource(UUID id, T t);
    void updateDataResource(C oldResource, T newResource);
}
