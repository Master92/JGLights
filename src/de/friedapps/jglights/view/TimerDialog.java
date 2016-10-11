package de.friedapps.jglights.view;

import javax.swing.*;
import java.awt.*;

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

public class TimerDialog {
    private static String time = null;

    public static int show(Component parent) {
        JSpinner spinnerH = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
        JSpinner spinnerM = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        JSpinner spinnerS = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

        JPanel p = new JPanel();
        p.add(spinnerH);
        p.add(spinnerM);
        p.add(spinnerS);

        int i = JOptionPane.showConfirmDialog(parent, p, "Timer stellen...", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        String s = "";
        s += (char)(int)spinnerH.getValue();
        s += (char)(int)spinnerM.getValue();
        s += (char)(int)spinnerS.getValue();

        if(i == JOptionPane.OK_OPTION)
            time = s;

        return i;
    }

    public static String getTimer() {
        return time;
    }
}
