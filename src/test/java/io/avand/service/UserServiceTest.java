package io.avand.service;

import io.avand.VoldemortApp;
import io.avand.service.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        UserDTO userDTO = userService
            .save(
                "pouyaashna@gmail.com",
                "Pouya",
                "Ashna",
                "pouyaashna@gmail.com",
                "1234"
            );
        System.out.println(userDTO);
    }

}
