import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Enumeration;

public class SetMusic {
    private static float voice = 1; // 音量设置，0.0到1.0之间
    private Container parentContainer; // 使用Container作为通用父组件

    public SetMusic(Container parentContainer) {
        this.parentContainer = parentContainer;
        setButtonBehavior(parentContainer); // 传入 parentContainer 作为容器
    }

    private void setButtonBehavior(Container container) {
        if (container != null) {
            Component[] components = container.getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    // 直接添加一个新的ActionListener，用于播放声音
                    button.addActionListener(getSoundActionListener());
                } else if (component instanceof Container) {
                    // 如果组件是容器，递归调用
                    setButtonBehavior((Container) component);
                }
            }
        }
    }

    private ActionListener getSoundActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    playSound("music/mcbutton.wav", false);
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                    // 记录错误信息
                    ex.printStackTrace(); // 或者记录到日志
                }
            }
        };
    }

    public void playSound(String filePath, boolean loop) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        // 使用SwingWorker确保音频播放在非EDT线程中
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    File audioFile = new File(filePath);
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(audioFile));

                    // 设置音量
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(voice);

                    clip.start(); // 开始播放音乐
                    if (loop) {
                        clip.loop(Clip.LOOP_CONTINUOUSLY); // 设置音乐循环播放
                    }

                    // 如果音量在播放过程中被改变，需要更新音量
                    while (clip.isActive() && loop) {
                        gainControl.setValue(voice);
                        Thread.sleep(500); // 检查音量更新的频率，可以根据需要调整
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }


    public void setVoice(float newVoice) {
        voice = newVoice;
    }
    public float getVoice(){
        return voice;
    }

    public static void playMusic(String filePath, boolean loop) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        try {
            File audioFile = new File(filePath);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audioFile));
            clip.start(); // 开始播放音乐
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // 设置音乐循环播放
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
