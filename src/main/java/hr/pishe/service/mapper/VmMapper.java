package hr.pishe.service.mapper;

public interface VmMapper<D,V> {

    public V dtoToVm(D dto);

    public D vmToDto(V vm);

}
