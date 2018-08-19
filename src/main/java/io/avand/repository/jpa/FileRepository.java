package io.avand.repository.jpa;

import io.avand.domain.entity.jpa.FileEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the FileEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findById(Long id);
}
