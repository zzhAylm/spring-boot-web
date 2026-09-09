package com.zzh.ai.test;

import freemarker.template.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerExample {
    public static void main(String[] args) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        // 模板加载路径（假设放在 classpath 下 templates 文件夹）
        cfg.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), "");
        cfg.setDefaultEncoding("UTF-8");

        Template template = cfg.getTemplate("static/hello.ftl");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("user", "ChatGPT");

        StringWriter out = new StringWriter();
        template.process(dataModel, out);

        System.out.println(out.toString());
    }
}
