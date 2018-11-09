package io.avand.service.dto;

import java.io.Serializable;

public class SmsSendRequestDTO implements Serializable {

    private String receptor;
    private String token;
    private String template;

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "SmsSendRequestDTO{" +
            "receptor='" + receptor + '\'' +
            ", token='" + token + '\'' +
            ", template='" + template + '\'' +
            '}';
    }
}
