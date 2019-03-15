package hr.pishe.web.rest.component.impl;

import hr.pishe.service.CompanyService;
import hr.pishe.service.FileService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.CompanyDTO;
import hr.pishe.service.dto.FileDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.web.rest.component.CompanyComponent;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CompanyComponentImpl implements CompanyComponent {

    private final Logger log = LoggerFactory.getLogger(CompanyComponentImpl.class);
    private final CompanyService companyService;
    private final FileService fileService;
    private final UserService userService;
    private final UserMapper userMapper;

    public CompanyComponentImpl(CompanyService companyService,
                                FileService fileService,
                                UserService userService,
                                UserMapper userMapper) {
        this.companyService = companyService;
        this.fileService = fileService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseVM<CompanyDTO> save(CompanyDTO companyDTO) throws NotFoundException {
        log.debug("Request to save companyDTO via component : {}", companyDTO);
        companyDTO = companyService.save(companyDTO);
        ResponseVM<CompanyDTO> responseVM = new ResponseVM<>();
        responseVM.setData(companyDTO);
        responseVM.setInclude(this.createIncluded(companyDTO));
        return responseVM;
    }

    @Override
    public ResponseVM<CompanyDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find companyDTO by id via component : {}", id);
        CompanyDTO companyDTO = companyService.findById(id);
        ResponseVM<CompanyDTO> responseVM = new ResponseVM<>();
        responseVM.setData(companyDTO);
        responseVM.setInclude(this.createIncluded(companyDTO));
        return responseVM;
    }

    private Map<String, Object> createIncluded(CompanyDTO companyDTO) {
        Map<String, Object> included = new HashMap<>();

        Optional<FileDTO> fileDTOOptional = fileService.findById(companyDTO.getFileId());
        fileDTOOptional.ifPresent(fileDTO -> included.put("file", fileDTO));

        Optional<UserDTO> userDTOOptional = userService.findById(companyDTO.getUserId());
        userDTOOptional.ifPresent(userDTO -> included.put("user", userMapper.dtoToVm(userDTO)));

        return included;
    }
}
