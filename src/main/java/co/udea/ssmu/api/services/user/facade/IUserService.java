package co.udea.ssmu.api.services.user.facade;

import co.udea.ssmu.api.model.jpa.dto.user.UserDTO;
import co.udea.ssmu.api.model.jpa.model.user.User;

import java.util.Optional;

public interface IUserService {

    UserDTO save(UserDTO userDTO);
}
