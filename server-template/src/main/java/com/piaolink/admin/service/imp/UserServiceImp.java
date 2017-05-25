package com.piaolink.admin.service.imp;

import com.piaolink.admin.entity.User;
import com.piaolink.admin.mapper.UserMapper;
import com.piaolink.admin.service.UserService;
import com.piaolink.common.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Service("com.piaolink.admin.service.UserService")
public class UserServiceImp implements UserService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    @Override
    public List<User> getUsers() {
        List<User> userList = userMapper.getUsers();
        return userList;
    }

    @Override
    public List<User> getUserByLikeId(String id) {
        List<User> userList = userMapper.selectByLikeId(id);
        return userList;
    }

//    @Cacheable(value ="default", key = "#id")
    @Override
    public User getUser(String id) {
        final User user = userMapper.selectByPrimaryKey(id);
//        redisTemplate.execute(new RedisCallback() {
//            @Override
//            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                redisConnection.set(redisTemplate.getStringSerializer().serialize(user.getUserId()), redisTemplate.getValueSerializer().serialize(user.getUserName()));
//                ValueOperations<Serializable, Serializable> valueops = redisTemplate.opsForValue();
//                valueops.set("billfan",user);
//                User user1 =  (User)valueops.get("深圳区块链金服");
//
//                String json  = null;
//                ObjectMapper objectMapper = new ObjectMapper();
//                try {
//                    json =objectMapper.writeValueAsString(user);
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//                redisConnection.set(redisTemplate.getStringSerializer().serialize(user.getUserId()), redisTemplate.getStringSerializer().serialize(json));
//                return null;
//            }
//        });
        return user;
    }

    @Override
    public int setUserValidationResult(String id, String result) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setValidationResult(result);
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public boolean saveUser(User user) {
        return false;
    }

    @Override
    public List<User> getUserByValidation(String validation) {
        List<User> userList = userMapper.selectByValidation(validation);
        return userList;
    }

    @Override
    public List<User> getUserLikePhone(String phone) {
        List<User> userList = userMapper.selectLikePhone(phone);
        return userList;
    }

    @Override
    public User getUserByPhone(String phone) {
        User user = userMapper.selectByPhone(phone);
        return user;
    }

    @Override
    public List<User> getUserLikeName(String name) {
        List<User> userList = userMapper.selectLikeName(name);
        return userList;
    }

    @Override
    public User getUserByName(String name) {
        User user = userMapper.selectByName(name);
        return user;
    }

    @Override
    public List<User> getUserLikeUsername(String username) {
        List<User> userList = userMapper.selectLikeUsername(username);
        return userList;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        return user;
    }

    @Override
    public List<User> getUserLikeCreatetime(Date createtime)
    {
        Date beginDate = null;
        Date endDate = null;
        List<User> userList = null;

        Calendar date = Calendar.getInstance();
        date.setTime(createtime);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MINUTE,0);
        beginDate = date.getTime();

        date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
        endDate = date.getTime();
        userList = userMapper.selectLikeCreatetime(beginDate, endDate);

        return userList;
    }

    @Override
    public List<User> getUserByReguid(String reguid){
        List<User> userList = userMapper.selectByReguid(reguid);
        return userList;
    }
}
