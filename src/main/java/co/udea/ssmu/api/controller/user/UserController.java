package co.udea.ssmu.api.controller.user;


import co.udea.ssmu.api.model.jpa.dto.user.UserDTO;
import co.udea.ssmu.api.services.user.service.UserService;
import co.udea.ssmu.api.utils.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserService userServices;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userServices = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    /**
     * http://localhost:8080/
     * Registrar Usuario nuevo
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO){
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        UserDTO userFoundDTO = userServices.save(userDTO);
        if (userFoundDTO == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else{
            return ResponseEntity.status(HttpStatus.CREATED).body(userFoundDTO);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserDTO userDTO) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getPrincipal());

        String jwt = this.jwtUtil.create(userDTO.getEmail());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();


    }
}
