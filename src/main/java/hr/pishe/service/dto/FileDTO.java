package hr.pishe.service.dto;

import java.io.Serializable;

public class FileDTO implements Serializable {

    private Long id;

    private String filename;

    private String fileType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
            "id=" + id +
            ", filename='" + filename + '\'' +
            ", fileType='" + fileType + '\'' +
            '}';
    }
}
