package io.avand.web.rest.vm;

import java.io.Serializable;
import java.util.List;

public class CompanyMemberVM implements Serializable {
    private List<String> emails;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }



    @Override
    public String toString() {
        return "CompanyMemberVM{" +
            "emails=" + emails +
            '}';
    }
}
