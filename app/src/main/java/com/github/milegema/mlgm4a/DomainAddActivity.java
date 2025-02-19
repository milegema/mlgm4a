package com.github.milegema.mlgm4a;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.MilegemaUserActivity;
import com.github.milegema.mlgm4a.classes.domains.DomainService;
import com.github.milegema.mlgm4a.classes.services.LocalServices;
import com.github.milegema.mlgm4a.data.entities.DomainEntity;
import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.tasks.Promise;
import com.github.milegema.mlgm4a.tasks.Result;

public class DomainAddActivity extends MilegemaUserActivity {

    private Button mButtonOK;
    private EditText mInputDomainName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_domain_add);
        mButtonOK = findViewById(R.id.button_ok);
        mInputDomainName = findViewById(R.id.input_domain_name);

        mButtonOK.setOnClickListener(this::handleClickButtonOK);
    }

    private void handleClickButtonOK(View view) {


        final DomainEntity item = new DomainEntity();
        final DomainService ds = LocalServices.getDomainService(this);

        String dn = this.mInputDomainName.getText().toString();

        item.setName(dn);
        item.setLabel("-");
        item.setDescription("-");

        Promise.init(this, DomainEntity.class).Try(() -> {
            DomainEntity item2 = ds.insert(this, item);
            return new Result<>(item2);
        }).Then((res) -> {
            this.finish();
            return res;
        }).Catch((res) -> {
            Errors.handle(this, res.getError());
            return res;
        }).Finally((res) -> {
            return res;
        }).start();
    }

}
