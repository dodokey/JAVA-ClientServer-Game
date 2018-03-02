package finalclient;

import static finalclient.FinalClient.car1;
import static finalclient.FinalClient.firstplayer;
import static finalclient.FinalClient.secondplayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Car extends JLabel {

    static Timer timer;
    static Timer timer2;
    static Timer timer3;
    static Timer timer4;
    static Timer timer5;

    public void go(int i) {
        if (i == 1) {
            timer.start();
        }
        if (i == 2) {
            timer2.start();
        }
        if (i == 3) {
            timer3.start();
        }
        if (i == 4) {
            timer4.start();
        }
        if (i == 5) {
            timer5.start();
        }

    }

    public Car(ImageIcon icon, int i) {
        super(icon);

        if (i == 1) {
            setBounds(850, 400, 150, 50);
            final int num = 20;
            timer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = getX();
                    int y = getY();
                    System.out.print(x + "___" + y);
                    System.out.println("");
                    if (getX() < -180) {
                        setLocation(650, 400);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),650, 400) == false) firstplayer.setBounds(300, 500, 85, 50);
                        if(carbump(secondplayer.getX(), secondplayer.getY(),650, 400) == false) secondplayer.setBounds(400, 500, 85, 50);
                    } else {
                        x = x - num;
                        setLocation(x, y);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),x, y) == false) firstplayer.setBounds(300, 500, 85, 50);   
                        if(carbump(secondplayer.getX(), secondplayer.getY(),x, y) == false) secondplayer.setBounds(400, 500, 85, 50);   
                    }

                }
            });

        }
        if (i == 2) {
            setBounds(850, 300, 150, 50);
            final int num = 20;
            timer2 = new Timer(60, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = getX();
                    int y = getY();
                    System.out.print(x + "___" + y);
                    System.out.println("");
                    if (getX() < -180) {
                        setLocation(650, 300);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),650, 300) == false) firstplayer.setBounds(300, 500, 85, 50);
                        if(carbump(secondplayer.getX(), secondplayer.getY(),650, 300) == false) secondplayer.setBounds(400, 500, 85, 50);
                    } else {
                        x = x - num;
                        setLocation(x, y);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),x, y) == false) firstplayer.setBounds(300, 500, 85, 50);   
                        if(carbump(secondplayer.getX(), secondplayer.getY(),x, y) == false) secondplayer.setBounds(400, 500, 85, 50);
                    }

                }
            });
        }
        
        if (i == 3) {
            setBounds(-150, 150, 150, 50);
            final int num = -25;
            timer3 = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = getX();
                    int y = getY();
                    System.out.print(x + "___" + y);
                    System.out.println("");
                    if (getX() > 950) {
                        setLocation(-100, 150);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),-100, 150) == false) firstplayer.setBounds(300, 500, 85, 50);
                        if(carbump(secondplayer.getX(), secondplayer.getY(),-100, 150) == false) secondplayer.setBounds(400, 500, 85, 50);
                    } else {
                        x = x - num;
                        setLocation(x, y);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),x, y) == false) firstplayer.setBounds(300, 500, 85, 50);   
                        if(carbump(secondplayer.getX(), secondplayer.getY(),x, y) == false) secondplayer.setBounds(400, 500, 85, 50);
                    }

                }
            });

        }
        
        if (i == 4) {
            setBounds(-150, 100, 150, 50);
            final int num = -20;
            timer4 = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = getX();
                    int y = getY();
                    System.out.print(x + "___" + y);
                    System.out.println("");
                    if (getX() > 950) {
                        setLocation(-100, 100);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),-100, 100) == false) firstplayer.setBounds(300, 500, 85, 50);
                        if(carbump(secondplayer.getX(), secondplayer.getY(),-100, 100) == false) secondplayer.setBounds(400, 500, 85, 50);
                    } else {
                        x = x - num;
                        setLocation(x, y);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),x, y) == false) firstplayer.setBounds(300, 500, 85, 50);   
                        if(carbump(secondplayer.getX(), secondplayer.getY(),x, y) == false) secondplayer.setBounds(400, 500, 85, 50);
                    }

                }
            });           
        }
        
         if (i == 5) {
            setBounds(-150, 200, 150, 50);
            final int num = -27;
            timer5 = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = getX();
                    int y = getY();
                    System.out.print(x + "___" + y);
                    System.out.println("");
                    if (getX() > 950) {
                        setLocation(-100, 200);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),100, 200) == false) firstplayer.setBounds(300, 500, 85, 50);
                        if(carbump(secondplayer.getX(), secondplayer.getY(),100, 200) == false) secondplayer.setBounds(400, 500, 85, 50);
                    } else {
                        x = x - num;
                        setLocation(x, y);
                        if(carbump(firstplayer.getX(), firstplayer.getY(),x, y) == false) firstplayer.setBounds(300, 500, 85, 50);   
                        if(carbump(secondplayer.getX(), secondplayer.getY(),x, y) == false) secondplayer.setBounds(400, 500, 85, 50);
                    }

                }
            });           
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
}


