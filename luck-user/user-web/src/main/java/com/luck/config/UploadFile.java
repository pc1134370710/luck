package com.luck.config;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class UploadFile {

    /**
     * 文件上传
     * @param newName 新名称
     * @param path 磁盘路径
     * @param url 虚拟路径
     * @param file  文件
     * @return  虚拟路径
     * @throws IOException
     */
    public static String upload(String newName, String path,String url, MultipartFile file) throws IOException {
        // 获取 原来的名称
        String name = file.getOriginalFilename();
        // 新名称  + 旧名称  生产全新的名称
        newName+=name;
        File files = new File(path+newName);
        if(!files.exists()){
            files.mkdirs();
        }
        file.transferTo(files);
        return url+newName;
    }

    /**
     * MultipartFile转File
     */
    public static File toFile(MultipartFile multipartFile){
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        File file = null;
        try {
            // 用uuid作为文件名，防止生成的临时文件重复
            file = File.createTempFile(UUID.randomUUID().toString(), prefix);
            // MultipartFile to File
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    /**
     * 文件下载
     * @param request  请求流
     * @param response 响应流
     * @param file
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file,
                                   String filename){
        // 设置响应编码
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename="+filename+".pdf");
            IOUtils.copy(fis,response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
