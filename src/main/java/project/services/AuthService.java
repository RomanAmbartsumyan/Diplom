package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private Map<String, Integer> authUsers;

    public void saveSession(String sessionId, Integer userId){
        authUsers.put(sessionId, userId);
    }

    public Integer getUserId(String sessionId){
        return authUsers.get(sessionId);
    }

    public boolean checkSession(){
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        return authUsers.get(sessionId) != null;
    }
}
