package com.mrli.school_book.util;

import com.alibaba.fastjson.JSONObject;
import com.mrli.school_book.constant.ResultCodeEnum;
import com.mrli.school_book.vo.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {

    /**
     * ajax 返回
     * @param response
     * @param r
     */
    public static void ajaxResponse(HttpServletResponse response , R r) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        response.getWriter().write(JSONObject.toJSONString(r));
        printWriter.flush();
    }

}
