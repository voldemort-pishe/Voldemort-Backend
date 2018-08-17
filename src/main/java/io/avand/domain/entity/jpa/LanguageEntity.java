package io.avand.domain.entity.jpa;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "language_entity")
public class LanguageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="language_values_entity", joinColumns=@JoinColumn(name="language_id"))
    private Map<String, String> strings = new HashMap<>();

    public void addString(String locale, String text) {
        strings.put(locale, text);
    }

    public String getString(String locale) {
        return strings.get(locale);
    }

    @Override
    public String toString() {
        return "LanguageEntity{" +
            "id=" + id +
            ", strings=" + strings +
            '}';
    }
}
