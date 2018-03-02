package finalgame;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FinalGame extends JFrame {

    private ServerSocket server;
    private Socket socket;
    static int num = 0;

    JTextArea msg;

    public FinalGame() {
        super();
        setResizable(false);
        setTitle("Server");
        setBounds(600, 100, 245, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        msg = new JTextArea();
        scrollPane.setViewportView(msg);
    }

    public static void main(String[] args) {
        FinalGame Game = new FinalGame();
        Game.setVisible(true);
        Game.createSocket();

    }

    private void createSocket() {
        try {
            server = new ServerSocket(9800);
            while (true) {
                msg.append("等待連線中... \n");
                socket = server.accept();
                num++;
                msg.append("玩家[" + num + "]" + socket + "\n已加入... \n");
                new GameThread(socket).start();
  //              GameThread handler = new GameThread(socket);
//                handler.start();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
