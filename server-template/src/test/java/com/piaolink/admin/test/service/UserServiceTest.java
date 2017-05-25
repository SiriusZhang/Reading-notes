package com.piaolink.admin.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piaolink.admin.entity.User;
import com.piaolink.admin.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bill on 2017-5-5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceTest {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Test
    public void testSelectByPrimaryKey() throws JsonProcessingException {
        User user = userService.getUser("13418688416");
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info(objectMapper.writeValueAsString(user));
        Assert.assertEquals(user.getUserName(), "范斌");
    }
}
