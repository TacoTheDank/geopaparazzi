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
package eu.geopaparazzi.library.database;

import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

/**
 * Interface that helps handling notes in the database.
 *
 * @author Andrea Antonello (www.hydrologis.com)
 */
public interface INotesDbHelper {


    /**
     * Get an note from the db by its id..
     *
     * @param id the id of the note to get.
     * @return the image or null.
     * @throws IOException if something goes wrong.
     */
    ANote getNoteById(long id) throws IOException;
}