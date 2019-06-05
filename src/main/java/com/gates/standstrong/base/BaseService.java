package com.gates.standstrong.base;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {

    T save(T t);

    T update(T t);

    List<T> findAll();

    Iterable<T> findAll(BooleanExpression exp, OrderSpecifier... orderBy);

    Page<T> findAll(Predicate var1, Pageable var2);

    Optional<T> findOne(Long id);

    void delete(T t);

    void delete(Long id);

}
