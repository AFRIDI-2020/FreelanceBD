package com.practice.freelancebd.Custom;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.practice.freelancebd.R;

public class CameraGalleryDialog extends AppCompatDialogFragment {

    private ImageView cameraIV,galleryIV;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.camera_gallery_dialog_box_layout,null);

        cameraIV =view.findViewById(R.id.cameraIV);
        galleryIV = view.findViewById(R.id.galleryIV);

        builder.setView(view);
        return builder.create();

    }
}
