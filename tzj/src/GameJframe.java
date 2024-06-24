import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameJframe {

    public static void main(String[] args) {
        // 在Swing组件中显示分割后的图片
        JFrame frame = new JFrame("Image Splitter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 加载图片
        String imagePath = "picture/2.jpg";
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("读取图像文件时出错：" + e.getMessage());
            return; // 退出程序或适当地处理错误
        }

        if (image == null) {
            System.err.println("图像无法读取或不是有效的图像文件。");
            return; // 退出程序或适当地处理错误
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int Wwidth=frame.getWidth();
        int Wheight=frame.getHeight();
        // 创建一个ImageIcon数组，包含3x3个ImageIcon
        ImageIcon[][] icons = new ImageIcon[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BufferedImage iconImage = image.getSubimage(i * image.getWidth() / 3, j * image.getHeight() / 3,
                        image.getWidth() / 3, image.getHeight() / 3);
                ImageIcon icon = new ImageIcon(iconImage);
                icons[i][j] = icon;
            }
        }



        // 设置一个面板来包含所有的ImageIcon
        JPanel panel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JLabel label = new JLabel(icons[j][i]);
                panel.add(label);
            }
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
