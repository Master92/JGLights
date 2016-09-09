package de.friedapps.jglights.view;

import javax.swing.*;
import java.awt.*;

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

public class ConnectDialog {

    /**
     * Shows a modal Dialog to ask the user to how many displays he wants to connect to.
     *
     * @param parent Component to center on
     * @return The number of displays to connect to if ok has been clicked or -1 if it has been canceled.
     */
    public static int show(Component parent) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        JLabel label = new JLabel("Mit wie vielen Displays möchtest du dich verbinden?");
        label.setLabelFor(spinner);

        JPanel p = new JPanel();
        p.add(label);
        p.add(spinner);


        if(JOptionPane.showConfirmDialog(parent, p, "Verbinden...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION)
            return (int)spinner.getValue();
        else
            return -1;
    }
}
