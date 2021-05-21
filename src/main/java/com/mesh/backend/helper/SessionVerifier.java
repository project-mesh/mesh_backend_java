package com.mesh.backend.helper;

import com.mesh.backend.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class SessionVerifier {

    @Autowired
    private MapSessionRepository mapSessionRepository;

    public void createSession(Users user){
        MapSession mapSession = mapSessionRepository.createSession();
        mapSession.setId(user.getId().toString());
        mapSession.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, user.getEmail());
        mapSessionRepository.save(mapSession);
    }

    public boolean verify(Users user){
        if(user == null){
            return false;
        }
        MapSession mapSession = mapSessionRepository.findById(user.getId().toString());
        if(mapSession == null){
            return false;
        }
        String username = mapSession.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        return username.equals(user.getEmail());
    }

    public int getOnlineUserCount(int maxId){
        int count = 0;
        for(int i = 1; i <= maxId; ++i){
            MapSession mapSession = mapSessionRepository.findById(String.valueOf(i));
            if(mapSession != null){
                ++count;
            }
        }
        return count;
    }

}
