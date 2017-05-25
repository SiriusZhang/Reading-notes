package com.piaolink.admin.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.piaolink.admin.dto.ApiParam;
import com.piaolink.admin.dto.ClientValidation;
import com.piaolink.admin.dto.FirstExamine;
import com.piaolink.admin.dto.SecondExamine;
import com.piaolink.admin.entity.OpAdmin;
import com.piaolink.admin.entity.OpAuditLog;
import com.piaolink.admin.entity.User;
import com.piaolink.admin.service.OpAuditLogService;
import com.piaolink.admin.service.TaskJobService;
import com.piaolink.admin.service.UserService;
import com.piaolink.common.dto.ResponseDTO;
import com.piaolink.common.dto.ResponseMessage;
import com.piaolink.common.dto.ResponseStatus;
import com.piaolink.common.utils.DateTimeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.channels.SelectableChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequestMapping("user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("com.piaolink.admin.service.UserService")
    UserService userService;

    @Autowired
    @Qualifier("com.piaolink.admin.service.OpAuditLogService")
    OpAuditLogService opAuditLogService;

    @Autowired
    @Qualifier("com.piaolink.admin.service.TaskJobService")
    TaskJobService TaskJobSerice;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<Serializable, Serializable> redisTemplate;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<List> getUsers(HttpServletRequest request) {
        ResponseDTO<List> responseDTO = new ResponseDTO<>();
        List<User> userList = null;

        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        try {
            userList = userService.getUsers();
            for (User user:userList) {
                user.setPassword("");
            }
            if (userList != null) {
                log.setRemark("select all users success.");
                responseDTO.setData(userList);
                responseDTO.setStatus(ResponseStatus.SUCCESS);
            } else {
                log.setRemark("select all users failed.");
                responseDTO.addMessage(new ResponseMessage("000000002",
                        "there are not any user info."));
                responseDTO.setStatus(ResponseStatus.FAIL);
            }
        } catch (Exception e) {
            responseDTO.addMessage(new ResponseMessage("000000003",
                    "there is something wrong with db."));
            responseDTO.setStatus(ResponseStatus.FAIL);
        }

        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return responseDTO;
    }

    @RequestMapping(value = "/usersPage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<PageInfo<User>> getUsersPage(HttpServletRequest request,
                                                    @RequestParam("num") int currentPage,
                                                    @RequestParam("size") int pageSize) {
        ResponseDTO<PageInfo<User>> responseDTO = new ResponseDTO<>();
        PageInfo<User> userPageInfo = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        try {
            PageHelper.startPage(currentPage, pageSize);
            userPageInfo = new PageInfo<>(userService.getUsers());
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select users page success.");
        } catch (Exception e) {
            responseDTO.addMessage(new ResponseMessage("000000003",
                    "there is something wrong with db.") );
            responseDTO.setStatus(ResponseStatus.FAIL);
            log.setRemark("select users page failed.");
        } finally {
            PageHelper.clearPage();
        }

        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        responseDTO.setData(userPageInfo);
        return responseDTO;
    }

    @RequestMapping(value = "/likeIdUsersPage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<PageInfo<User>> getUsersLikeIdPage(HttpServletRequest request,
                                                          @RequestParam("id") String id,
                                                          @RequestParam("num") int currentPage,
                                                          @RequestParam("size") int pageSize) {
        ResponseDTO<PageInfo<User>> responseDTO = new ResponseDTO<>();
        PageInfo<User> userPageInfo = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        try {
            PageHelper.startPage(currentPage, pageSize);
            userPageInfo = new PageInfo<>(userService.getUserByLikeId(id));
            if (userPageInfo != null) {
                PageHelper.startPage(currentPage, pageSize);
                responseDTO.setStatus(ResponseStatus.SUCCESS);
                log.setRemark("likeIdUsersPage " + id + " success.");
            } else {
                responseDTO.addMessage(new ResponseMessage("000000005",
                        "there are no user info."));
                responseDTO.setStatus(ResponseStatus.FAIL);
                log.setRemark("likeIdUsersPage " + id + " failed.");
            }
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
        } finally {
            PageHelper.clearPage();
        }

        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        responseDTO.setData(userPageInfo);
        return responseDTO;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<User> getUserById(HttpServletRequest request,
                                         @PathVariable String id) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        if (id.isEmpty()) {
            responseDTO.addMessage(new ResponseMessage("000000004",
                    "user id couldn't be null"));
            responseDTO.setStatus(ResponseStatus.FAIL);
            return responseDTO;
        }

        User user = userService.getUser(id);
        if (user != null) {
            responseDTO.setData(user);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("getUserById id=" + id + " success.");
        } else {
            responseDTO.addMessage(new ResponseMessage("000000005",
                    "this id is invalid"));
            responseDTO.setStatus(ResponseStatus.FAIL);
            log.setRemark("getUserById error this id:" + id +" is invalid");
        }

        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return responseDTO;
    }


    @RequestMapping(value = "/byValidationUsersPage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<PageInfo<User>> getUsersByValidation(HttpServletRequest request,
                                                            @RequestParam("validation") String validation,
                                                            @RequestParam("num") int currentPage,
                                                            @RequestParam("size") int pageSize) {
        ResponseDTO<PageInfo<User>> responseDTO = new ResponseDTO<>();
        PageInfo<User> userPageInfo = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        try {
            PageHelper.startPage(currentPage, pageSize);
            userPageInfo = new PageInfo<>(userService.getUserByValidation(validation));
            responseDTO.setData(userPageInfo);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select byValidationUsersPage validation:" + validation + " success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select byValidationUsersPage validation:" + validation + " failed.");
        } finally {
            PageHelper.clearPage();
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/firstExamine", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseDTO<String> firstExamineUser(HttpServletRequest request, @RequestBody FirstExamine clientExamine) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        User user = userService.getUser(clientExamine.getUserid());
        //S成功 F失败 N待审核 O审核中 A客服初审通过 B客服初审失败
        if (user != null && user.getValidationResult().equals("O")) {
            int result = 0;
            OpAuditLog log = new OpAuditLog();
            log.setUserId(user.getUserId());
            log.setOperationTime(new Date());
            log.setOperationType("D");
            OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
            if (client != null) {
                log.setAdminId(client.getId());
            }

            if (clientExamine.getExamine().equals("pass")) {
                result = userService.setUserValidationResult(clientExamine.getUserid(), "A");
                log.setRemark("O -> A");
            } else {
                result = userService.setUserValidationResult(clientExamine.getUserid(), "B");
                log.setRemark("O -> B");
            }

            if (result > 0) {
                opAuditLogService.insertOpAuditLog(log);
                responseDTO.setStatus(ResponseStatus.SUCCESS);
            } else {
                responseDTO.addMessage(new ResponseMessage("000000003",
                        "there is something wrong with db."));
                responseDTO.setStatus(ResponseStatus.FAIL);
            }
        } else {
            responseDTO.addMessage(new ResponseMessage("000000005",
                    "this id is invalid"));
            responseDTO.setStatus(ResponseStatus.FAIL);
        }
        return  responseDTO;
    }

    @RequestMapping(value = "/secondExamine", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    @Transactional
    public ResponseDTO<String> secondExamineUser(HttpServletRequest request, @RequestBody SecondExamine examine) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        OpAuditLog log = new OpAuditLog();
        log.setOperationTime(new Date());
        log.setOperationType("D");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        //S成功 F失败 N待审核 O审核中 A客服初审通过 B客服初审失败
        if (examine.getAction().equals("AtoS")) {
            List<User> users = userService.getUserByValidation("A");
            log.setRemark("A -> S");

            for (User user: users) {
                log.setUserId(user.getUserId());
                log.setOperationTime(new Date());
                opAuditLogService.insertOpAuditLog(log);

                int result = userService.setUserValidationResult(user.getUserId(), "S");
                if (users.size() == 0)
                    responseDTO.setStatus(ResponseStatus.SUCCESS);
                if (result > 0) {
                    responseDTO.setStatus(ResponseStatus.SUCCESS);
                } else {
                    responseDTO.addMessage(new ResponseMessage("000000003",
                            "there is something wrong with db."));
                    responseDTO.setStatus(ResponseStatus.FAIL);
                }
            }
        } else if(examine.getAction().equals("BtoF")) {
            List<User> users = userService.getUserByValidation("B");
            if (users.size() == 0)
                responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("B -> F");

            for (User user: users) {
                log.setOperationTime(new Date());
                log.setUserId(user.getUserId());
                opAuditLogService.insertOpAuditLog(log);

                int result = userService.setUserValidationResult(user.getUserId(), "F");
                if (result > 0) {
                    responseDTO.setStatus(ResponseStatus.SUCCESS);
                } else {
                    responseDTO.addMessage(new ResponseMessage("000000003",
                            "there is something wrong with db."));
                    responseDTO.setStatus(ResponseStatus.FAIL);
                }
            }
        }
        return responseDTO;
    }

    @RequestMapping(value = "/setValidation", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseDTO<String> setValidation(HttpServletRequest request, @RequestBody ClientValidation validation) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        User user = userService.getUser(validation.getUserid());
        String vaildStr = "NOBF";
        String vaildParam = "ON";

        if (user != null && vaildStr.contains(user.getValidationResult())
                && vaildParam.contains(validation.getValidation())) {
            OpAuditLog log = new OpAuditLog();
            log.setUserId(user.getUserId());
            log.setOperationTime(new Date());
            log.setOperationType("D");
            log.setRemark(user.getValidationResult() + " -> " + validation.getValidation());
            OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
            if (client != null) {
                log.setAdminId(client.getId());
            }
            opAuditLogService.insertOpAuditLog(log);

            int result = userService.setUserValidationResult(user.getUserId(), validation.getValidation());
            if (result > 0) {
                responseDTO.setStatus(ResponseStatus.SUCCESS);
            } else {
                responseDTO.addMessage(new ResponseMessage("000000003",
                        "there is something wrong with db."));
                responseDTO.setStatus(ResponseStatus.FAIL);
            }
        } else {
            responseDTO.addMessage(new ResponseMessage("000000005",
                    "this id is invalid"));
            responseDTO.setStatus(ResponseStatus.FAIL);
        }
        return responseDTO;
    }

    @RequestMapping(value = "/likePhoneUsersPage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<PageInfo<User>> getUsersLikePhone(HttpServletRequest request,
                                                         @RequestParam("phone") String phone,
                                                         @RequestParam("num") int currentPage,
                                                         @RequestParam("size") int pageSize) {
        ResponseDTO<PageInfo<User>> responseDTO = new ResponseDTO<>();
        PageInfo<User> userPageInfo = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }
        try {
            PageHelper.startPage(currentPage, pageSize);
            userPageInfo = new PageInfo<>(userService.getUserLikePhone(phone));
            responseDTO.setData(userPageInfo);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select likePhoneUsersPage phone:" + phone + " success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select likePhoneUsersPage phone:" + phone + " failed.");
        } finally {
            PageHelper.clearPage();
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/byPhoneUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<User> getUserByPhone(HttpServletRequest request, @RequestParam("phone") String phone) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }
        try {
            User user = userService.getUserByPhone(phone);
            responseDTO.setData(user);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select getUserByPhone phone:" + phone + " success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select getUserByPhone phone:" + phone + " failed.");
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/likeNameUsersPage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<PageInfo<User>> getUsersLikeName(HttpServletRequest request,
                                                        @RequestParam("name") String name,
                                                         @RequestParam("num") int currentPage,
                                                         @RequestParam("size") int pageSize) {
        ResponseDTO<PageInfo<User>> responseDTO = new ResponseDTO<>();
        PageInfo<User> userPageInfo = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }
        try {
            PageHelper.startPage(currentPage, pageSize);
            userPageInfo = new PageInfo<>(userService.getUserLikeName(name));
            responseDTO.setData(userPageInfo);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select likeNameUsersPage name:" + name + " success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select likeNameUsersPage name:" + name + " failed." + e.getMessage());
        } finally {
            PageHelper.clearPage();
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/byNameUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<User> getUserByName(HttpServletRequest request,
                                           @RequestParam("name") String name) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }
        try {
            User user = userService.getUserByName(name);
            responseDTO.setData(user);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select byNameUser name:" + name + " success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select byNameUser name:" + name + " failed.");
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }


    @RequestMapping(value = "/likeUsernameUsersPage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<PageInfo<User>> getUsersLikeUsername(HttpServletRequest request,
                                                            @RequestParam("username") String username,
                                                        @RequestParam("num") int currentPage,
                                                        @RequestParam("size") int pageSize) {
        ResponseDTO<PageInfo<User>> responseDTO = new ResponseDTO<>();
        PageInfo<User> userPageInfo = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        try {
            PageHelper.startPage(currentPage, pageSize);
            userPageInfo = new PageInfo<>(userService.getUserLikeUsername(username));
            responseDTO.setData(userPageInfo);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select likeUsernameUsersPage username:" + username + " success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select likeUsernameUsersPage username:" + username + " failed.");
        } finally {
            PageHelper.clearPage();
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/byUsernameUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<User> getUserByUsername(HttpServletRequest request, @RequestParam("username") String username) {
        ResponseDTO<User> responseDTO = new ResponseDTO<>();
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }
        try {
            User user = userService.getUserByUsername(username);
            responseDTO.setData(user);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select byUsernameUser username:" + username + "success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select byUsernameUser username:" + username + " failed.");
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/likeCreateTimePage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<PageInfo<User>> getUsersLikeCreateTime(HttpServletRequest request,
                                                            @RequestParam("createTime") String createTime,
                                                            @RequestParam("num") int currentPage,
                                                            @RequestParam("size") int pageSize) {
        ResponseDTO<PageInfo<User>> responseDTO = new ResponseDTO<>();
        PageInfo<User> userPageInfo = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        try {
            PageHelper.startPage(currentPage, pageSize);
            userPageInfo = new PageInfo<>(userService.getUserLikeCreatetime(DateTimeUtils.stringToDate(createTime)));
            responseDTO.setData(userPageInfo);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select likeCreateTimePage createTime:" + createTime + " success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select likeCreateTimePage createTime:" + createTime + " failed.");
        } finally {
            PageHelper.clearPage();
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/byReguidPage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<PageInfo<User>> getUsersByReguid(HttpServletRequest request,
                                                              @RequestParam("reguid") String reguid,
                                                              @RequestParam("num") int currentPage,
                                                              @RequestParam("size") int pageSize) {
        ResponseDTO<PageInfo<User>> responseDTO = new ResponseDTO<>();
        PageInfo<User> userPageInfo = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        try {
            PageHelper.startPage(currentPage, pageSize);
            userPageInfo = new PageInfo<>(userService.getUserByReguid(reguid));
            responseDTO.setData(userPageInfo);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setRemark("select byReguidPage reguid:" + reguid + " success.");
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            responseDTO.addMessage(new ResponseMessage("000000006",
                    "Can't get user information."));
            log.setRemark("select byReguidPage reguid:" + reguid + " failed.");
        } finally {
            PageHelper.clearPage();
        }
        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/startRecommendRelationshipJob", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO<String> startRecommendRelationshipJob(HttpServletRequest request) {
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        if (client != null) {
            log.setAdminId(client.getId());
            logger.info(client.getUsername() + " have started a recommendRelationshipJob");
        }

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                TaskJobSerice.recommendRelationshipJob();
            }
        }, 10);

        responseDTO.setStatus(ResponseStatus.SUCCESS);
        responseDTO.setData("you have started a recommendRelationshipJob");

        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  responseDTO;
    }

    @RequestMapping(value = "/downloadfrom", method = RequestMethod.GET)
    @ResponseBody
    public byte [] downloadfrom(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam("validation") String validation) {
        List<User> userList = null;
        String csv = null;
        byte [] stream = null;
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        try {
            userList = userService.getUserByValidation(validation);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fielname = "user-" + validation + "-from" + df.format(new Date())+".csv";
            response.setContentType("text/html;charset=gbk");
            response.setHeader("Content-disposition", "attachment;filename=" + fielname );

            for (User user: userList) {
                user.setPassword("");
                user.setPicture("");
                user.setBussinessLicenseUrl("");
            }

            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper.schemaFor(User.class).withHeader();
            csv = csvMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS)
                    .writer(csvSchema).writeValueAsString(userList);
            stream = csv.getBytes("GBK");
            log.setRemark("download from success.");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            log.setRemark("download from faild.");
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            log.setRemark("download from faild.");
        }

        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return  stream;
    }

    @RequestMapping(value = "/getCompanyInfo", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseDTO<String> getCompanyInfo(HttpServletRequest request, @RequestBody final ApiParam param){
        OpAuditLog log = new OpAuditLog();
        log.setOperationType("C");
        OpAdmin client = (OpAdmin)request.getSession().getAttribute("user");
        if (client != null) {
            log.setAdminId(client.getId());
        }

        String redisCache = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisTemplate.getStringSerializer().deserialize(
                        redisConnection.get(redisTemplate.getStringSerializer().serialize(param.getEntName()))
                );
            }
        });

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (redisCache != null && !redisCache.isEmpty()) {
            responseDTO.setData(redisCache);
            log.setRemark("post api get company:"  + param.getEntName() + " from cache info success.");
            responseDTO.setStatus(ResponseStatus.SUCCESS);
            log.setOperationTime(new Date());
            opAuditLogService.insertOpAuditLog(log);
            return responseDTO;
        }

        String url = "http://api.chinadatapay.com/government/economic/321?key=cc155bd4b2413702945f0e044e553a22&entName="
                + param.getEntName();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        try {
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                final String result = EntityUtils.toString(entity, "UTF-8");
                if (result.contains("\"code\":\"10000\"")) {
                    redisTemplate.execute(new RedisCallback<Boolean>() {
                        @Override
                        public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                             redisConnection.set(redisTemplate.getStringSerializer().serialize(param.getEntName()),
                                    redisTemplate.getStringSerializer().serialize(result));
                             return  true;
                        }
                    });
                    responseDTO.setData(result);
                    log.setRemark("post api get company:"  + param.getEntName() + " info success.");
                    responseDTO.setStatus(ResponseStatus.SUCCESS);
                } else {
                    responseDTO.setData(result);
                    log.setRemark("post api get company:"  + param.getEntName() + " info failed.");
                    responseDTO.setStatus(ResponseStatus.FAIL);
                }
            }finally {
                response.close();
            }
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            log.setRemark("post api get company:" + param.getEntName() + " info failed.");
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
            }
        }

        log.setOperationTime(new Date());
        opAuditLogService.insertOpAuditLog(log);
        return responseDTO;
    }
}
