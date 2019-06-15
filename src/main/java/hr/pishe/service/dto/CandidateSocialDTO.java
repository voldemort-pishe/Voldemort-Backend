package hr.pishe.service.dto;

import hr.pishe.domain.enumeration.SocialType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CandidateSocialDTO implements Serializable {
    private Long id;
    @NotNull
    private Long candidateId;
    private SocialType type;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public SocialType getType() {
        return type;
    }

    public void setType(SocialType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CandidateSocialDTO{" +
            "id=" + id +
            ", candidateId=" + candidateId +
            ", type=" + type +
            ", url='" + url + '\'' +
            '}';
    }
}
