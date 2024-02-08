package com.notice.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ExceptionUtil {

    public static String printStackTrace(Exception e){
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        printWriter.close();
        return writer.toString();
    }
}

