import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

// FaceDetector类
public class FaceDetector {
    private CascadeClassifier faceDetector;

    public FaceDetector(String classifierPath) {
        faceDetector = new CascadeClassifier();
        if (!faceDetector.load(classifierPath)) {
            System.out.println("无法加载人脸检测模型");
            System.exit(0);
        }
    }

    public boolean detectFace(Mat frame) {
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(frame, faceDetections);
        return faceDetections.toArray().length > 0;
    }
}

// VideoProvider类
class VideoProvider {
    private VideoCapture capture;

    public VideoProvider(String videoPath) {
        capture = new VideoCapture(videoPath);
        if (!capture.isOpened()) {
            System.out.println("无法打开视频文件");
            System.exit(0);
        }
    }

    public boolean readFrame(Mat frame) {
        return capture.read(frame);
    }

    public void release() {
        capture.release();
    }
}

// 主类
public class FaceDetectionApp {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        String classifierPath = "F:/ubuntu/opencv/build/etc/haarcascades/haarcascade_frontalface_alt.xml";
        String videoPath = "path_to_video_file"; // 替换为视频文件的路径

        FaceDetector faceDetector = new FaceDetector(classifierPath);
        VideoProvider videoProvider = new VideoProvider(videoPath);

        Mat frame = new Mat();
        while (videoProvider.readFrame(frame)) {
            if (frame.empty()) {
                System.out.println("无法从视频文件读取帧");
                break;
            }

            boolean faceDetected = faceDetector.detectFace(frame);
            if (faceDetected) {
                System.out.println("检测到人脸！");
            } else {
                System.out.println("没有人脸");
            }

            // 这里可以添加代码来显示帧或进行其他操作

            // 按 'q' 退出
            HighGui Highgui = null;
            if (Highgui.waitKey(1) == 'q') {
                break;
            }
        }

        videoProvider.release();
    }
}
