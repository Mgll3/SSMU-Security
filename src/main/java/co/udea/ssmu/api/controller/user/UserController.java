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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Auth", description = "Autenticacion y Registro de nuevos usuarios")
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




    @PostMapping("/register")
    @Operation(summary = "Permite registrar un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "El usuario se registró correctamente"),
            @ApiResponse(responseCode = "409", description = "El usuario no se registró correctamente")
    })
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO userFoundDTO = userServices.save(userDTO);
        if (userFoundDTO == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else{
            return ResponseEntity.status(HttpStatus.CREATED).body(userFoundDTO);
        }
    }


    @PostMapping("/login")
    @Operation(summary = "Permite iniciar session")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "El usuario inició sesión correctamente"),
            @ApiResponse(responseCode = "401", description = "El usuario no está autorizado")
    })
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

    public static List<String> convertObjectArrayToListString(Object[] objectArray) {
        return Arrays.stream(objectArray)
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}
