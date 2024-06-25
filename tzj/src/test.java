import javax.swing.*;
import java.awt.event.KeyListener;

public class test extends JFrame {
      public test() {
             initJFrame();
      }


       private void initJFrame(){
              this.setSize(700,700);
              this.setTitle("拼图");
              this.setAlwaysOnTop(true);
              this.setLocationRelativeTo(null);
              this.setDefaultCloseOperation(2);
              this.setLayout(null);
              this.addKeyListener((KeyListener) this);
              this.setVisible(true);
       }
}