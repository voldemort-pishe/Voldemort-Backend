package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class FileDTO implements Serializable {

    @NotNull
    private Long id;

    private String filename;

    private String fileType;

    @JsonIgnore
    private CandidateDTO candidate;

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

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
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
