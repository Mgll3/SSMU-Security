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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    public static List<String> convertObjectArrayToListString(Object[] objectArray) {
        return Arrays.stream(objectArray)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    /**
     * http://localhost:8080/auth/login
     * Login de usuario
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        try {
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
            Authentication authentication = this.authenticationManager.authenticate(login);

            String jwt = this.jwtUtil.create(userDTO.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("email", userDTO.getEmail());
            response.put("role", convertObjectArrayToListString(authentication.getAuthorities().toArray()));


            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(401).body(null);
        }



    }
}
