package com.doubao.douding.system.dto.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Johnson
 * @Description generic mapper
 */

public interface GenericMapper<D, E> {

    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntityList(List<D> dtoList);

    List<D> toDTOList(List<E> entityList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeEntityToDTO(E entity, @MappingTarget D dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeDTOToEntity(D dto, @MappingTarget E entity);
}
