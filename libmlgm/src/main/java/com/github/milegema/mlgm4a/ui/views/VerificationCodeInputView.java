package com.github.milegema.mlgm4a.ui.views;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.libmlgm.R;
import com.github.milegema.mlgm4a.utils.Time;

public class VerificationCodeInputView extends LinearLayout {

    private Button mButtonSend;
    private EditText mEditVCode;
    private String mSendButtonText;
    private int mTimerCurrent; // in seconds
    private int mTimerInitial; // in seconds
    private Handler mCurrentHandler;


    public VerificationCodeInputView(Context context) {
        super(context);
        this.load_layout();
    }

    public VerificationCodeInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.load_layout();
    }

    public VerificationCodeInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.load_layout();
    }

    public VerificationCodeInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.load_layout();
    }


    public void setCode(String code) {
        this.mEditVCode.setText(code);
    }

    public String getCode() {
        Editable code = this.mEditVCode.getText();
        return String.valueOf(code);
    }

    public void setOnClickSendListener(@Nullable OnClickListener l) {
        this.mButtonSend.setOnClickListener(l);
    }

    public void startTimer() {
        final Handler h = new Handler();
        Thread th = new Thread(() -> {
            this.runTimer(h);
        });
        this.mCurrentHandler = h;
        th.start();
    }


    public void reset() {
        this.mCurrentHandler = null;
        this.mButtonSend.setEnabled(true);
        this.mButtonSend.setText(this.mSendButtonText);
    }

    // private

    private void load_layout() {
        View view = ViewUtils.loadLayout(R.layout.view_input_verification_code, this);
        this.mEditVCode = view.findViewById(R.id.edit_text_verification_code);
        this.mButtonSend = view.findViewById(R.id.button_send_verification_code);
        this.mSendButtonText = this.mButtonSend.getText().toString();
        this.mTimerInitial = 60;
    }

    private void runTimer(Handler h) {
        for (mTimerCurrent = mTimerInitial; mTimerCurrent > 0; mTimerCurrent--) {
            if (!h.equals(this.mCurrentHandler)) {
                break;
            }
            h.post(this::updateDisplay);
            Time.sleep(1000);
        }
        h.post(this::reset);
    }

    private void updateDisplay() {
        int current = this.mTimerCurrent;
        String text = this.mSendButtonText + '(' + current + ')';
        this.mButtonSend.setEnabled(false);
        this.mButtonSend.setText(text);
    }
}
