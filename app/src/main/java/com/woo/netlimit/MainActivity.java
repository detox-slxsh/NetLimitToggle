package com.woo.netlimit;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    // Values in kbps (-1 = no limit)
    public static final int[] LIMIT_VALUES = {-1, 128, 256, 1024, 5120, 15360};
    public static final String[] LIMIT_LABELS = {"No Limit", "128 kbps", "256 kbps", "1 Mbps", "5 Mbps", "15 Mbps"};
    public static final String[] LIMIT_SUBLABELS = {"Unlimited", "Very Slow", "Slow", "Moderate", "Fast", "Very Fast"};

    private RecyclerView recyclerView;
    private LimitTileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new LimitTileAdapter(this, getCurrentLimit(), (value) -> {
            setLimit(value);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.setActiveValue(getCurrentLimit());
        }
    }

    public int getCurrentLimit() {
        try {
            String val = Settings.Global.getString(getContentResolver(), "network_download_rate_limit");
            if (val == null || val.isEmpty()) return -1;
            return Integer.parseInt(val);
        } catch (Exception e) {
            return -1;
        }
    }

    public void setLimit(int kbps) {
        try {
            String value = kbps == -1 ? "" : String.valueOf(kbps);
            Settings.Global.putString(getContentResolver(), "network_download_rate_limit", value);
            adapter.setActiveValue(kbps);
            String label = kbps == -1 ? "No Limit" : (kbps < 1024 ? kbps + " kbps" : (kbps / 1024) + " Mbps");
            Toast.makeText(this, "Set to: " + label, Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this,
                "Permission needed!\nRun: adb shell pm grant " + getPackageName() + " android.permission.WRITE_SECURE_SETTINGS",
                Toast.LENGTH_LONG).show();
        }
    }
}
