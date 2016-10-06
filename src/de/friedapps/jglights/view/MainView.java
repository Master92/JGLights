package de.friedapps.jglights.view;

import de.friedapps.jglights.controller.Communicator;
import de.friedapps.jglights.interfaces.MainViewControllerInterface;
import de.friedapps.jglights.interfaces.aiprotocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class MainView extends JFrame implements ActionListener {
    private static final String DISCONNECT = "dis", CONNECT = "con", ADD_END = "add", DEL_END = "del", NEXT = "next", SHOW_CLOCK = "clck", SET_TIMER = "tim", SEND_TEXT = "tex", NEW_ROUND = "new";

    private JPanel labelPane;
    private JLabel countdownLabel, groupLabel, endsLabel;
    private JButton disconnectButton, connectButton, addButton, delButton, nextButton, clockButton, timerButton, sendTextButton, newRoundButton;
    private JTextField inputField;

    private Communicator comm;


    public MainView() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("JGLights");
        setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        preparePanel();

        cons.fill = GridBagConstraints.BOTH;
        cons.weightx = 1;
        cons.weighty = 1;
        cons.gridx = 0;
        cons.gridy = 4;
        cons.insets = new Insets(5,5,5,5);

        cons.gridwidth = 2;
        disconnectButton = new JButton("Trennen");
        disconnectButton.setActionCommand(DISCONNECT);
        disconnectButton.addActionListener(this);
        add(disconnectButton, cons);

        cons.gridx = 2;
        cons.gridwidth = 4;
        connectButton = new JButton("Verbinden");
        connectButton.setActionCommand(CONNECT);
        connectButton.addActionListener(this);
        add(connectButton, cons);

        cons.gridy++;
        cons.gridwidth = 3;
        cons.gridx = 0;
        delButton = new JButton("Passe entfernen");
        delButton.setActionCommand(DEL_END);
        delButton.addActionListener(this);
        add(delButton, cons);

        cons.gridx = 3;
        addButton = new JButton("Passe hinzufügen");
        addButton.setActionCommand(ADD_END);
        addButton.addActionListener(this);
        add(addButton, cons);

        cons.gridy++;
        cons.gridx = 0;
        cons.gridwidth = 6;
        cons.gridheight = 2;
        nextButton = new JButton("Weiter");
        nextButton.setActionCommand(NEXT);
        nextButton.addActionListener(this);
        add(nextButton, cons);

        cons.gridy++;
        cons.gridy++;
        cons.gridheight = 1;
        cons.gridwidth = 3;
        clockButton = new JButton("Uhrzeit anzeigen");
        clockButton.setActionCommand(SHOW_CLOCK);
        clockButton.addActionListener(this);
        add(clockButton, cons);

        cons.gridx = 3;
        timerButton = new JButton("Timer stellen...");
        timerButton.setActionCommand(SET_TIMER);
        timerButton.addActionListener(this);
        add(timerButton, cons);

        cons.gridy++;
        cons.gridx = 0;
        cons.gridwidth = 5;
        inputField = new JTextField();
        inputField.setActionCommand(SEND_TEXT);
        inputField.addActionListener(this);
        add(inputField, cons);

        cons.gridx = 5;
        cons.gridwidth = 1;
        sendTextButton = new JButton("Abschicken");
        sendTextButton.setActionCommand(SEND_TEXT);
        sendTextButton.addActionListener(this);
        add(sendTextButton, cons);

        cons.gridx = 0;
        cons.gridy++;
        cons.gridwidth = 6;
        newRoundButton = new JButton("Neue Runde...");
        newRoundButton.setActionCommand(NEW_ROUND);
        newRoundButton.addActionListener(this);
        add(newRoundButton, cons);

        pack();
        setLocationRelativeTo(null);
        comm = new Communicator(new MainViewController());
        commEnabled(false);
        setResizable(false);
        setVisible(true);
    }

    private void preparePanel() {
        GridBagConstraints cons = new GridBagConstraints();

        cons.fill = GridBagConstraints.NONE;
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 6;

        labelPane = new JPanel(new GridBagLayout());
        countdownLabel = new JLabel("0");
        countdownLabel.setHorizontalAlignment(JLabel.CENTER);
        countdownLabel.setFont(new Font(null, Font.BOLD, 45));
        labelPane.add(countdownLabel, cons);

        cons.gridy = 1;
        groupLabel = new JLabel("A/B");
        groupLabel.setHorizontalAlignment(JLabel.CENTER);
        groupLabel.setFont(new Font(null, Font.PLAIN, 25));
        labelPane.add(groupLabel, cons);

        cons.gridy = 2;
        endsLabel = new JLabel("Passe 0 von 0");
        endsLabel.setHorizontalAlignment(JLabel.CENTER);
        endsLabel.setFont(new Font(null, Font.PLAIN, 12));
        labelPane.add(endsLabel, cons);

        add(labelPane, cons);
    }

    private void commEnabled(boolean enable) {
        connectButton.setEnabled(!enable);
        disconnectButton.setEnabled(enable);
        delButton.setEnabled(enable);
        addButton.setEnabled(enable);
        nextButton.setEnabled(enable);
        clockButton.setEnabled(enable);
        timerButton.setEnabled(enable);
        inputField.setEnabled(enable);
        sendTextButton.setEnabled(enable);
        newRoundButton.setEnabled(enable);

        if(enable)
            comm.startListening();
        else
            comm.stopListening();
    }

    private void onConnect() {
        int am = ConnectDialog.show(this);
        if(am > 0)
            comm.connect(am);
    }

    private void onDisconnect() {
        comm.disconnect();
    }

    private void onAddEnd() {
        comm.sendMessage(aiprotocol.ADD_END);
    }

    private void onDelEnd() {
        comm.sendMessage(aiprotocol.DEL_END);
    }

    private void onNext() {
        comm.sendMessage(aiprotocol.NEXT);
    }

    private void onShowClock() {
        comm.sendMessage(aiprotocol.TIME);
    }

    private void onSetTimer() {
        if(TimerDialog.show(this) == JOptionPane.OK_OPTION)
            comm.sendMessage(aiprotocol.TIMER, TimerDialog.getTimer());
    }

    private void onNewRound() {
        if(NewRoundDialog.showDialog(this) == JOptionPane.OK_OPTION) {
            comm.sendMessage(aiprotocol.NEW_ROUND, NewRoundDialog.getString());
        }
    }

    private void onSendText() {
        comm.sendMessage(aiprotocol.RUNTEXT, inputField.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command) {
            case DISCONNECT:
                onDisconnect();
                break;

            case CONNECT:
                onConnect();
                break;

            case ADD_END:
                onAddEnd();
                break;

            case DEL_END:
                onDelEnd();
                break;

            case NEXT:
                onNext();
                break;

            case SHOW_CLOCK:
                onShowClock();
                break;

            case SET_TIMER:
                onSetTimer();
                break;

            case SEND_TEXT:
                onSendText();
                break;

            case NEW_ROUND:
                onNewRound();
                break;

            default:
                System.out.println(command);
                break;
        }
    }

    private class MainViewController implements MainViewControllerInterface {

        @Override
        public void setCountdown(int value) {
            countdownLabel.setText(value + "");
        }

        @Override
        public void setGroup(int value) {
            String s = (value == 0) ? "A/B" : "C/D";
            groupLabel.setText(s);
        }

        @Override
        public void setEnd(int curEnd, int maxEnds) {
            endsLabel.setText(String.format("Passe %d von %d", curEnd, maxEnds));
        }

        @Override
        public void setBGColor(Color c) {
            labelPane.setBackground(c);
        }

        @Override
        public void setCommEnabled(boolean enabled) {
            commEnabled(enabled);
        }

        @Override
        public void showError(String msg) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "ERROR: "+msg, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
