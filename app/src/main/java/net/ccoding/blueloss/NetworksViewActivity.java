package net.ccoding.blueloss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class NetworksViewActivity extends AppCompatActivity {
  private BlueLossSettings blueLossSettings;
  private Networks networks;
  private Discoverable discoverable;
  private NetworkInformation networkInfo;
  private static View networksActivityView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_networks_view);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    networksActivityView = findViewById(android.R.id.content);
    blueLossSettings = new BlueLossSettings(this);
    networkInfo = new NetworkInformation(this);
    networks = new Networks(this, networkInfo);
    discoverable = new Discoverable(blueLossSettings, networks);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//    Button saveNetwork = findViewById(R.id.saveButton);
//    saveNetwork.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        networks.saveCurrentNetwork();
//        discoverable.toggleDiscoverable();
//        MyLogger.d(networkInfo.getNetworkInfo());
//
//      }
//    });
//
//    Button removeNetwork = findViewById(R.id.removeButton);
//    removeNetwork.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
////        networks.removeNetwork();
//        discoverable.toggleDiscoverable();
//      }
//    });

  }

}