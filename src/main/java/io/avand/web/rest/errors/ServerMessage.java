package io.avand.web.rest.errors;

import java.io.Serializable;

public class ServerMessage implements Serializable{

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
            "message='" + message + '\'' +
            '}';
    }
}
