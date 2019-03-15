package hr.pishe.service.mapper;

import java.util.List;
import java.util.Set;

public interface EntityMapper<D, E> {

    public E toEntity(D dto);

    public D toDto(E entity);

    public List<E> toEntity(List<D> dtoList);

    public List<D> toDto(List<E> entityList);

    public Set<E> toEntity(Set<D> dtoList);

    public Set<D> toDto(Set<E> entityList);
}
