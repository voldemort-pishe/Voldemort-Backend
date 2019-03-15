package hr.pishe.web.rest.vm;

import java.io.Serializable;

public class CommentUserVM implements Serializable{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CommentUserVM{" +
            "name='" + name + '\'' +
            '}';
    }
}
