package com.github.milegema.mlgm4a;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.milegema.mlgm4a.application.MilegemaUserActivity;
import com.github.milegema.mlgm4a.classes.domains.DomainListHolder;
import com.github.milegema.mlgm4a.classes.domains.DomainService;
import com.github.milegema.mlgm4a.classes.services.LocalServices;
import com.github.milegema.mlgm4a.data.entities.DomainEntity;
import com.github.milegema.mlgm4a.errors.Errors;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.tasks.Promise;
import com.github.milegema.mlgm4a.tasks.Result;

import java.util.List;

public class DomainListActivity extends MilegemaUserActivity {

    private ListView mListView;
    private Button mButtonAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_domain_list);
        mListView = findViewById(R.id.list_items);
        mButtonAdd = findViewById(R.id.button_add);

        setupButtonToStartIntent(mButtonAdd, DomainAddActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.fetch();
    }

    private void setupButtonToStartIntent(Button btn, Class<?> activity_class) {
        btn.setOnClickListener((v) -> {
            Intent i = new Intent(this, activity_class);
            this.startActivity(i);
        });
    }

    private void fetch() {

        DomainService domain_service = LocalServices.getDomainService(this);

        Promise.init(this, DomainListHolder.class).Try(() -> {
            List<DomainEntity> list = domain_service.list(this, null);
            return new Result<>(new DomainListHolder(list));
        }).Then((res) -> {
            Logs.info("OK");
            onFetchListItems(res.getValue());
            return res;
        }).Catch((res) -> {
            Errors.handle(this, res.getError());
            return res;
        }).Finally((res) -> {
            return res;
        }).start();
    }

    private static class MyItemWrapper {
        String text;

        public MyItemWrapper(DomainEntity entity) {
            this.text = entity.getName();
        }

        @NonNull
        @Override
        public String toString() {
            return this.text;
        }
    }

    private void onFetchListItems(DomainListHolder listHolder) {
        List<DomainEntity> src = listHolder.getList();
        MyItemWrapper[] dst = new MyItemWrapper[src.size()];
        for (int i = 0; i < dst.length; i++) {
            dst[i] = new MyItemWrapper(src.get(i));
        }
        int res = android.R.layout.simple_list_item_1;
        ArrayAdapter<MyItemWrapper> adapter = new ArrayAdapter<>(this, res, dst);
        this.mListView.setAdapter(adapter);
    }

}
