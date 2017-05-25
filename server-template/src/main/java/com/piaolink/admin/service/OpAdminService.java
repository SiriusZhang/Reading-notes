package com.piaolink.admin.service;

import com.piaolink.admin.entity.OpAdmin;

public interface OpAdminService {
    OpAdmin getOpAdmin(String username);
    int updatePassword(String username, String newPassword);
    int insert(OpAdmin opAdmin);
}
