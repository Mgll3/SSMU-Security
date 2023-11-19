package co.udea.ssmu.api.model.jpa.model.auditLog;


import co.udea.ssmu.api.model.jpa.model.user.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User idUser;
    @Column
    private Long timeSession;
    @Column
    private Date startSession;
    @Column
    private Date endSession;

    // Constructors
    public AuditLog() {
    }

    public AuditLog(User idUser, Long timeSession, Date startSession, Date endSession) {
        this.idUser = idUser;
        this.timeSession = timeSession;
        this.startSession = startSession;
        this.endSession = endSession;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public User getIdUser() {
        return idUser;
    }
    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
    public Long getTimeSession() {
        return timeSession;
    }
    public void setTimeSession(Long timeSession) {
        this.timeSession = timeSession;
    }
    public Date getStartSession() {
        return startSession;
    }
    public void setStartSession(Date startSession) {
        this.startSession = startSession;
    }
    public Date getEndSession() {
        return endSession;
    }
    public void setEndSession(Date endSession) {
        this.endSession = endSession;
    }


}
