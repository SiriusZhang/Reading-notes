package com.piaolink.admin.controller;



import com.piaolink.admin.dto.ChangePassword;
import com.piaolink.admin.dto.ClientLogin;
import com.piaolink.admin.dto.ClientOpAdmin;
import com.piaolink.admin.dto.ClientToken;
import com.piaolink.admin.entity.OpAdmin;
import com.piaolink.admin.entity.OpAuditLog;
import com.piaolink.admin.service.OpAdminService;
import com.piaolink.admin.service.OpAuditLogService;
import com.piaolink.common.dto.ResponseDTO;
import com.piaolink.common.dto.ResponseMessage;
import com.piaolink.common.dto.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Controller
@RequestMapping("op_admin")
public class OpAdminController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("com.piaolink.admin.service.OpAdminService")
    OpAdminService opAdminService;

    @Autowired
    @Qualifier("com.piaolink.admin.service.OpAuditLogService")
    OpAuditLogService opAuditLogService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseDTO<ClientToken> login(HttpServletRequest httpRequest, @RequestBody ClientLogin newComer) {
        ResponseDTO<ClientToken> responseDTO = new ResponseDTO<>();
        OpAdmin opAdminRecord = opAdminService.getOpAdmin(newComer.getUsername());
        OpAuditLog log = new OpAuditLog();
        log.setUserId(newComer.getUsername());
        log.setOperationType("A");

        if (opAdminRecord != null && opAdminRecord.getPassword().equals(newComer.getPassword())) {

            log.setOperationTime(new Date());
            log.setRemark("username:" + opAdminRecord.getUsername() + " login success");
            opAuditLogService.insertOpAuditLog(log);

            opAdminRecord.setPassword("");
            httpRequest.getSession().setAttribute("user", opAdminRecord);
            ClientToken clientToken = new ClientToken();
            clientToken.setUsername(opAdminRecord.getUsername());
            clientToken.setRole(opAdminRecord.getRole());
            responseDTO.setData(clientToken);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
        } else {
            log.setOperationTime(new Date());
            log.setRemark("username:" + newComer.getUsername() + " login failed.");
            opAuditLogService.insertOpAuditLog(log);

            responseDTO.addMessage(new ResponseMessage("000000001",
                    "username or password is wrong." ));
            responseDTO.setStatus(ResponseStatus.FAIL);
        }
        return  responseDTO;
    }

    @RequestMapping(value = "/user/changepassword", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseDTO<String> changepassword(HttpServletRequest request, @RequestBody ChangePassword changePassword) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        OpAuditLog log = new OpAuditLog();
        log.setOperationType("G");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null && client.getUsername().equals(changePassword.getUsername())) {
            log.setAdminId(client.getId());
            OpAdmin opAdmin = opAdminService.getOpAdmin(changePassword.getUsername());
            if (opAdmin != null && opAdmin.getPassword().equals(changePassword.getOldPassword())) {
                opAdminService.updatePassword(opAdmin.getUsername(), changePassword.getNewPassword());
                responseDTO.setStatus(ResponseStatus.SUCCESS);
                log.setRemark("change " + opAdmin.getUsername() + "'s password success.");
            }
            else {
                log.setRemark("change " + opAdmin.getUsername() + "'s password failed.");
                responseDTO.addMessage(new ResponseMessage("000000007", "your old password wrong"));
                responseDTO.setStatus(ResponseStatus.FAIL);
            }
        } else {
            responseDTO.addMessage(new ResponseMessage("000000008", "your params are wrong"));
            responseDTO.setStatus(ResponseStatus.FAIL);
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return responseDTO;
    }

    @RequestMapping(value = "/user/addopadmin", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseDTO<String> addOpAdmin(HttpServletRequest request, @RequestBody ClientOpAdmin clientOpAdmin) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("B");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        OpAdmin opAdmin = opAdminService.getOpAdmin(clientOpAdmin.getUsername());
        if (opAdmin != null) {
            responseDTO.addMessage(new ResponseMessage("000000008", "your username has exited."));
            responseDTO.setStatus(ResponseStatus.FAIL);
            log.setRemark("add new opadmin:" + opAdmin.getUsername() + " failed.");
        } else if (clientOpAdmin.getRole().isEmpty() || clientOpAdmin.getPassword().isEmpty()) {
            responseDTO.addMessage(new ResponseMessage("000000009", "your param is wrong."));
            responseDTO.setStatus(ResponseStatus.FAIL);
            log.setRemark("add new opadmin:" + opAdmin.getUsername() + " failed.");
        }
        else {
            OpAdmin newOpAdmin = new OpAdmin();
            newOpAdmin.setUsername(clientOpAdmin.getUsername());
            newOpAdmin.setPassword(clientOpAdmin.getPassword());
            newOpAdmin.setEmail(clientOpAdmin.getEmail());
            newOpAdmin.setMobilePhoneNo(clientOpAdmin.getMobilePhoneNo());
            newOpAdmin.setPosition(clientOpAdmin.getPosition());
            newOpAdmin.setRealName(clientOpAdmin.getRealName());
            newOpAdmin.setRemark(clientOpAdmin.getRemark());
            newOpAdmin.setRole(clientOpAdmin.getRole());

            opAdminService.insert(newOpAdmin);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("add new opadmin:" + newOpAdmin.getUsername() + " success.");
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return responseDTO;
    }

}
