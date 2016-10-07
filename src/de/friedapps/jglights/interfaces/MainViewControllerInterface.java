package de.friedapps.jglights.interfaces;

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

import java.awt.*;

public interface MainViewControllerInterface {
    void setCountdown(int value);
    void setGroup(int value);
    void setEnd(int curEnd, int maxEnds);
    void setBGColor(Color c);
    void setCommEnabled(boolean enabled);
    void showError(String msg);
    void setTimer(int hours, int minutes, int seconds);
}
