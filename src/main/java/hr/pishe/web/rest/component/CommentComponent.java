package hr.pishe.web.rest.component;

import hr.pishe.service.dto.CommentDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentComponent {

    ResponseVM<CommentDTO> save(CommentDTO commentDTO) throws NotFoundException;

    ResponseVM<CommentDTO> findById(Long id) throws NotFoundException;

    Page<ResponseVM<CommentDTO>> findByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException;

}
