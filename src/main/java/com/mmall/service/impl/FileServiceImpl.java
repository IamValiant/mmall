package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Xueyong on 2018/5/21.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        //例如文件名为abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);

        //A:abc.jpg
        //B:abc.jpg(传的同名文件)
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功了

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //将targetFile上传到我们的ftp服务器上（此处是否删除关系不大）

            targetFile.delete();
            // 上传完之后，删除upload下面的文件（tomcat服务器下，文件要删除，文件夹可以留着）

        } catch (IOException e) {
            logger.error("上传文件异常",e);
        }

        return targetFile.getName();
    }


}
