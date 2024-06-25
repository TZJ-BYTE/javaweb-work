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
    private String imagePath = "picture/b.jpg";
    private int n;
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public GameJframe(String pname,int n) {
        this.n=n;
        inum = new int[n][n]; // 初始化inum数组
        icons = new ImageIcon[n][n];
        imagePath = "picture/"+pname+".jpg";

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

        initializeGame();
    }

    private void initializeGame() {
        // 如果需要，在这里创建并显示另一个窗口
        SwingUtilities.invokeLater(() -> {
            // 创建并显示新的GameJframe实例
            game();
        });

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
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void game() {
        JFrame frame = new JFrame("Image Splitter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 获取屏幕尺寸
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenSize = ge.getMaximumWindowBounds();

        // 设置窗口初始大小
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setVisible(true);

        // 使用JLabel显示图片
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label, BorderLayout.CENTER);

        // 添加ComponentListener监听器，以监听窗口大小变化
        BufferedImage finalImage = image;
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // 获取当前窗口大小
                Dimension size = frame.getSize();
                // 在后台线程中缩放图像
                new ImageScaler(label, finalImage, size).execute();
            }
        });

        // 初始化时调整图片大小
        new ImageScaler(label, image, frame.getSize()).execute();

        frame.setVisible(true);
    }

    // SwingWorker用于异步缩放图像
    class ImageScaler extends SwingWorker<ImageIcon, Void> {
        private JLabel label;
        private BufferedImage image;
        private Dimension size;

        public ImageScaler(JLabel label, BufferedImage image, Dimension size) {
            this.label = label;
            this.image = image;
            this.size = size;
        }

        @Override
        protected ImageIcon doInBackground() throws Exception {
            // 计算等比例缩放的大小
            double scaleFactorWidth = (double) size.width / image.getWidth();
            double scaleFactorHeight = (double) size.height / image.getHeight();
            double scaleFactor = Math.min(scaleFactorWidth, scaleFactorHeight);

            // 根据宽高比例选择合适的缩放因子
            if (scaleFactorWidth > scaleFactorHeight) {
                // 高度过窄，以高为标准
                scaleFactor = scaleFactorHeight;
            } else {
                // 宽度过窄，以宽为标准
                scaleFactor = scaleFactorWidth;
            }

            int newWidth = (int) (image.getWidth() * scaleFactor);
            int newHeight = (int) (image.getHeight() * scaleFactor);

            // 在后台线程中缩放图像
            return new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        }

        @Override
        protected void done() {
            try {
                // 更新UI
                label.setIcon(get());
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////
}
