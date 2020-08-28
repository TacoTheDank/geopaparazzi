/*
 * Geopaparazzi - Digital field mapping on Android based devices
 * Copyright (C) 2016  HydroloGIS (www.hydrologis.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.geopaparazzi.library.plugin.types;

import android.os.Binder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cesar on 8/02/17.
 */

public class MenuEntryList extends Binder implements IMenuEntryList {
    private ArrayList<IMenuEntry> list = new ArrayList<>();

    public void addEntry(IMenuEntry entry) {
        list.add(entry);
    }

    @Override
    public List<IMenuEntry> getEntries() {
        return list;
    }

    @Override
    public boolean shouldReplaceDefaults() {
        return false;
    }
}