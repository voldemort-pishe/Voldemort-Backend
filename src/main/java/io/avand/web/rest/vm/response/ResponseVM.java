package io.avand.web.rest.vm.response;

import java.io.Serializable;
import java.util.Map;

public class ResponseVM<T> implements Serializable {
    private T data;
    private Map<String, Object> include;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getInclude() {
        return include;
    }

    public void setInclude(Map<String, Object> include) {
        this.include = include;
    }

    @Override
    public String toString() {
        return "ResponseVM{" +
            "data=" + data +
            ", include=" + include +
            '}';
    }
}
