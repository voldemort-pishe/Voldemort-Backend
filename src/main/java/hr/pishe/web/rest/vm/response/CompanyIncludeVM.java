package hr.pishe.web.rest.vm.response;

import java.io.Serializable;

public class CompanyIncludeVM implements Serializable {

    private Long id;
    private String nameEn;
    private String nameFa;
    private String descriptionEn;
    private String descriptionFa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameFa() {
        return nameFa;
    }

    public void setNameFa(String nameFa) {
        this.nameFa = nameFa;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionFa() {
        return descriptionFa;
    }

    public void setDescriptionFa(String descriptionFa) {
        this.descriptionFa = descriptionFa;
    }

    @Override
    public String toString() {
        return "CompanyIncludeVM{" +
            "id=" + id +
            ", nameEn='" + nameEn + '\'' +
            ", nameFa='" + nameFa + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", descriptionFa='" + descriptionFa + '\'' +
            '}';
    }
}
