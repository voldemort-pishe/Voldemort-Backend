package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserDTO extends AbstractAuditingDTO implements Serializable {

    private String login;

    private String passwordHash;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean activated;

    private String activationKey;

    private String invitationKey;

    private String resetKey;

    private ZonedDateTime resetDate;

    @JsonIgnore
    private Set<UserAuthorityDTO> userAuthorities = new HashSet<>();

    @JsonIgnore
    private Set<CompanyDTO> companies = new HashSet<>();

    @JsonIgnore
    private Set<TalentPoolDTO> talentPools = new HashSet<>();

    @JsonIgnore
    private Set<InvoiceDTO> invoices = new HashSet<>();

    private Long fileId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
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

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    public Set<UserAuthorityDTO> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<UserAuthorityDTO> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    public Set<CompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<CompanyDTO> companies) {
        this.companies = companies;
    }

    public Set<TalentPoolDTO> getTalentPools() {
        return talentPools;
    }

    public void setTalentPools(Set<TalentPoolDTO> talentPools) {
        this.talentPools = talentPools;
    }

    public Set<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", passwordHash='" + passwordHash + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", activationKey='" + activationKey + '\'' +
            ", invitationKey='" + invitationKey + '\'' +
            ", resetKey='" + resetKey + '\'' +
            ", resetDate=" + resetDate +
            ", fileId=" + fileId +
            '}';
    }
}
