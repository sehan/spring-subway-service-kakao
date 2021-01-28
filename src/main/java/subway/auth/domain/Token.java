package subway.auth.domain;

public class Token {

    private String accessToken;

    private Token(String accessToken) {
        this.accessToken = accessToken;
    }

    public static Token of(String accessToken) {
        return new Token(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }
}
