package io.avand.domain.entity.jpa;

import io.avand.domain.enumeration.SocialType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "candidate_social")
public class CandidateSocialEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CandidateEntity candidate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SocialType type;

    @Column(name = "url")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
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
        return "CandidateSocialEntity{" +
            "id=" + id +
            ", type=" + type +
            ", url='" + url + '\'' +
            '}';
    }
}
