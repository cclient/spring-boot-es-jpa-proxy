package com.example.controller;

import com.example.common.dict.Mydict;
import com.example.service.sql.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

//import jdk.management.resource.ResourceRequest;

/**
 * ik elasticsearch plugin 插件 配置动态词典更新需要接口
 * 基础本建议用nginx，因为要作成动态的，每次更新nginx静态目录太费事
 */
@Controller
@RequestMapping("/dict")

public class Dict {
    @Autowired
    private CommunityService communityService;
    @Autowired
    private Mydict mydict;

    @RequestMapping(value = "/custom.dic")
    void custom(HttpServletResponse response) {
        try {
            response.setContentType("application/text");
            List<String> dics = mydict.LoadDictFromFile();
            ServletOutputStream stream = response.getOutputStream();
            Writer writer = new BufferedWriter(new OutputStreamWriter(stream));
            response.addHeader("Last-Modified", "");
            response.addHeader("ETag", "");
            for (String s : dics) {
                writer.append(s + "\n");
            }
            writer.flush();
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
