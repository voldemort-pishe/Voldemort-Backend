package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserDTO extends AbstractAuditingDTO implements Serializable {

    @NotNull
    private Long id;

    private String login;

    private String passwordHash;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean activated;

    private String activationKey;

    private String resetKey;

    private ZonedDateTime resetDate;

    private Set<UserAuthorityDTO> userAuthorities = new HashSet<>();

    private Set<CompanyDTO> companies = new HashSet<>();

    private Set<TalentPoolDTO> talentPools = new HashSet<>();

    private Set<InvoiceDTO> invoices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", passwordHash='" + passwordHash + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", activationKey='" + activationKey + '\'' +
            ", resetKey='" + resetKey + '\'' +
            ", resetDate=" + resetDate +
            ", userAuthorities=" + userAuthorities +
            ", companies=" + companies +
            ", talentPools=" + talentPools +
            ", invoices=" + invoices +
            '}';
    }
}
