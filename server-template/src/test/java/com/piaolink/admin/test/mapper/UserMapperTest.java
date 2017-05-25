package com.piaolink.admin.test.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piaolink.admin.entity.User;
import com.piaolink.admin.mapper.UserMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bill on 2017-5-5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class UserMapperTest {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectByPrimaryKey() throws JsonProcessingException {
        List<User> userList = userMapper.selectByLikeId("13418688416");
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info(objectMapper.writeValueAsString(userList));
        Assert.assertEquals(userList.get(0).getUserName(), "范斌");
    }

}
