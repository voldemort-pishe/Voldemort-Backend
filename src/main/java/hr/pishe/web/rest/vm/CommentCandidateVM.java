package hr.pishe.web.rest.vm;

import java.io.Serializable;

public class CommentCandidateVM implements Serializable {

    private String name;
    private String filePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "CommentCandidateVM{" +
            "name='" + name + '\'' +
            ", filePath='" + filePath + '\'' +
            '}';
    }
}
