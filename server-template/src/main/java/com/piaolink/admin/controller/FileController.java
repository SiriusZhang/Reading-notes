package com.piaolink.admin.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.piaolink.admin.dto.CosResponse;
import com.piaolink.common.dto.ResponseDTO;
import com.piaolink.common.dto.ResponseMessage;
import com.piaolink.common.dto.ResponseStatus;
import com.piaolink.common.utils.CodecUtils;
import com.piaolink.common.utils.DateTimeUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by bill on 2017-4-26.
 */
@Controller
@RequestMapping("file")
public class FileController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${ftp.hostname}")
    private String hostname;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String userName;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.path}")
    private String path;

    @RequestMapping("/fileUpload")
    @ResponseBody
    public ResponseDTO<String> fileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam String userName, @RequestParam String password) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> fileNameList = multiRequest.getFileNames();
                while (fileNameList.hasNext()) {
                    long startTime = System.currentTimeMillis();
                    MultipartFile file = multiRequest.getFile(fileNameList.next());
                    if (file != null) {
                        String fileName = file.getOriginalFilename();
                        if (StringUtils.isNotEmpty(fileName)) {
                            logger.info("Upload file: {}", fileName);
                            String prefix = fileName.substring(fileName.lastIndexOf("."));
                            String uploadFileName = DateTimeUtils.getCurrentDateString() + "_" + CodecUtils.getUUID()
                                    + prefix;

                            String localFileFullName = request.getSession().getServletContext().getRealPath(uploadFileName);
                            File localFile = new File(localFileFullName);
                            file.transferTo(localFile);

                            long appId = 1253336138 ;
                            String secretId = "AKIDrb9VgfT2RefU7ZrvpZ7WmphNrrJEBf1D";
                            String secretKey = "84pOlvzwzY0QhmNmzzpUyHOrDNLhxJUU";
                            String bucketName = "piaolink";

                            Credentials cred = new Credentials(appId, secretId, secretKey);
                            ClientConfig clientConfig = new ClientConfig();
                            clientConfig.setRegion("gz");

                            COSClient cosClient = new COSClient(clientConfig, cred);
                            String cosFileName = "/" + uploadFileName;
                            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosFileName, localFileFullName);
                            String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
                            CosResponse cosResponse = (CosResponse)JSONUtils.parse(uploadFileName);
                            if (cosResponse.getCode() == 0) {
                                responseDTO.setStatus(ResponseStatus.SUCCESS);
                                responseDTO.setData(cosResponse.getData().getAccess_url());
                            } else {
                                responseDTO.setStatus(ResponseStatus.FAIL);
                                String message = "upload to tencent cos failed.";
                                responseDTO.addMessage(new ResponseMessage("000000010", message));
                                logger.error(message);
                            }
                            logger.info(uploadFileRet);
                            cosClient.shutdown();
                            localFile.delete();
                        }
                    }

                    long endTime = System.currentTimeMillis();
                    logger.info("{} ms", endTime - startTime);
                }
            }

        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAIL);
            logger.error("File upload error", e);
        }
        return responseDTO;
    }

    @RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
    public void fileDownload(HttpServletResponse response, @RequestParam("fileName") String fileName) {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("multipart/form-data");

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.connect(hostname, port);
            ftpClient.login(userName, password);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return;
            }
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(path);
            ftpClient.enterLocalPassiveMode();
            FTPFile[] fs = ftpClient.listFiles();
            for (int i = 0; i < fs.length; i++) {
                if (fs[i].getName().equals(fileName)) {
                    String saveAsFileName = new String(fs[i].getName().getBytes("UTF-8"), "ISO8859-1");
                    response.setHeader("Content-Disposition", "attachment;fileName=" + saveAsFileName);
                    OutputStream os = response.getOutputStream();
                    ftpClient.retrieveFile(fs[i].getName(), os);
                    os.flush();
                    os.close();
                    break;
                }
            }
            ftpClient.logout();
        } catch (Exception e) {
            logger.error("FTP Exception", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    logger.error("FTP Exception", e);
                }
            }
        }
    }
}
