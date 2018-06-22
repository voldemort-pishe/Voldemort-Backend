package io.avand.repository;

import io.avand.domain.FileEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FileEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
