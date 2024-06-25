

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.util.Random;

public class PuzzleGame extends JFrame{
    int[][] data = new int[4][4];
    int x = 0;
    int y = 0;
    int[][] win = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    int step = 0;
    JMenuItem replay = new JMenuItem("重新游戏");
    JMenuItem close = new JMenuItem("退出");
    JMenuItem account = new JMenuItem("关于");
    JMenuItem relogin = new JMenuItem("重新登陆");


    public void PuzzleGame() {
            initJFrame();
            initJMenuBar();
            initData();
            initImage();
            this.setVisible(true);
    }
    private void initData(){
        int[]arr={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r = new Random();
        for(int i=0;i<arr.length;i++){
            int idex = r.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[idex];
            arr[idex] = temp;
        }
    }
    private void initImage(){
        this.getContentPane().removeAll();
        if(victory()){
            JLabel winJLabel = new JLabel(new ImageIcon("picture/win.jpg"));
            winJLabel.setBounds(203,283,197,73);
            this.getContentPane().add(winJLabel);
        }
        JLabel stepCount= new JLabel("步数"+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                int num=data[i][j];
                JLabel jLabel = new JLabel(new ImageIcon("picture/"+num+".png"));
                jLabel.setBounds(105*j+90,105*i+130,105,105);
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                this.getContentPane().add(jLabel);
            }
        }
        JLabel backgroung = new JLabel(new ImageIcon("picture/back.png"));
        backgroung.setBounds(50,40,508,560);
        this.getContentPane().add(backgroung);
        this.getContentPane().repaint();
    }
    private void initJMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu=new JMenu("帮助");
        functionJMenu.add(replay);
        functionJMenu.add(relogin);
        functionJMenu.add(close);
        aboutJMenu.add(account);
        replay.addActionListener((ActionListener) this);
        close.addActionListener((ActionListener) this);
        account.addActionListener((ActionListener) this);
        relogin.addActionListener((ActionListener) this);
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);
        this.setJMenuBar(jMenuBar);
    }
    private void initJFrame(){
        this.setSize(700,700);
        this.setTitle("拼图");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setLayout(null);
        this.addKeyListener((KeyListener) this);
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        if(code==112||code==65){
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon("images/all.png"));
            all.setBounds(30,70,420,420);
            this.getContentPane().add(all);
            this.getContentPane().repaint();
        }
    }
    public void keyReleased(KeyEvent e) {
        if(victory()){
            return;
        }
        int code=e.getKeyCode();
        System.out.println(code);
        if(code==37){
        System.out.println("向左移动");
        if(y==3){
            return;
        }
        data[x][y]=data[x][y+1];
        data[x][y+1]=0;
        y++;
        step++;
        initImage();
    }else if(code==38){
            System.out.println("向上移动");
            if(x==3){
                return;
            }
            data[x][y]=data[x+1][y];
            data[x+1][y]=0;
            x++;
            step++;
            initImage();
        }else if(code==39){
            System.out.println("向右移动");
            if(y==0){
                return;
            }
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;
            step++;
            initImage();
        }else if(code==40){
            System.out.println("向下移动");
            if(x==0){
                return;
            }
            data[x][y]=data[x-1][y];
            data[x-1][y]=0;
            x--;
            step++;
            initImage();
        }else if(code==65){
            initImage();
        }else if(code==87){
            data=new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
            initImage();
        }
    }
    public boolean victory(){
        for(int i=0;i< data.length;i++){
            for(int j=0;j<data[i].length;j++){
                if(data[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    public void actionPerformed(ActionEvent e){
        Object obj=e.getSource();
        if(obj==replay){
            System.out.println("重新游戏");
            step=0;
            initData();
            initImage();
        }else if(obj==relogin){
            System.out.println("重新登录");
            this.setVisible(false);
            new Window();
        }else if(obj==close){
            System.out.println("关闭游戏");
            System.exit(0);
        }else if(obj==account){
            System.out.println("关于");
            JDialog jDialog=new JDialog();
            jDialog.setSize(344,344);
            JLabel clue=new JLabel("w一键完成,a刷新，长按a显示原图，就这些了");
            clue.setBounds(0,0,100,20);
            jDialog.getContentPane().add(clue);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }
    }
}