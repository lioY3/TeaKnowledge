package com.example.teaknowledge.ui.version;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teaknowledge.BuildConfig;
import com.example.teaknowledge.R;

public class VersionFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_version, container, false);

        TextView textAppVersion = root.findViewById(R.id.text_app_version);
        textAppVersion.setText("应用版本：" + BuildConfig.VERSION_NAME);

        return root;
    }
}