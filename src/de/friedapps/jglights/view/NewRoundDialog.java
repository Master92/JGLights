package de.friedapps.jglights.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//
// JGLights Copyright (C) 2016 Nils Friedchen <Nils.Friedchen@googlemail.com>
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation version 2.
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

public class NewRoundDialog implements ActionListener {
    private static String s = "";
    private static JSpinner amountSpinner, durationSpinner, preparationSpinner;
    private static JCheckBox groupCheckBox;
    private static JRadioButton indoorRadio, outdoorRadio, ligaRadio, customRadio;

    private static final String INDOOR = "in", OUTDOOR = "out", LIGA = "lig", CUSTOM = "cus";

    public static int showDialog(Component parent) {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        ActionListener a = new NewRoundDialog();

        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 2;
        cons.gridheight = 1;
        cons.fill = GridBagConstraints.NONE;

        indoorRadio = new JRadioButton("WA indoor", true);
        indoorRadio.setActionCommand(INDOOR);
        indoorRadio.addActionListener(a);
        p.add(indoorRadio, cons);

        cons.gridy++;
        outdoorRadio = new JRadioButton("WA outdoor");
        outdoorRadio.setActionCommand(OUTDOOR);
        outdoorRadio.addActionListener(a);
        p.add(outdoorRadio, cons);

        cons.gridy++;
        ligaRadio = new JRadioButton("Ligamodus");
        ligaRadio.setActionCommand(LIGA);
        ligaRadio.addActionListener(a);
        p.add(ligaRadio, cons);

        cons.gridy++;
        customRadio = new JRadioButton("Benutzerdefiniert");
        customRadio.setActionCommand(CUSTOM);
        customRadio.addActionListener(a);
        p.add(customRadio, cons);

        ButtonGroup b = new ButtonGroup();
        b.add(indoorRadio);
        b.add(outdoorRadio);
        b.add(ligaRadio);
        b.add(customRadio);

        cons.gridwidth = 1;
        cons.gridy++;
        amountSpinner = new JSpinner(new SpinnerNumberModel(10,1,12,1));
        amountSpinner.setEnabled(false);
        JLabel l = new JLabel("Anzahl der Passen:");
        l.setLabelFor(amountSpinner);
        p.add(l, cons);
        cons.gridx++;
        p.add(amountSpinner, cons);

        cons.gridx--;
        cons.gridy++;
        preparationSpinner = new JSpinner(new SpinnerNumberModel(10,1,20,1));
        preparationSpinner.setEnabled(false);
        l = new JLabel("Vorbereitungszeit");
        l.setLabelFor(preparationSpinner);
        p.add(l, cons);
        cons.gridx++;
        p.add(preparationSpinner, cons);

        cons.gridx--;
        cons.gridy++;
        durationSpinner = new JSpinner(new SpinnerNumberModel(120, 1, 240, 1));
        durationSpinner.setEnabled(false);
        l = new JLabel("Dauer einer Passe");
        l.setLabelFor(durationSpinner);
        p.add(l, cons);
        cons.gridx++;
        p.add(durationSpinner, cons);

        cons.gridx--;
        cons.gridy++;
        cons.gridwidth = 2;
        groupCheckBox = new JCheckBox("A/B - C/D Modus", true);
        groupCheckBox.setEnabled(false);
        p.add(groupCheckBox, cons);


        int i = JOptionPane.showConfirmDialog(parent, p, "Neue Runde...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(i == JOptionPane.OK_OPTION) {
            s = "";
            s += (char)(int) amountSpinner.getValue();
            s += (char)(int) preparationSpinner.getValue();
            int iDur = (int)durationSpinner.getValue();
            int d1= iDur / 10;
            int d2 = iDur - d1 * 10;
            s += (char)d1;
            s += (char)d2;
            s += (groupCheckBox.isSelected()) ? '1' : '0';
        }

        return i;
    }

    public static String getString() {
        return s;
    }

    private void enable(boolean b) {
        amountSpinner.setEnabled(b);
        preparationSpinner.setEnabled(b);
        durationSpinner.setEnabled(b);
        groupCheckBox.setEnabled(b);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String s = actionEvent.getActionCommand();
        switch(s) {
            case INDOOR:
                amountSpinner.setValue(10);
                preparationSpinner.setValue(10);
                durationSpinner.setValue(120);
                groupCheckBox.setSelected(true);
                enable(false);
                break;

            case OUTDOOR:
                amountSpinner.setValue(6);
                preparationSpinner.setValue(10);
                durationSpinner.setValue(240);
                groupCheckBox.setSelected(true);
                enable(false);
                break;

            case LIGA:
                amountSpinner.setValue(3);
                preparationSpinner.setValue(10);
                durationSpinner.setValue(120);
                groupCheckBox.setSelected(false);
                enable(false);
                break;

            case CUSTOM:
                enable(true);
                break;
        }
    }
}
