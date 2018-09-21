package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.MessageOwnerType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CandidateMessageDTO extends AbstractAuditingDTO implements Serializable {

    @NotNull
    private String message;
    private String subject;
    @NotNull
    private Long fromUserId;
    @NotNull
    private Long toUserId;
    private String messageId;
    private MessageOwnerType owner;
    private Long parentId;

    @JsonIgnore
    private Set<CandidateMessageDTO> child = new HashSet<>();

    @NotNull
    private Long candidateId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public MessageOwnerType getOwner() {
        return owner;
    }

    public void setOwner(MessageOwnerType owner) {
        this.owner = owner;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<CandidateMessageDTO> getChild() {
        return child;
    }

    public void setChild(Set<CandidateMessageDTO> child) {
        this.child = child;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "CandidateMessageDTO{" +
            "message='" + message + '\'' +
            ", subject='" + subject + '\'' +
            ", fromUserId=" + fromUserId +
            ", toUserId=" + toUserId +
            ", messageId='" + messageId + '\'' +
            ", owner=" + owner +
            ", parentId=" + parentId +
            ", child=" + child +
            ", candidateId=" + candidateId +
            '}';
    }
}
