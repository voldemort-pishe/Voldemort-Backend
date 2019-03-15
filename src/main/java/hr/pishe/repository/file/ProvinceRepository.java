package hr.pishe.repository.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.pishe.domain.entity.file.ProvinceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Repository
public class ProvinceRepository {

    private final Logger log = LoggerFactory.getLogger(ProvinceRepository.class);

    public List<ProvinceEntity> findAll() {
        log.debug("Request to find all province");

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ProvinceEntity>> typeReference = new TypeReference<List<ProvinceEntity>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/province.json");
        try {
            return mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            System.out.println("Unable to save users: " + e.getMessage());
            return null;
        }
    }

    public ProvinceEntity findByName(String name) {
        log.debug("Request to find province by name : {}", name);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ProvinceEntity>> typeReference = new TypeReference<List<ProvinceEntity>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/province.json");
        try {
            List<ProvinceEntity> provinceEntities = mapper.readValue(inputStream, typeReference);
            for (ProvinceEntity provinceEntity : provinceEntities) {
                if (provinceEntity.getName().equals(name))
                    return provinceEntity;
            }
            return null;
        } catch (IOException e) {
            System.out.println("Unable to save users: " + e.getMessage());
            return null;
        }
    }

}
