package io.avand.web.rest.component;

import io.avand.service.dto.UserDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import io.avand.web.rest.vm.response.UserIncludeVM;
import javassist.NotFoundException;

public interface UserComponent {
    ResponseVM<UserDTO> save(UserDTO userDTO) throws NotFoundException;

    ResponseVM<UserDTO> findById(Long id);
}
