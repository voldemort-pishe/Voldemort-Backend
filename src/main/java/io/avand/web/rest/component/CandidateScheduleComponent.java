package io.avand.web.rest.component;

import io.avand.service.CandidateService;
import io.avand.service.UserService;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.CandidateScheduleDTO;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.vm.CandidateScheduleVm;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CandidateScheduleComponent {

    private final UserService userService;
    private final CandidateService candidateService;

    public CandidateScheduleComponent(UserService userService, CandidateService candidateService) {
        this.userService = userService;
        this.candidateService = candidateService;
    }

    public List<CandidateScheduleVm> convertToOwnerVM(List<CandidateScheduleDTO> candidateScheduleDTOS) {
        List<CandidateScheduleVm> candidateScheduleVms = new ArrayList<>();
        for (CandidateScheduleDTO candidateScheduleDTO : candidateScheduleDTOS) {
            try {
                CandidateScheduleVm candidateScheduleVm = new CandidateScheduleVm();
                candidateScheduleVm.setId(candidateScheduleDTO.getId());
                CandidateDTO candidateDTO = candidateService.findById(candidateScheduleDTO.getCandidateId());
                candidateScheduleVm.setExtraId(candidateScheduleDTO.getCandidateId());
                candidateScheduleVm.setFullName(candidateDTO.getFirstName() + " " + candidateDTO.getLastName());
                candidateScheduleVm.setCellphone(candidateDTO.getCellphone());
                candidateScheduleVm.setEmail(candidateDTO.getEmail());
                candidateScheduleVm.setScheduleTime(candidateScheduleDTO.getScheduleDate());
                candidateScheduleVms.add(candidateScheduleVm);
            } catch (NotFoundException ignore) {
            }
        }
        return candidateScheduleVms;
    }

    public List<CandidateScheduleVm> convertToCandidateVM(List<CandidateScheduleDTO> candidateScheduleDTOS){
        List<CandidateScheduleVm> candidateScheduleVms = new ArrayList<>();
        for (CandidateScheduleDTO candidateScheduleDTO : candidateScheduleDTOS) {
            CandidateScheduleVm candidateScheduleVm = new CandidateScheduleVm();
            candidateScheduleVm.setId(candidateScheduleDTO.getId());
            Optional<UserDTO> userDTOOptional = userService.findById(candidateScheduleDTO.getOwner());
            if (userDTOOptional.isPresent()) {
                UserDTO userDTO = userDTOOptional.get();
                candidateScheduleVm.setExtraId(candidateScheduleDTO.getOwner());
                candidateScheduleVm.setFullName(userDTO.getFirstName() + " " + userDTO.getLastName());
                candidateScheduleVm.setEmail(userDTO.getEmail());
                candidateScheduleVm.setScheduleTime(candidateScheduleDTO.getScheduleDate());
                candidateScheduleVms.add(candidateScheduleVm);
            }
        }
        return candidateScheduleVms;
    }
}
