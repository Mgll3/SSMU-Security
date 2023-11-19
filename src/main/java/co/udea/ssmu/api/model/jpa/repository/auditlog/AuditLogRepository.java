package co.udea.ssmu.api.model.jpa.repository.auditlog;

import co.udea.ssmu.api.model.jpa.model.auditLog.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository <AuditLog, Long> {
}
