package hr.pishe.web.rest.component;

import hr.pishe.service.dto.UserDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;

public interface UserComponent {
    ResponseVM<UserDTO> save(UserDTO userDTO) throws NotFoundException;

    ResponseVM<UserDTO> findById(Long id);
}
