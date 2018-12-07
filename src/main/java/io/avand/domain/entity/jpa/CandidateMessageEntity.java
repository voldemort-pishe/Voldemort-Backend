package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.CandidateMessageOwnerType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "candidate_message")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CandidateMessageEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message")
    private String message;

    @Column(name = "owner")
    @Enumerated(EnumType.STRING)
    private CandidateMessageOwnerType owner;

    @Column(name = "from_user_id")
    private Long fromUserId;

    @Column(name = "to_user_id")
    private Long toUserId;

    @Column(name = "message_id")
    private String messageId;

    @ManyToOne
    private CandidateMessageEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
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

    public CandidateMessageOwnerType getOwner() {
        return owner;
    }

    public void setOwner(CandidateMessageOwnerType owner) {
        this.owner = owner;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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
            ", owner=" + owner +
            ", fromUserId=" + fromUserId +
            ", toUserId=" + toUserId +
            ", messageId='" + messageId + '\'' +
            ", parent=" + parent +
            ", child=" + child +
            ", candidate=" + candidate +
            '}';
    }
}
