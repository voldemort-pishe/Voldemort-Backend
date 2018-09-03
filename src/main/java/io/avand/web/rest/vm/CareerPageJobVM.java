package io.avand.web.rest.vm;

import io.avand.domain.enumeration.JobType;
import io.avand.domain.enumeration.LanguageType;

import java.io.Serializable;

public class CareerPageJobVM implements Serializable {

    private Long id;
    private String uniqueId;
    private String nameFa;
    private String descriptionFa;
    private String nameEn;
    private String descriptionEn;
    private LanguageType language;
    private JobType type;
    private String location;
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUniqueId() {
        return uniqueId;
    }
    
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    
    public String getNameFa() {
        return nameFa;
    }
    
    public void setNameFa(String nameFa) {
        this.nameFa = nameFa;
    }
    
    public String getDescriptionFa() {
        return descriptionFa;
    }
    
    public void setDescriptionFa(String descriptionFa) {
        this.descriptionFa = descriptionFa;
    }
    
    public String getNameEn() {
        return nameEn;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    
    public String getDescriptionEn() {
        return descriptionEn;
    }
    
    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
    
    public LanguageType getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageType language) {
        this.language = language;
    }
    
    public JobType getType() {
        return type;
    }
    
    public void setType(JobType type) {
        this.type = type;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    @Override
    public String toString() {
        return "CareerPageJobVM{" +
            "id=" + id +
            ", uniqueId='" + uniqueId + '\'' +
            ", nameFa='" + nameFa + '\'' +
            ", descriptionFa='" + descriptionFa + '\'' +
            ", nameEn='" + nameEn + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", language=" + language +
            ", type=" + type +
            ", location='" + location + '\'' +
            '}';
    }
}
