package com.foolxing.mall.controller;
import com.foolxing.mall.util.Result;
import com.foolxing.mall.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UploadController {

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {
        //06baaff712554d4dabcd0a60933c1661
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //a.png
        String filename = file.getOriginalFilename();
        //.png
        String extension = filename.substring(filename.lastIndexOf("."));
        //06baaff712554d4dabcd0a60933c1661.png
        String newFilename = uuid + extension;
        String filePath = "D:\\mypic\\" + newFilename;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.ok("上传成功", newFilename);
    }
}
