package org.example.apq;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

public class MyLogin extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer sb=new StringBuffer();
        sb.append(req.getMethod()+" "+req.getRequestURI()+"<br>");
        Enumeration<String> e= req.getHeaderNames();
        while (e.hasMoreElements()) {
            String name=e.nextElement();
            sb.append(name + ": " + req.getHeader(name) + "<br>");
            sb.append("<br>");
        }
        sb.append("<hr>");
        InputStream in=req.getInputStream();
        byte[] bs=new byte[1024];
        int len=0;
        while((len=in.read(bs))>0){
            sb.append(new String(bs,0,len));
        }
        sb.append("<hr>");

        Enumeration<String> e2=req.getParameterNames();
        while (e2.hasMoreElements()) {
            String name=e2.nextElement();
            String val=req.getParameter(name);
            sb.append(name + ": " + val + "<br>");
        }

        resp.setContentType("text/html");
        PrintWriter out=resp.getWriter();
        out.println(sb.toString());
        out.flush();

    }
}