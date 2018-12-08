package io.avand.web.rest.component.impl;

import io.avand.service.CandidateService;
import io.avand.service.CommentService;
import io.avand.service.UserService;
import io.avand.service.dto.CommentDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.CandidateMapper;
import io.avand.service.mapper.UserMapper;
import io.avand.web.rest.component.CommentComponent;
import io.avand.web.rest.util.PageMaker;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommentComponentImpl implements CommentComponent {

    private final Logger log = LoggerFactory.getLogger(CommentComponentImpl.class);
    private final CommentService commentService;
    private final UserService userService;
    private final CandidateService candidateService;
    private final UserMapper userMapper;
    private final CandidateMapper candidateMapper;

    public CommentComponentImpl(CommentService commentService,
                                UserService userService,
                                CandidateService candidateService,
                                UserMapper userMapper,
                                CandidateMapper candidateMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.candidateService = candidateService;
        this.userMapper = userMapper;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public ResponseVM<CommentDTO> save(CommentDTO commentDTO) throws NotFoundException {
        log.debug("Request to save commentDTO via component : {}", commentDTO);
        commentDTO = commentService.save(commentDTO);
        ResponseVM<CommentDTO> responseVM = new ResponseVM<>();
        responseVM.setData(commentDTO);
        responseVM.setInclude(this.createIncluded(commentDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CommentDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find commentDTO by id via component");
        CommentDTO commentDTO = commentService.findById(id);
        ResponseVM<CommentDTO> responseVM = new ResponseVM<>();
        responseVM.setData(commentDTO);
        responseVM.setInclude(this.createIncluded(commentDTO));
        return responseVM;
    }

    @Override
    public Page<ResponseVM<CommentDTO>> findByCandidateId(Long candidateId, Pageable pageable)
        throws NotFoundException {
        log.debug("Request to findAll commentDTO by candidateId via component");
        Page<CommentDTO> commentDTOS = commentService.findAllByCandidateId(pageable, candidateId);
        List<ResponseVM<CommentDTO>> responseVMS = new ArrayList<>();
        for (CommentDTO commentDTO : commentDTOS) {
            ResponseVM<CommentDTO> responseVM = new ResponseVM<>();
            responseVM.setData(commentDTO);
            responseVM.setInclude(this.createIncluded(commentDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, commentDTOS);
    }

    private Map<String, Object> createIncluded(CommentDTO commentDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        Optional<UserDTO> userDTOOptional = userService.findById(commentDTO.getUserId());
        userDTOOptional.ifPresent(userDTO -> included.put("owner", userMapper.dtoToVm(userDTO)));

        included.put("candidate", candidateMapper.dtoToVm(candidateService.findById(commentDTO.getCandidateId())));

        return included;
    }
}
