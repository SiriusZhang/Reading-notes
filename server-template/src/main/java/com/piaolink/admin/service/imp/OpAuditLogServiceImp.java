package com.piaolink.admin.service.imp;

import com.piaolink.admin.entity.OpAuditLog;
import com.piaolink.admin.mapper.OpAuditLogMapper;
import com.piaolink.admin.mapper.UserMapper;
import com.piaolink.admin.service.OpAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.piaolink.admin.service.OpAuditLogService")
public class OpAuditLogServiceImp implements OpAuditLogService {
    @Autowired
    private OpAuditLogMapper opAuditLogMapper;

    @Override
    public int insertOpAuditLog(OpAuditLog log){
        if (log.getUserId() == null) log.setUserId("");
        return opAuditLogMapper.insert(log);
    }
}
