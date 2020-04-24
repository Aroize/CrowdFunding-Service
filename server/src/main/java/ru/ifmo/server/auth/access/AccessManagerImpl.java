package ru.ifmo.server.auth.access;

import org.springframework.stereotype.Component;
import ru.ifmo.server.data.entities.User;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Component
public class AccessManagerImpl implements AccessManager {

    private static final long ONE_MINUTE = 60_000;

    private static final long EXPIRES_AFTER = ONE_MINUTE * 30;

    private final AccessTokenService accessTokenService;

    public AccessManagerImpl(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }


    @Override
    public AccessToken registerTokenForUser(User user) {
        long currentTimeMills = Calendar.getInstance().getTimeInMillis();
        AccessToken accessToken = new AccessToken(user.getId(), new Date(currentTimeMills + EXPIRES_AFTER));
        //Register new token in service and delete all previous
        accessTokenService.deleteAccessToken(user.getId());
        accessTokenService.save(accessToken);
        return accessToken;
    }

    @Override
    public boolean checkAccessToken(String token, int uid) {
        Optional<AccessToken> accessToken = accessTokenService.findAccessTokenByValue(token, uid);
        //TODO(Add check for token expiration)
        return accessToken.isPresent();
    }

    @Override
    public void deleteTokenForUser(String token, int uid) {
        Optional<AccessToken> accessToken =  accessTokenService.findAccessTokenByValue(token, uid);
        accessToken.ifPresent(realToken -> {
            accessTokenService.deleteAccessToken(realToken.getId());
        });
    }
}
