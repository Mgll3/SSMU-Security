package co.udea.ssmu.api.controller.user;


import co.udea.ssmu.api.model.jpa.dto.user.UserDTO;
import co.udea.ssmu.api.services.user.service.UserService;
import co.udea.ssmu.api.utils.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserService userServices;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userServices = userService;
        this.passwordEncoder  = passwordEncoder;
    }



    /**
     * http://localhost:8080/auth/register
     * Registrar Usuario nuevo
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO userFoundDTO = userServices.save(userDTO);
        if (userFoundDTO == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else{
            return ResponseEntity.status(HttpStatus.CREATED).body(userFoundDTO);
        }
    }

    /**
     * http://localhost:8080/auth/login
     * Login de usuario
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO userDTO) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        String jwt = this.jwtUtil.create(userDTO.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);

        return ResponseEntity.ok(response);
    }
}
