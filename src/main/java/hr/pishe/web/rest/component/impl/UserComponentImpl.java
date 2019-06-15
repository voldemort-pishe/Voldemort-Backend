package hr.pishe.web.rest.component.impl;

import hr.pishe.service.FileService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.FileDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.web.rest.component.UserComponent;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserComponentImpl implements UserComponent {

    private final Logger log = LoggerFactory.getLogger(UserComponentImpl.class);
    private final UserService userService;
    private final FileService fileService;

    public UserComponentImpl(UserService userService,
                             FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }


    @Override
    public ResponseVM<UserDTO> save(UserDTO userDTO) throws NotFoundException {
        log.debug("Request to save userDTO : {}", userDTO);
        userDTO = userService.update(userDTO);
        ResponseVM<UserDTO> responseVM = new ResponseVM<>();
        responseVM.setData(userDTO);
        responseVM.setInclude(this.createIncluded(userDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<UserDTO> findById(Long id) {
        log.debug("Request to find userDTO by id : {}", id);
        Optional<UserDTO> userDTO = userService.findById(id);
        ResponseVM<UserDTO> responseVM = new ResponseVM<>();
        responseVM.setData(userDTO.get());
        responseVM.setInclude(this.createIncluded(userDTO.get()));
        return responseVM;
    }

    private Map<String, Object> createIncluded(UserDTO userDTO) {
        Map<String, Object> included = new HashMap<>();
        Optional<FileDTO> fileDTOOptional = fileService.findById(userDTO.getFileId());
        fileDTOOptional.ifPresent(fileDTO -> included.put("file", fileDTO));
        return included;
    }
}
