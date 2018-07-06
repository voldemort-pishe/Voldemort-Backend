package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class FileDTO implements Serializable {

    @NotNull
    private Long id;

    private String filename;

    private String fileType;

    @NotNull
    private Long candidateId;

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

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
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
