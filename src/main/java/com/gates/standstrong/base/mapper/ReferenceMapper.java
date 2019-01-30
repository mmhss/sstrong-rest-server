package com.gates.standstrong.base.mapper;


import com.gates.standstrong.base.BaseDto;
import com.gates.standstrong.base.BaseEntity;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    public <T extends BaseEntity> T resolve(BaseDto reference, @TargetType Class<T> entityClass) {
        return reference != null ? entityManager.find( entityClass, reference.getId() ) : null;
    }

    public BaseDto toReference(BaseEntity entity) {
        BaseDto dto = new BaseDto() {};
        dto.setId(entity != null ? entity.getId() : null);
        return dto;
    }
}
