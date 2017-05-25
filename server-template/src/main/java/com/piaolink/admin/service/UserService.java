package com.piaolink.admin.service;

import com.piaolink.admin.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    List<User> getUsers();
    List<User> getUserByLikeId(String id);
    List<User> getUserByValidation(String validation);
    List<User> getUserLikePhone(String phone);
    User getUserByPhone(String phone);
    List<User> getUserLikeName(String name);
    User getUserByName(String name);
    List<User> getUserLikeUsername(String username);
    User getUserByUsername(String username);
    List<User> getUserLikeCreatetime(Date createtime);
    List<User> getUserByReguid(String reguid);

    User getUser(String id);
    int setUserValidationResult(String id, String result);

    boolean saveUser(User user);
}
