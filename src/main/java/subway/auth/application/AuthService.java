package subway.auth.application;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private MemberDao memberDao;
    private JwtTokenProvider tokenProvider;
    private Map<String, TokenContext> tokenStore = new HashMap<>();

    public AuthService(JwtTokenProvider tokenProvider, MemberDao memberDao) {
        this.tokenProvider = tokenProvider;
        this.memberDao = memberDao;
    }

    public String createToken(String email, String password) {
        Member member = checkPasswordAndGetMember(email, password);
        String tokenString = tokenProvider.createToken(member.getEmail());
        tokenStore.put(tokenString, new TokenContext(new LoginMember(member.getId(), member.getEmail(), member.getAge())));
        return tokenString;
    }

    private Member checkPasswordAndGetMember(String email, String password) {
        Member member;
        try {
            member = memberDao.findByEmail(email);
        } catch ( EmptyResultDataAccessException e ){
            throw new AuthenticationException("유저가 존재하지 않습니다", e);
        }
        if(!member.getPassword().equals(password) ) throw new AuthenticationException("패스워드가 일치하지 않습니다");
        return member;
    }

    public TokenContext getTokenContext(String token){
        return tokenStore.get(token);
    }

    public void validateToken(String token) {
        if( !tokenProvider.validateToken(token) ){
            throw new AuthenticationException("토큰이 유효하지 않습니다");
        }

        if( !tokenStore.containsKey(token) ){
            throw new AuthenticationException("시스테에서 발급된 토큰이 아닙니다");
        }

    }
}
