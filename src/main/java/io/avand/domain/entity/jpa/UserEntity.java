package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserEntity.
 */
@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class UserEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "activation_key")
    private String activationKey;

    @Column(name = "invitation_key")
    private String invitationKey;

    @Column(name = "reset_key")
    private String resetKey;

    @Column(name = "reset_date")
    private ZonedDateTime resetDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<UserAuthorityEntity> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<CompanyEntity> companies = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<EventEntity> events = new HashSet<>();

    @OneToOne
    @JoinColumn
    private FileEntity file;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public UserEntity login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserEntity passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Boolean isActivated() {
        return activated;
    }

    public UserEntity activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public UserEntity activationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getInvitationKey() {
        return invitationKey;
    }

    public void setInvitationKey(String invitationKey) {
        this.invitationKey = invitationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public UserEntity resetKey(String resetKey) {
        this.resetKey = resetKey;
        return this;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    public UserEntity resetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
        return this;
    }

    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    public Set<UserAuthorityEntity> getUserAuthorities() {
        return authorities;
    }

    public UserEntity userAuthorities(Set<UserAuthorityEntity> userAuthorityEntities) {
        this.authorities = userAuthorityEntities;
        return this;
    }

    public UserEntity addUserAuthority(UserAuthorityEntity userAuthorityEntity) {
        this.authorities.add(userAuthorityEntity);
        userAuthorityEntity.setUser(this);
        return this;
    }

    public UserEntity removeUserAuthority(UserAuthorityEntity userAuthorityEntity) {
        this.authorities.remove(userAuthorityEntity);
        userAuthorityEntity.setUser(null);
        return this;
    }

    public void setUserAuthorities(Set<UserAuthorityEntity> userAuthorityEntities) {
        this.authorities = userAuthorityEntities;
    }

    public Set<CompanyEntity> getCompanies() {
        return companies;
    }

    public UserEntity companies(Set<CompanyEntity> companyEntities) {
        this.companies = companyEntities;
        return this;
    }

    public UserEntity addCompany(CompanyEntity companyEntity) {
        this.companies.add(companyEntity);
        companyEntity.setUser(this);
        return this;
    }

    public UserEntity removeCompany(CompanyEntity companyEntity) {
        this.companies.remove(companyEntity);
        companyEntity.setUser(null);
        return this;
    }

    public void setCompanies(Set<CompanyEntity> companyEntities) {
        this.companies = companyEntities;
    }

    public Set<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(Set<EventEntity> events) {
        this.events = events;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
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
        UserEntity userEntity = (UserEntity) o;
        if (userEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserEntity{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", passwordHash='" + passwordHash + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", cellphone='" + cellphone + '\'' +
            ", activated=" + activated +
            ", activationKey='" + activationKey + '\'' +
            ", invitationKey='" + invitationKey + '\'' +
            ", resetKey='" + resetKey + '\'' +
            ", resetDate=" + resetDate +
            ", file=" + file +
            '}';
    }
}
