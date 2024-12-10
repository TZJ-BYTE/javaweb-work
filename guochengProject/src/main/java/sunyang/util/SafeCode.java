package sunyang.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.File;


public class SafeCode extends HttpServlet {
    public SafeCode() {
    }

    public void init() throws ServletException {
        super.init();
    }


    /**
     * 处理HTTP GET请求以生成并返回一个包含验证码图片的响应
     * 此方法设置响应内容类型为图像JPEG，并通过各种手段防止客户端缓存该图像
     * 它还负责生成一个包含四位随机字符的图像，并将这些字符作为会话属性存储，
     * 以便后续验证用户输入的验证码是否正确
     *
     * @param request 代表HTTP请求的HttpServletRequest对象，用于访问请求信息
     * @param response 代表HTTP响应的HttpServletResponse对象，用于设置响应属性和发送响应体
     * @throws ServletException 如果Servlet遇到异常
     * @throws IOException 如果发生输入或输出异常
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应内容类型为图像JPEG
        response.setContentType("image/jpeg");
        // 以下三行设置响应头，以防止客户端缓存该图像
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0L);

        // 定义验证码图片的宽度和高度
        int width = 60;
        int height = 20;
        // 创建一个BufferedImage对象，用于生成验证码图片
        BufferedImage image = new BufferedImage(width, height, 1);
        // 获取Graphics对象，用于在图像上绘制图形和文字
        Graphics g = image.getGraphics();
        // 创建一个Random对象，用于生成随机数
        Random random = new Random();

        // 设置背景色为一个随机的浅色
        g.setColor(getRandColor(200, 250));
        // 填充整个图像区域为背景色
        g.fillRect(0, 0, width, height);
        // 设置字体为Arial，样式为普通，大小为19pt
        g.setFont(new Font("Arial", 0, 19));
        // 设置前景色为一个随机的深色
        g.setColor(getRandColor(160, 200));

        // 初始化随机字符串
        String sRand = "";
        // 循环四次，每次生成一个随机字符，并将其绘制在图像上
        for (int i = 0; i < 4; i++) {
            // 生成一个随机字符
            String rand = getRandChar(random.nextInt(36));
            // 将生成的随机字符追加到字符串sRand中
            sRand = sRand + rand;
            // 设置绘制字符的颜色为一个随机的深色
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            // 在图像上绘制随机字符
            g.drawString(rand, 13 * i + 6, 16);
        }

        // 将生成的随机字符串作为会话属性存储，键为"rand"
        request.getSession().setAttribute("rand", sRand);
        // 释放Graphics对象
        g.dispose();
        // 获取响应的输出流，用于发送图像数据
        javax.servlet.ServletOutputStream imageOut = response.getOutputStream();
        // 创建一个JPEGImageEncoder对象，用于将BufferedImage对象编码为JPEG格式
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(imageOut);
        // 使用JPEGImageEncoder对象将图像编码到输出流中
        encoder.encode(image);
    }
    public void destroy() {
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private String getRandChar(int randNumber) {
        return CHARARRAY[randNumber];
    }

    private static final String CHARARRAY[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i",
            "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z"
    };

}