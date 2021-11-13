package indi.ljf.im.protocol;

import lombok.Data;

/**
 * @author: ljf
 * @date: 2021/10/25 21:47
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Data
public class Session {
    private int userId;
    private String userName;

    public Session(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
