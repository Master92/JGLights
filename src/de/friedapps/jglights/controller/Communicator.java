package de.friedapps.jglights.controller;

import de.friedapps.jglights.interfaces.MainViewControllerInterface;
import de.friedapps.jglights.interfaces.aiprotocol;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

//
// JAGLights Copyright (C) 2016 Nils Friedchen <Nils.Friedchen@googlemail.com>
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
// or visit https://www.gnu.org/licenses/gpl-2.0.txt
//

public class Communicator {
    private MainViewControllerInterface controller;
    private ArrayList<Socket> sockets;
    private ArrayList<BufferedWriter> writers;
    private InputStreamReader reader;
    private Thread t;

    public Communicator(MainViewControllerInterface controller) {
        this.controller = controller;
        sockets = new ArrayList<>();
        writers = new ArrayList<>();
    }

    public void connect(int amountOfDisplays) {
        for(int i = 0; i < amountOfDisplays; i++) {
            try {
                Socket s = new Socket();
                s.connect(new InetSocketAddress("192.168.0.1" + i, aiprotocol.PORT), 1000);
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

                sockets.add(s);
                writers.add(w);
            } catch (IOException e) {
                controller.showError(e.getLocalizedMessage());
            }

            if(sockets.size() > 0 && writers.size() > 0) {
                sync();
                try {reader = new InputStreamReader(sockets.get(0).getInputStream());} catch (IOException e) {controller.showError(e.getLocalizedMessage());}
                controller.setCommEnabled(true);
            }
        }
    }

    public void disconnect() {
        controller.setCommEnabled(false);

        for(BufferedWriter w : writers) {
            try {w.close();} catch (IOException e) {controller.showError(e.getLocalizedMessage());}
        }

        for(Socket s : sockets) {
            try {s.close();} catch (IOException e) {controller.showError(e.getLocalizedMessage());}
        }
    }

    public void sendMessage(byte msg) {
        char[] ca = new char[3];
        ca[0] = 3;
        ca[1] = (char)msg;
        ca[2] = 127;

        send(ca);
    }

    public void sendMessage(byte msg, String extra) {
        int length = extra.length() + 3;
        char[] ca = new char[length];
        ca[0] = (char)length;
        ca[1] = (char)msg;
        for(int i = 0; i < extra.length(); i++) {
            ca[i+2] = extra.charAt(i);
        }
        ca[length-1] = 127;

        send(ca);
    }

    private void send(char[] c) {
        for(BufferedWriter w : writers) {
            try {
                w.write(c);
                w.flush();
            } catch (IOException e) {
                controller.showError(e.getLocalizedMessage());
            }
        }

        for(char ch : c) {
            System.out.print(ch);
        }
        System.out.println();
    }

    private void sync() {
        Calendar now = Calendar.getInstance();
        StringBuilder date = new StringBuilder();
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DATE);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        date.append(now.get(Calendar.YEAR));
        date.append((month < 10) ? "0" + month : month);
        date.append((day < 10) ? "0" + day : day);
        date.append((hour < 10) ? "0" + hour : hour);
        date.append((minute < 10) ? "0" + minute : minute);
        date.append((second < 10) ? "0" + second : second);

        sendMessage(aiprotocol.SYNC, date.toString());
    }

    public void startListening() {
        if(t != null) {
            t.start();
            return;
        }

        t = new Thread(() -> {
            boolean run = true;
            char buffer[] = new char[7];
            while(run) {
                try {
                    if(reader.read(buffer, 0, 7) == -1)
                        run = false;

                    else {
                        controller.setCountdown(buffer[1]*10+buffer[2]);
                        controller.setGroup(buffer[4]);
                        controller.setEnd(buffer[5]+1, buffer[6]);
                        controller.setBGColor((buffer[3] == aiprotocol.COLOR_GREEN) ? Color.green : (buffer[3] == aiprotocol.COLOR_YELLOW) ? Color.yellow : Color.red);
                    }

                    Thread.sleep(250);
                } catch(InterruptedException e) {run = false;}
                catch(IOException e) {controller.showError(e.getLocalizedMessage()); run = false;}
            }
        });
        t.start();
    }

    public void stopListening() {
        if(t == null)
            return;
        t.interrupt();
    }
}
