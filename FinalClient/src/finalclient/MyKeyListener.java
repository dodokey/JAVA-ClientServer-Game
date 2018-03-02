package finalclient;

import static finalclient.FinalClient.firstplayer;
import static finalclient.FinalClient.secondplayer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.System.out;
import javax.swing.JLabel;

     class MyKeyListener implements KeyListener{
        JLabel role_label;
        public MyKeyListener(String role_name){
            if (role_name.equals("firstin")) {
                role_label = firstplayer;
            }
            else{
                role_label = secondplayer;
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int before_label_x = role_label.getX();
            int before_label_y = role_label.getY();
            int now_label_y;
            int now_label_x;
            int delta_move = 10;
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                     now_label_y = before_label_y - delta_move;
                     role_label.setLocation(before_label_x, now_label_y);
                     out.println("role_move "+ before_label_x  + " " + now_label_y);
                     out.flush();
                    break;
                case KeyEvent.VK_DOWN:
                     now_label_y = before_label_y + delta_move;
                     role_label.setLocation(before_label_x, now_label_y);
                     out.println("role_move "+ before_label_x  + " " + now_label_y);
                     out.flush();
                     break;
                case KeyEvent.VK_LEFT:
                     now_label_x = before_label_x - delta_move;
                     role_label.setLocation(now_label_x, before_label_y);
                     out.println("role_move "+ now_label_x  + " " + before_label_y);
                     out.flush();
                     break;
                case KeyEvent.VK_RIGHT:
                     now_label_x = before_label_x + delta_move;
                     role_label.setLocation(now_label_x, before_label_y);
                     out.println("role_move "+ now_label_x  + " " + before_label_y);
                     out.flush();  
                     break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
        
    }
    