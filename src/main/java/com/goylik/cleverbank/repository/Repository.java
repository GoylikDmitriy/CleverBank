package com.goylik.cleverbank.repository;

import com.goylik.cleverbank.entity.BaseEntity;
import com.goylik.cleverbank.repository.exception.DalException;

import java.util.List;

public interface Repository<ID, E extends BaseEntity> {
    E save(E entity) throws DalException;
    E findById(ID id) throws DalException;
    List<E> findAll() throws DalException;
    void delete(ID id) throws DalException;
    void delete(E entity) throws DalException;
    void deleteAll() throws DalException;
    Long count() throws DalException;
}