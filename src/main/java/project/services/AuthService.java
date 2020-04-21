package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import project.exceptions.UnauthorizedException;

import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private Map<String, Integer> authUsers;

    public void saveSession(String sessionId, Integer userId) {
        authUsers.put(sessionId, userId);
    }

    public Integer getUserId() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        return authUsers.get(sessionId);
    }

    public void checkSession() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        if (authUsers.get(sessionId) == null) {
            throw new UnauthorizedException();
        }
    }

    public void logout() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        authUsers.remove(sessionId);
    }
}
