package io.avand.service.dto;

import java.io.Serializable;

public class CloudflareRequestDTO implements Serializable {
    private String type;
    private String name;
    private String content;
    private Boolean proxied;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getProxied() {
        return proxied;
    }

    public void setProxied(Boolean proxied) {
        this.proxied = proxied;
    }

    @Override
    public String toString() {
        return "CloudflareRequestDTO{" +
            "type='" + type + '\'' +
            ", name='" + name + '\'' +
            ", content='" + content + '\'' +
            ", proxied=" + proxied +
            '}';
    }
}
