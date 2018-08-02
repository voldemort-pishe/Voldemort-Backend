package io.avand.web.rest.vm;

import java.io.Serializable;

public class CareerPageCompanyVM implements Serializable {

    private String nameEn;

    private String nameFa;

    private String descriptionEn;

    private String descriptionFa;

    private String filePath;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "CareerPageCompanyVM{" +
            "nameEn='" + nameEn + '\'' +
            ", nameFa='" + nameFa + '\'' +
            ", descriptionEn='" + descriptionEn + '\'' +
            ", descriptionFa='" + descriptionFa + '\'' +
            ", filePath='" + filePath + '\'' +
            '}';
    }
}
