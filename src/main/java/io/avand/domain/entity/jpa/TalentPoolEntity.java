package io.avand.domain.entity.jpa;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.avand.domain.enumeration.CandidateState;

/**
 * A TalentPoolEntity.
 */
@Entity
@Table(name = "talent_pool_entity")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class TalentPoolEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "file_id")
    private Long fileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private CandidateState state;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "email")
    private String email;

    @ManyToOne
    private UserEntity user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public TalentPoolEntity firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public TalentPoolEntity lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getFileId() {
        return fileId;
    }

    public TalentPoolEntity fileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public CandidateState getState() {
        return state;
    }

    public TalentPoolEntity state(CandidateState state) {
        this.state = state;
        return this;
    }

    public void setState(CandidateState state) {
        this.state = state;
    }

    public String getCellphone() {
        return cellphone;
    }

    public TalentPoolEntity cellphone(String cellphone) {
        this.cellphone = cellphone;
        return this;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public TalentPoolEntity email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity getUser() {
        return user;
    }

    public TalentPoolEntity user(UserEntity userEntity) {
        this.user = userEntity;
        return this;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TalentPoolEntity talentPoolEntity = (TalentPoolEntity) o;
        if (talentPoolEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), talentPoolEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TalentPoolEntity{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fileId=" + getFileId() +
            ", state='" + getState() + "'" +
            ", cellphone='" + getCellphone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
