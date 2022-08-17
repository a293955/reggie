package com.xs.controller;

import com.xs.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     *文件上传
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是临时文件
        log.info(file.toString());
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名
        String filename = UUID.randomUUID() + suffix;
        //创建目录对象
        File dir = new File(basePath);
        //当前目录是否存在
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath + filename));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            //通过输入流读取图片内容
            FileInputStream fis = new FileInputStream(basePath + name);
            //通过输出流将文件写回到浏览器
            ServletOutputStream sos = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];

            while ((len = fis.read(bytes)) != -1) {
                sos.write(bytes, 0, len);
                sos.flush();
            }

            sos.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
