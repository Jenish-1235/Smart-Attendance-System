package com.backend.attendance.backend.services;

import com.backend.attendance.backend.models.IOSPollingResponse;
import com.backend.attendance.backend.models.StudentSession;
import com.backend.attendance.backend.repositories.AccessPointRepository;
import com.backend.attendance.backend.utils.AttendanceProvider;
import com.backend.attendance.backend.utils.StudentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IOSPollingService {

    @Autowired
    private AttendanceProvider attendanceProvider;

    @Autowired
    private AccessPointRepository accessPointRepository;

    @Autowired
    private StudentProvider studentProvider;

    ConcurrentHashMap<String, StudentSession> iosSessions = new ConcurrentHashMap<>();

    public IOSPollingResponse poll(String email, String bssid) throws SQLException {
        IOSPollingResponse response = new IOSPollingResponse();

        if(!studentProvider.getStudentDirectory().containsKey(email)) {
            response.setSuccess(Boolean.FALSE);
            return response;
        }

        String batch = studentProvider.getStudentDirectory().get(email).getBatch();
        String year = studentProvider.getStudentDirectory().get(email).getYear();

        String subject = attendanceProvider.getSubjectMap().get(year + ":" + batch);

        String attendanceSessionKey = year + ":" + batch + ":" + subject;

        if(!attendanceProvider.getMonitoringStatusMap().containsKey(attendanceSessionKey)) {
            response.setSuccess(Boolean.FALSE);
            return response;
        }

        if (!accessPointRepository.checkAccessPoint(bssid)){
            response.setSuccess(Boolean.FALSE);
            return response;
        }

        long now = System.currentTimeMillis();
        StudentSession existingSession = iosSessions.get(attendanceSessionKey);
        if(existingSession == null) {
            StudentSession newStudentSession = new StudentSession(email, bssid, now, 0L, null, batch, year, subject, true);
            newStudentSession.setLastPingTime(now);
            iosSessions.put(email, newStudentSession);
        }else {
            Long duration = now - existingSession.getLastPingTime();
            if (existingSession.getIsConnected()){
                existingSession.setTotalConnectionTime(existingSession.getTotalConnectionTime() + duration);
                existingSession.setLastPingTime(now);
            }else{
                existingSession.setLastPingTime(now);
                existingSession.setIsConnected(true);
            }
        }
        response.setSuccess(Boolean.TRUE);
        return response;
    }
}
