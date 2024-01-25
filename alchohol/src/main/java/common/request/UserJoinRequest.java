package common.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String email;
    private String password;
    private String nickname;
    private String statement;
    private String userImage;

    public UserJoinRequest(String email, String password, String nickname, String statement, String userImage) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.statement = statement;
        this.userImage = userImage;
    }
}
