package org.example.LiveSystem.controller;

import io.swagger.annotations.Api;
import org.example.LiveSystem.annotations.TokenUser;
import org.example.LiveSystem.common.BaseController;
import org.example.LiveSystem.common.util.ResultUtil;
import org.example.LiveSystem.dao.UserDao;
import org.example.LiveSystem.dao.UserGroupsDao;
import org.example.LiveSystem.dto.Result;
import org.example.LiveSystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Date;
import java.util.UUID;

@Api(tags = "文件传输API")
@RestController
@Validated
public class FileController extends BaseController {

    /**
     * 保存文件的根地址(C:\\Users\\xx.png)
     */
    private String filePath = "D:\\";

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserGroupsDao userGroupsDao;

    @RequestMapping("/api/files/saveFile/")
    public Result<String> saveFile(@RequestParam(value = "file") MultipartFile file,
                                   @RequestParam(value = "GroipID") String GroipID,
                                   @TokenUser User user) {
        if(user == null) {
            return ResultUtil.fail("非法用户");
        }
        try {
            //目录地址,filePath根地址+用户账号
            String uploadDir = filePath+user.getULoginid();
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //调用上传方法
            String filename = executeUpload(uploadDir, file);
            if(GroipID.isEmpty()) {
                userDao.setUserIcon(filename, user.getUId());
            } else {
                userGroupsDao.setGroupIcon(filename, Integer.valueOf(GroipID));
            }
        } catch (Exception e) {
            //打印错误堆栈信息
            e.printStackTrace();
            return ResultUtil.fail("上传失败");
        }
        return ResultUtil.success();
    }

    private String executeUpload(String uploadDir, MultipartFile file) throws Exception {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filename = UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + filename);

        if(!serverFile.exists()) {
            //先得到文件的上级目录，并创建上级目录，在创建文件
            serverFile.getParentFile().mkdir();
            try {
                //创建文件
                serverFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);

        return filename;
    }

    @GetMapping("/api/files/getImg/")
    public ResponseEntity<FileSystemResource> getImg(@TokenUser User user,
                       @RequestParam String fileLocation) {
        if(user == null) {
            return null;
        }
        File file = new File(filePath+fileLocation);
        if (file.exists()) {
            return export(file);
        }
        System.out.println(file);
        return null;
    }

    public ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }
}
