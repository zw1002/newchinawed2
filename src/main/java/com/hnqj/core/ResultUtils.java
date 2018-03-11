package com.hnqj.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static com.alibaba.fastjson.JSON.*;
import static com.hnqj.core.ResultUtils.toJson;

public class ResultUtils {

    public static String toJson(Object data) {
        return toJSONString(data);
    }

    public static String toDateJson(Object data) {
        DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        return toJSONString(data, SerializerFeature.WriteDateUseDateFormat);
    }

    public static String toDateTimeJson(Object data) {
        DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm";
        return  toJSONString(data, SerializerFeature.WriteDateUseDateFormat);
    }

    public static void writeSuccess(HttpServletResponse response) {
        write(response, "success");
    }

    public static void writeFailed(HttpServletResponse response) {
        write(response, "failed");
    }

    public static void write(HttpServletResponse response, String message) {
        PrintWriter writer = null;
        try {
            response.setHeader("Charset", "UTF-8");
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            writer.write(message);
        } catch (Exception e) {

        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     * @param response
     * @param object
     */
    public static void write(HttpServletResponse response, Object object) {
        PrintWriter writer = null;
        try {
            response.setHeader("Charset", "UTF-8");
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            writer.write(toJson(object));
            // System.out.println(toJson(object));
        } catch (Exception e) {

        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
