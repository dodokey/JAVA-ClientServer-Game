package finalgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.lang.Object;
import java.awt.*;
import java.awt.event.*;

class GameThread extends Thread {

    static Vector players = new Vector(10);
    Socket socket;
    int player_id;
    BufferedReader in;
    PrintWriter out;
    static int usernum = 0;
    int role_x;
    int role_y;
    String rolesite;
    String msg;

    public GameThread(Socket socket) throws IOException {
        synchronized (players) {
            players.addElement(this);
        }
        player_id = usernum;
        usernum++;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        if (usernum % 2 == 1) {  //兩個一組
            out.flush();
            role_x = 50;
            rolesite = "firstin";
            out.println(rolesite);
            out.flush();

        } else {
            out.flush();
            role_x = 665;
            rolesite = "secondin";
            out.println(rolesite);
            out.flush();

            for (int i = 0; i < players.size(); i++) {
                synchronized (players) {
                    GameThread self = (GameThread) players.elementAt(i);
                    GameThread rival;
                    if (i % 2 == 0) {
                        rival = (GameThread) players.elementAt(i + 1);
                    } else {
                        rival = (GameThread) players.elementAt(i - 1);
                    }

                    self.out.println("init_rival " + rival.role_x + " " + rival.rolesite);
                    self.out.flush();
                    self.out.println("set_keyevent " + self.rolesite);
                    self.out.flush();
                }
            }

        }
        while (true) {
            try {
                msg = in.readLine();
                if (msg.startsWith("role_move")) {
                    String[] row_data_Array = msg.split(" ");
                    role_x = Integer.parseInt(row_data_Array[1]);
                    role_y = Integer.parseInt(row_data_Array[2]);
                    GameThread rival;
                    if (player_id % 2 == 0) {
                        rival = (GameThread) players.elementAt(player_id + 1);
                    } else {
                        rival = (GameThread) players.elementAt(player_id - 1);
                    }
                    rival.out.println("rival_move " + role_x + " " + role_y + " " + this.rolesite);
                    System.out.println(this.rolesite);
                    rival.out.flush();
                }
                if (msg.equals("first_win")) {
                    GameThread rival;
                    if (player_id % 2 == 0) {
                        rival = (GameThread) players.elementAt(player_id + 1);
                    } else {
                        rival = (GameThread) players.elementAt(player_id - 1);
                    }
                    rival.out.println("over first_win");
                    rival.out.flush();
                }
                if (msg.equals("second_win")) {
                    GameThread rival;
                    if (player_id % 2 == 0) {
                        rival = (GameThread) players.elementAt(player_id + 1);
                    } else {
                        rival = (GameThread) players.elementAt(player_id - 1);
                    }
                    rival.out.println("over second_win");
                    rival.out.flush();
                }
            } catch (Exception e) {

            }
        }
    }
}
