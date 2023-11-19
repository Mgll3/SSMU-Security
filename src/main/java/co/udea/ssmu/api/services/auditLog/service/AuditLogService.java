package co.udea.ssmu.api.services.auditLog.service;

import co.udea.ssmu.api.model.jpa.model.auditLog.AuditLog;
import co.udea.ssmu.api.model.jpa.model.user.User;
import co.udea.ssmu.api.model.jpa.repository.auditlog.AuditLogRepository;
import co.udea.ssmu.api.model.jpa.repository.user.UserRepository;
import co.udea.ssmu.api.utils.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Optional;

@Service
public class AuditLogService {

    @Autowired
    private final AuditLogRepository auditLogRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    public AuditLogService(AuditLogRepository auditLogRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.auditLogRepository = auditLogRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }



    public void closeSession(String token) {
        String email = jwtUtil.getEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
        Date  startSession = jwtUtil.getIssuedAt(token);
        Date endSession = new Date();
        //Se guarda en segundos
        Long timeSession = ( endSession.getTime() - startSession.getTime())/1000;

        if (user.isPresent()){
            AuditLog auditLog = new AuditLog(user.get(), timeSession, startSession, endSession);
            auditLogRepository.save(auditLog);
        }

    }

}
