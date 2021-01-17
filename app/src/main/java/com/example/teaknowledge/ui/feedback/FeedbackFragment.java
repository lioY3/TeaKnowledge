package com.example.teaknowledge.ui.feedback;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.teaknowledge.R;

public class FeedbackFragment extends Fragment {

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_feedback, container, false);
        final Context context = root.getContext();

        Button buttonFeedback = root.findViewById(R.id.button_feedback);
        buttonFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        context,
                        "感谢您的反馈！至于有没有人能收到这个反馈，那咱就不敢说了。",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        return root;
    }
}