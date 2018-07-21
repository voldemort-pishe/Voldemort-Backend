package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.MessageOwnerType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "candidate_message_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandidateMessageEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message")
    private String message;

    @Column(name = "from_id")
    private Long fromId;

    @Column(name = "to_id")
    private Long toId;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner")
    private MessageOwnerType owner;

    @ManyToOne
    private CandidateMessageEntity parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CandidateMessageEntity> child = new HashSet<>();

    @ManyToOne
    private CandidateEntity candidate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public MessageOwnerType getOwner() {
        return owner;
    }

    public void setOwner(MessageOwnerType owner) {
        this.owner = owner;
    }

    public CandidateMessageEntity getParent() {
        return parent;
    }

    public void setParent(CandidateMessageEntity parent) {
        this.parent = parent;
    }

    public Set<CandidateMessageEntity> getChild() {
        return child;
    }

    public void setChild(Set<CandidateMessageEntity> child) {
        this.child = child;
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return "CandidateMessageEntity{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", message='" + message + '\'' +
            ", fromId=" + fromId +
            ", toId=" + toId +
            ", owner=" + owner +
            ", parent=" + parent +
            ", child=" + child +
            '}';
    }
}
