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

package eu.geopaparazzi.library.core.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import eu.geopaparazzi.library.R;
import eu.geopaparazzi.library.style.LabelObject;


/**
 * Class to label properties for a feature.
 *
 * @author Andrea Antonello
 */
public class LabelDialogFragment extends DialogFragment {
    /**
     * A simple interface to use to notify label properties changes.
     */
    public interface ILabelPropertiesChangeListener {

        /**
         * Called when there is the need to notify that a change occurred.
         */
        void onPropertiesChanged(LabelObject newLabelObject);
    }

    private final static String PREFS_KEY_LABELPROPERTIES = "PREFS_KEY_LABELPROPERTIES";//NON-NLS

    private LabelObject mCurrentLabelObject;

    private ILabelPropertiesChangeListener labelPropertiesChangeListener;
    private TextView mSizeTextView;

    /**
     * Create a dialog instance.
     *
     * @param labelObject object holding label info.
     * @return the instance.
     */
    public static LabelDialogFragment newInstance(LabelObject labelObject) {
        LabelDialogFragment f = new LabelDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(PREFS_KEY_LABELPROPERTIES, labelObject);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabelObject mInitialLabelObject = (LabelObject) getArguments().getSerializable(PREFS_KEY_LABELPROPERTIES);
        if (mInitialLabelObject != null) {
            mCurrentLabelObject = mInitialLabelObject.duplicate();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        View labelDialogView =
                getActivity().getLayoutInflater().inflate(
                        R.layout.fragment_dialog_label, null);
        builder.setView(labelDialogView);

        CheckBox visibilityCheckBox = labelDialogView.findViewById(R.id.checkVisibility);
        visibilityCheckBox.setChecked(mCurrentLabelObject.hasLabel);
        visibilityCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> mCurrentLabelObject.hasLabel = isChecked);

        mSizeTextView = labelDialogView.findViewById(R.id.sizeTextView);
        mSizeTextView.setText(String.valueOf(mCurrentLabelObject.labelSize));
        SeekBar mSizeSeekBar = labelDialogView.findViewById(R.id.sizeSeekBar);
        mSizeSeekBar.setOnSeekBarChangeListener(sizeChanged);
        mSizeSeekBar.setProgress(mCurrentLabelObject.labelSize);


        List<String> labelFieldsList = mCurrentLabelObject.labelFieldsList;
        int index = 0;
        for (int i = 0; i < labelFieldsList.size(); i++) {
            if (labelFieldsList.get(i).equals(mCurrentLabelObject.label)) {
                index = i;
                break;
            }
        }
        final Spinner fieldsSpinner = labelDialogView.findViewById(R.id.labelFieldSpinner);
        ArrayAdapter<String> fieldsSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                labelFieldsList);
        fieldsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fieldsSpinner.setAdapter(fieldsSpinnerAdapter);
        fieldsSpinner.setSelection(index);
        fieldsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Object selectedItem = fieldsSpinner.getSelectedItem();
                mCurrentLabelObject.label = selectedItem.toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // ignore
            }
        });


        builder.setPositiveButton(R.string.set_properties,
                (dialog, id) -> {
                    if (labelPropertiesChangeListener != null) {
                        labelPropertiesChangeListener.onPropertiesChanged(mCurrentLabelObject);
                    }
                }
        );
        builder.setNegativeButton(getString(android.R.string.cancel),
                (dialog, id) -> {
                }
        );

        return builder.create(); // return dialog
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ILabelPropertiesChangeListener) {
            labelPropertiesChangeListener = (ILabelPropertiesChangeListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        labelPropertiesChangeListener = null;
    }

    private final OnSeekBarChangeListener sizeChanged =
            new OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    mCurrentLabelObject.labelSize = progress;
                    mSizeTextView.setText(String.valueOf(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                } // required

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                } // required
            };


}
