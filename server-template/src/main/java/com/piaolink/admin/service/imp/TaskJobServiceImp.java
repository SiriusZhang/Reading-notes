package com.piaolink.admin.service.imp;

import com.piaolink.admin.entity.OpAuditLog;
import com.piaolink.admin.entity.User;
import com.piaolink.admin.mapper.UserMapper;
import com.piaolink.admin.service.OpAuditLogService;
import com.piaolink.admin.service.TaskJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("com.piaolink.admin.service.TaskJobService")
public class TaskJobServiceImp implements TaskJobService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("com.piaolink.admin.service.OpAuditLogService")
    OpAuditLogService opAuditLogService;

    @Override
    public void recommendRelationshipJob() {
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("K");

        List<User> users = userMapper.getUsers();
        for (User user : users) {
            String myUid = user.getMyUid();
            if (myUid == null || myUid.isEmpty())
                continue;
            int regCount = 0;
            for (User reguser: users) {
                String regUid = reguser.getRegUid();
                if (regUid == null || regUid.isEmpty())
                    continue;
                if (regUid.equals(myUid))
                    ++regCount;
            }
            logger.info("relationship result: userid" + user.getUserId() +
                    " myUid: " + user.getMyUid() + "  regCount: " + regCount);

            user.setRegCount(regCount);
            user.setRegUpdateTime(new Date());
            userMapper.updateByPrimaryKey(user);
            log.setRemark("relationship result: userid" + user.getUserId() +
                    " myUid: " + user.getMyUid() + "  regCount: " + regCount);
            log.setOperationTime(new Date());
            opAuditLogService.insertOpAuditLog(log);
        }
    }
}
