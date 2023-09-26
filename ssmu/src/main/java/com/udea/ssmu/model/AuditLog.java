package com.udea.ssmu.model;

import java.util.Date;
import java.util.Map;

public class AuditLog {
    private int id;
    private String taskType;
    private int responsibleUserId;
    private Map data;
    private Date date;
    private String taskDetails;
}
