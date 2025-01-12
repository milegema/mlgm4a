package com.github.milegema.mlgm4a.frontend;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.milegema.mlgm4a.logs.AndroidLogger;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.web.DefaultWebClientFactory;
import com.github.milegema.mlgm4a.network.web.WebClient;
import com.github.milegema.mlgm4a.network.web.WebClientFactory;
import com.github.milegema.mlgm4a.network.web.WebMethod;
import com.github.milegema.mlgm4a.network.web.WebRequest;
import com.github.milegema.mlgm4a.network.web.WebResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        AndroidLogger.init();

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread th = new Thread(this::tryFetch);
        th.start();
    }

    private void tryFetch() {
        WebRequest req = new WebRequest();
        req.setMethod(WebMethod.GET);
        req.setUrl("https://gitee.com/milegema.git");
        try {
            WebClientFactory factory = new DefaultWebClientFactory();
            WebClient client = factory.create(null);
            WebResponse resp = client.execute(req);
            Logs.info(resp.getStatus().getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
