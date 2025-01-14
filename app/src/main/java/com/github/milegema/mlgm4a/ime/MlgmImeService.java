package com.github.milegema.mlgm4a.ime;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.EditorInfo;

public class MlgmImeService extends InputMethodService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onInitializeInterface() {
        super.onInitializeInterface();
    }

    @Override
    public void onBindInput() {
        super.onBindInput();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
    }

    @Override
    public View onCreateInputView() {
        // return super.onCreateInputView();
        MlgmImeView view = new MlgmImeView(this);
        view.setImeService(this);
        return view;
    }

    @Override
    public void onStartInputView(EditorInfo editorInfo, boolean restarting) {
        super.onStartInputView(editorInfo, restarting);
    }
}
