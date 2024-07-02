import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

public class GameJframe {
    private int[][] inum; // 声明二维数组
    private ImageIcon[][] icons; // 初始化icons数组
    private BufferedImage image = null;
    private String imagePath;
    private int n;


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public GameJframe(String pname,int n) {
        this.n=n;
        inum = new int[n][n]; // 初始化inum数组
        icons = new ImageIcon[n][n];
        imagePath = "picture/"+pname;

        // 加载图片
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

        try {
            icpicture(image,n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

///////////图片切割并保存////////////////////////////////////////////////////////////////////////////

    // 假设image是一个BufferedImage对象，n是要切割的块数，imageName是原始图片的文件名（不含路径）
    private void icpicture(BufferedImage image, int n) throws IOException {
        File imageFile = new File(imagePath);
        String imageName=imageFile.getName();
        // 计算每个小图片的尺寸
        int width = image.getWidth() / n;
        int height = image.getHeight() / n;

        // 创建output_images文件夹来存放小图片的文件夹
        File outputImagesFolder = new File("output_images");
        if (!outputImagesFolder.exists()) {
            outputImagesFolder.mkdirs();
        }

        // 创建与原始图片同名的文件夹
        String imageNameWithoutExtension = imageName.substring(0, imageName.lastIndexOf('.'));
        File outputFolder = new File(outputImagesFolder, imageNameWithoutExtension+"_"+n);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        // 切割图片并保存到文件夹中
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 获取子图像
                BufferedImage subImage = image.getSubimage(j * width, i * height, width, height);

                // 创建文件名并保存图像
                File outputFile = new File(outputFolder, "image_" + i + "_" + j + ".jpg");
                ImageIO.write(subImage, "jpg", outputFile);
            }
        }

        // 指定图像文件路径
        String imagePath = "output_images/0_0.jpg";
        // 使用ImageIO读取图像文件
        BufferedImage subImage = ImageIO.read(new File(imagePath));
        File outputFile = new File(outputFolder, "image_" + 0 + "_" + 0 + ".jpg");
        ImageIO.write(subImage, "jpg", outputFile);

    }


}
