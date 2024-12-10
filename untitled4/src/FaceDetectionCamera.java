import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.highgui.HighGui;

public class FaceDetectionCamera {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        FaceDetector faceDetector = new FaceDetector("F:/ubuntu/opencv/build/etc/haarcascades/haarcascade_frontalface_alt.xml");

        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("无法打开摄像头");
            return;
        }

        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);

        Mat frame = new Mat();
        while (true) {
            capture.read(frame);
            if (frame.empty()) {
                System.out.println("无法从摄像头读取帧");
                break;
            }

            faceDetector.detectAndDrawFaces(frame);
            HighGui.imshow("人脸检测", frame);

            if (HighGui.waitKey(1) == 'q') {
                break;
            }
        }

        capture.release();
        HighGui.destroyAllWindows();
    }
}
