package com.piaolink.admin.service.imp;

import com.piaolink.admin.entity.OpAdmin;
import com.piaolink.admin.mapper.OpAdminMapper;
import com.piaolink.admin.service.OpAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.piaolink.admin.service.OpAdminService")
public class OpAdminServiceImp implements OpAdminService {
    Logger logger = LoggerFactory.getLogger(OpAdminServiceImp.class);

    @Autowired
    private OpAdminMapper opAdminMapper;

    @Override
    public OpAdmin getOpAdmin(String username){
        OpAdmin opAdmin;
        opAdmin = opAdminMapper.selectByUserName(username);
        return opAdmin;
    }

    @Override
    public int updatePassword(String username, String newPassword) {
        OpAdmin opAdmin = opAdminMapper.selectByUserName(username);
        opAdmin.setPassword(newPassword);
        return opAdminMapper.updateByPrimaryKey(opAdmin);
    }

    @Override
    public int insert(OpAdmin opAdmin) {
        return opAdminMapper.insert(opAdmin);
    }
}
