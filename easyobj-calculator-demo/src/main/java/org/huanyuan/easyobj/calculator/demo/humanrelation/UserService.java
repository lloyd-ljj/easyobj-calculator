package org.huanyuan.easyobj.calculator.demo.humanrelation;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @Author lianjunjie
 * @Description
 * @Date 2023-04-17 14:01
 */
public class UserService {

    private static Map<Integer, User> userMap = ImmutableMap.<Integer, User>builder()
            .put(1, new User(1, 4, 5, 1, "tom", "11111111", 1))
            .put(2, new User(2, 3, 5, 1, "suri", "22222222", 0))
            .put(3, new User(3, 4, 5, 2, "bob", "3333333", 1))
            .put(4, new User(4, 5, 5, 3, "betty", "4444444", 0))
            .put(5, new User(5, 5, 5, 3,"andy", "5555555", 1))
            .build();

    public static User getUser(Integer id) {
        return userMap.get(id);
    }

}
