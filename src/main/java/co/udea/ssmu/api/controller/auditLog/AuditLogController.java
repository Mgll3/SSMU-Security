package co.udea.ssmu.api.controller.auditLog;

import co.udea.ssmu.api.services.auditLog.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "session", description = "Gestión de la sesión")
@RestController
@RequestMapping("/session")
public class AuditLogController {

    @Autowired
    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @PostMapping("/close")
    @Operation(summary = "Permite salvar auditoria en BD al cerrar la sesión")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Se cerró sesión exitosamente"),
            @ApiResponse(responseCode = "400", description = "Token de autenticación invalido")
    })
    public ResponseEntity closeSession(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authHeader.split(" ")[1].trim();

        try {
            auditLogService.closeSession(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
