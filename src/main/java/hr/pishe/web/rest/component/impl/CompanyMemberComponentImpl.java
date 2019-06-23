package hr.pishe.web.rest.component.impl;

import hr.pishe.service.CompanyMemberService;
import hr.pishe.service.CompanyService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.CompanyMemberDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.CompanyMapper;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.web.rest.component.CompanyMemberComponent;
import hr.pishe.web.rest.util.PageMaker;
import hr.pishe.web.rest.vm.CompanyMemberFilterVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CompanyMemberComponentImpl implements CompanyMemberComponent {

    private final Logger log = LoggerFactory.getLogger(CompanyMemberComponentImpl.class);
    private final CompanyMemberService companyMemberService;
    private final UserService userService;
    private final CompanyService companyService;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    public CompanyMemberComponentImpl(CompanyMemberService companyMemberService,
                                      UserService userService,
                                      CompanyService companyService,
                                      UserMapper userMapper,
                                      CompanyMapper companyMapper) {
        this.companyMemberService = companyMemberService;
        this.userService = userService;
        this.companyService = companyService;
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
    }


    @Override
    public ResponseVM<CompanyMemberDTO> save(CompanyMemberDTO companyMemberDTO) throws NotFoundException {
        log.debug("Request to save companyMemberDTO via component");
        companyMemberDTO = companyMemberService.save(companyMemberDTO);
        ResponseVM<CompanyMemberDTO> responseVM = new ResponseVM<>();
        responseVM.setData(companyMemberDTO);
        responseVM.setInclude(this.createIncluded(companyMemberDTO));
        return responseVM;
    }

    @Override
    public List<ResponseVM<CompanyMemberDTO>> saveAll(List<CompanyMemberDTO> memberDTOS) throws NotFoundException {
        log.debug("Request to save companyMemberDTO via component");
        List<CompanyMemberDTO> companyMemberDTOS = companyMemberService.saveAll(memberDTOS);
        List<ResponseVM<CompanyMemberDTO>> responseVMS = new ArrayList<>();
        for (CompanyMemberDTO companyMemberDTO : companyMemberDTOS) {
            ResponseVM<CompanyMemberDTO> responseVM = new ResponseVM<>();
            responseVM.setData(companyMemberDTO);
            responseVM.setInclude(this.createIncluded(companyMemberDTO));
            responseVMS.add(responseVM);
        }
        return responseVMS;
    }

    @Override
    public ResponseVM<CompanyMemberDTO> findById(Long id) throws NotFoundException {
        CompanyMemberDTO companyMemberDTO = companyMemberService.findById(id);
        ResponseVM<CompanyMemberDTO> responseVM = new ResponseVM<>();
        responseVM.setData(companyMemberDTO);
        responseVM.setInclude(this.createIncluded(companyMemberDTO));
        return responseVM;
    }

    @Override
    public Page<ResponseVM<CompanyMemberDTO>> findAllByFilter(CompanyMemberFilterVM filterVM, Pageable pageable)
        throws NotFoundException {
        Page<CompanyMemberDTO> companyMemberDTOS = companyMemberService.findAllByFilter(filterVM, pageable);
        List<ResponseVM<CompanyMemberDTO>> responseVMS = new ArrayList<>();
        for (CompanyMemberDTO companyMemberDTO : companyMemberDTOS) {
            ResponseVM<CompanyMemberDTO> responseVM = new ResponseVM<>();
            responseVM.setData(companyMemberDTO);
            responseVM.setInclude(this.createIncluded(companyMemberDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, companyMemberDTOS);
    }

    @Override
    public Page<ResponseVM<CompanyMemberDTO>> findAllActiveMember(Pageable pageable) throws NotFoundException {
        Page<CompanyMemberDTO> companyMemberDTOS = companyMemberService.findAllActiveMember(pageable);
        List<ResponseVM<CompanyMemberDTO>> responseVMS = new ArrayList<>();
        for (CompanyMemberDTO companyMemberDTO : companyMemberDTOS) {
            ResponseVM<CompanyMemberDTO> responseVM = new ResponseVM<>();
            responseVM.setData(companyMemberDTO);
            responseVM.setInclude(this.createIncluded(companyMemberDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, companyMemberDTOS);
    }

    private Map<String, Object> createIncluded(CompanyMemberDTO companyMemberDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        Optional<UserDTO> userDTOOptional = userService.findByEmail(companyMemberDTO.getUserEmail());
        userDTOOptional.ifPresent(userDTO -> included.put("user", userMapper.dtoToVm(userDTO)));

        included.put("company", companyMapper.dtoToVm(companyService.findById(companyMemberDTO.getCompanyId())));

        return included;
    }
}
