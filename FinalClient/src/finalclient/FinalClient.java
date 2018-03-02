package finalclient;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;
import java.util.EventListener;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FinalClient extends JFrame {

    static boolean cheat = false;
    static Frog firstplayer;
    static Frog secondplayer;
    static Vector players = new Vector(10);
    String msg;
    private static PrintWriter out;
    private static BufferedReader in;
    static Container container;
    static Car car1;
    static Car car2;
    static Car car3;
    static Car car4;
    static Car car5;

    public static void main(String[] args) throws IOException {
        BufferedImage img1 = ImageIO.read(new File("img/frog.png"));
        BufferedImage img2 = ImageIO.read(new File("img/lbue.png"));
        ImageIcon icon1 = new ImageIcon(img1);
        ImageIcon icon2 = new ImageIcon(img2);

        FinalClient frame = new FinalClient();
        container = frame.getContentPane();

        firstplayer = new Frog(icon1);
        firstplayer.setBounds(0, 0, 0, 0);

        secondplayer = new Frog(icon2);
        secondplayer.setBounds(720, 0, 0, 0);
        container.add(secondplayer);
        container.add(firstplayer);

        BufferedImage imgCar1 = ImageIO.read(new File("img/carl.png"));
        ImageIcon iconCar1 = new ImageIcon(imgCar1);
        BufferedImage imgCar2 = ImageIO.read(new File("img/carr.png"));
        ImageIcon iconCar2 = new ImageIcon(imgCar2);
        car1 = new Car(iconCar1, 1);
        car2 = new Car(iconCar1, 2);
        car3 = new Car(iconCar2, 3);
        car4 = new Car(iconCar2, 4);
        car5 = new Car(iconCar2, 5);
        container.add(car1);
        container.add(car2);
        container.add(car3);
        container.add(car4);
        container.add(car5);

        Background jbackground = new Background("img/newmap.png");
        jbackground.setBounds(0, 0, 800, 600);
        container.add(jbackground);

        frame.setVisible(true);
        frame.creatsocket();

    }

    public void creatsocket() throws IOException {
        Socket socket = new Socket("localhost", 9800);
        out = new PrintWriter(socket.getOutputStream(), true);
        final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (true) {
            msg = in.readLine();
            if (msg.equals("firstin")) { //取得第一個進來的是左邊的第一隻青蛙
                firstplayer.setBounds(50, 500, 85, 50);
            } else if (msg.equals("secondin")) { //取得第二個進來的隻青蛙
                secondplayer.setBounds(665, 500, 85, 50);
            } else if (msg.startsWith("init_rival")) { //第一個青蛙要取得第二隻青蛙的位置 從server傳來
                String[] role_data_Array = msg.split(" ");
                int role_x = Integer.parseInt(role_data_Array[1]);
                if (role_data_Array[2].equals("firstin")) {
                    firstplayer.setBounds(role_x, 500, 85, 50);
                    car1.go(1);
                    car2.go(2);
                    car3.go(3);
                    car4.go(4);
                    car5.go(5);
                } else if (role_data_Array[2].equals("secondin")) {
                    secondplayer.setBounds(role_x, 500, 85, 50);
                    System.out.println("All ready");
                    car1.go(1);
                    car2.go(2);
                    car3.go(3);
                    car4.go(4);
                    car5.go(5);
                }
            } else if (msg.startsWith("set_keyevent")) {
                String[] role_data_Array = msg.split(" ");
                if (role_data_Array[1].equals("firstin")) {
                    firstplayer.requestFocus();//一定要加這行，否則建盤偵聽會失效
                    firstplayer.addKeyListener(new MyKeyListener("firstin"));
                } else {
                    secondplayer.requestFocus();//一定要加這行，否則建盤偵聽會失效
                    secondplayer.addKeyListener(new MyKeyListener("secondin"));
                }
            } else if (msg.startsWith("rival_move")) {
                String[] role_data_Array = msg.split(" ");
                int riavl_label_x = Integer.parseInt(role_data_Array[1]);
                int riavl_label_y = Integer.parseInt(role_data_Array[2]);
                String rival_role_name = role_data_Array[3];
                if (rival_role_name.equals("firstin")) {
                    firstplayer.setLocation(riavl_label_x, riavl_label_y);
                    if (firstplayer.getY() < 50) {
                        out.println("first_win");
                        out.flush();

                    }
                } else {
                    secondplayer.setLocation(riavl_label_x, riavl_label_y);
                    if (secondplayer.getY() < 50) {
                        out.println("second_win");
                        out.flush();

                    }
                }
            } else if (msg.startsWith("over")) {
                String[] role_data_Array = msg.split(" ");
                if (role_data_Array[1].equals("first_win")) {
                    firstplayer.setBounds(50, 500, 85, 50);
                    secondplayer.setBounds(665, 500, 85, 50);
                      out.println("role_move " + 665 + " " + 500);
                    JOptionPane.showMessageDialog(null, "綠色青蛙贏了");
                } else {
                    firstplayer.setBounds(50, 500, 85, 50);
                    secondplayer.setBounds(665, 500, 85, 50);
                       out.println("role_move " + 665 + " " + 500);
                    JOptionPane.showMessageDialog(null, "藍色青蛙贏了");
                    
                }
            }
        }

    }

    public FinalClient() {
        super();
        setResizable(false);  // 視窗不可縮放
        setTitle("車過青蛙");
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

    }

    private static class Background extends JPanel {

        private Image bgimg;

        public Background(String fileName) throws IOException {
            bgimg = ImageIO.read(new File(fileName));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgimg, 0, 0, this);
        }

    }

    class MyKeyListener implements KeyListener {

        JLabel role_label;
        JLabel init_label;

        public MyKeyListener(String role_name) {
            if (role_name.equals("firstin")) {
                role_label = firstplayer;
                init_label = secondplayer;
            } else {
                role_label = secondplayer;
                init_label = firstplayer;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int init_label_x = init_label.getX();
            int init_label_y = init_label.getY();
            int before_label_x = role_label.getX();
            int before_label_y = role_label.getY();
            int now_label_y = 0;
            int now_label_x = 0;
            int delta_move = 15;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_Q:
                    cheat = true;
                    break;
                case KeyEvent.VK_W:
                    if (cheat == true) {
                        now_label_y = before_label_y - 60;
                        role_label.setLocation(before_label_x, now_label_y);
                        out.println("role_move " + before_label_x + " " + now_label_y);
                        out.flush();
                        break;
                    } else {
                        break;
                    }

                case KeyEvent.VK_UP:
                    now_label_y = before_label_y - delta_move;
                    if (outline(before_label_x, now_label_y) == true && bump(before_label_x, now_label_y, init_label_x, init_label_y) == true) {
                        role_label.setLocation(before_label_x, now_label_y);
                        if (carbump(before_label_x, now_label_y, car1.getX(), car1.getY()) == false || carbump(before_label_x, now_label_y, car2.getX(), car2.getY()) == false || carbump(before_label_x, now_label_y, car3.getX(), car3.getY()) == false || carbump(before_label_x, now_label_y, car4.getX(), car4.getY()) == false || carbump(before_label_x, now_label_y, car5.getX(), car5.getY()) == false) {
                            role_label.setBounds(300, 500, 85, 50);
                        }
                        out.println("role_move " + before_label_x + " " + now_label_y);
                        out.flush();
                        break;
                    } else {
                        break;
                    }
                case KeyEvent.VK_DOWN:
                    now_label_y = before_label_y + delta_move;
                    if (outline(before_label_x, now_label_y) == true && bump(before_label_x, now_label_y, init_label_x, init_label_y) == true) {
                        role_label.setLocation(before_label_x, now_label_y);
                        if (carbump(before_label_x, now_label_y, car1.getX(), car1.getY()) == false || carbump(before_label_x, now_label_y, car2.getX(), car2.getY()) == false || carbump(before_label_x, now_label_y, car3.getX(), car3.getY()) == false || carbump(before_label_x, now_label_y, car4.getX(), car4.getY()) == false || carbump(before_label_x, now_label_y, car5.getX(), car5.getY()) == false) {
                            role_label.setBounds(300, 500, 85, 50);
                        }
                        out.println("role_move " + before_label_x + " " + now_label_y);
                        out.flush();
                        break;
                    } else {
                        break;
                    }
                case KeyEvent.VK_LEFT:
                    now_label_x = before_label_x - delta_move;
                    if (outline(now_label_x, before_label_y) == true && bump(now_label_x, before_label_y, init_label_x, init_label_y) == true) {
                        role_label.setLocation(now_label_x, before_label_y);
                        if (carbump(now_label_x, before_label_y, car1.getX(), car1.getY()) == false || carbump(now_label_x, before_label_y, car2.getX(), car2.getY()) == false || carbump(now_label_x, before_label_y, car3.getX(), car3.getY()) == false || carbump(now_label_x, before_label_y, car4.getX(), car4.getY()) == false || carbump(now_label_x, before_label_y, car5.getX(), car5.getY()) == false) {
                            role_label.setBounds(300, 500, 85, 50);
                        }
                        out.println("role_move " + now_label_x + " " + before_label_y);
                        out.flush();
                        break;
                    } else {
                        break;
                    }
                case KeyEvent.VK_RIGHT:
                    now_label_x = before_label_x + delta_move;
                    if (outline(now_label_x, before_label_y) == true && bump(now_label_x, before_label_y, init_label_x, init_label_y) == true) {
                        role_label.setLocation(now_label_x, before_label_y);
                        if (carbump(now_label_x, before_label_y, car1.getX(), car1.getY()) == false || carbump(now_label_x, before_label_y, car2.getX(), car2.getY()) == false || carbump(now_label_x, before_label_y, car3.getX(), car3.getY()) == false || carbump(now_label_x, before_label_y, car4.getX(), car4.getY()) == false || carbump(now_label_x, before_label_y, car5.getX(), car5.getY()) == false) {
                            role_label.setBounds(300, 500, 85, 50);
                        }
                        out.println("role_move " + now_label_x + " " + before_label_y);
                        out.flush();
                        break;
                    } else {
                        break;
                    }

            }

        }

        public boolean carbump(int x, int y, int ix, int iy) {
            int sizeh = car1.getHeight();//取得高
            int sizew = car1.getWidth(); //取得寬
            if (x >= (ix - sizew / 2) && x <= (ix + sizew / 2) && y >= (iy - sizeh / 2) && y <= (iy + sizeh / 2)) {
                return false;
            } else {
                return true;
            }
        }

        public boolean outline(int x, int y) {
            if (x > 710 || x < -10 || y < 0 || y > 515) {
                System.out.println("outtttttttttt");
                return false;
            } else {
                return true;
            }
        }

        public boolean bump(int x, int y, int ix, int iy) {
            int sizeh = role_label.getHeight();//取得高
            int sizew = role_label.getWidth(); //取得寬
            if (x >= (ix - sizew / 2) && x <= (ix + sizew / 2) && y >= (iy - sizeh / 2) && y <= (iy + sizeh / 2)) {
                System.out.println("bumpppppppppp");
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

}
