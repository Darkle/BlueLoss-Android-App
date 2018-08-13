package net.ccoding.blueloss;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class NetworksViewActivity extends AppCompatActivity {
  private BlueLossSettings blueLossSettings;
  private Networks networks;
  private Discoverable discoverable;
  private NetworkInformation networkInfo;
  private View networksActivityView;
  private static NetworksViewRecyclerAdapter networksViewRecyclerAdapter;
  private static RecyclerView networksRecyclerView;
  private LinearLayoutManager networksLayoutManager;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_networks_view);
    Toolbar toolbar = (Toolbar) findViewById(R.id.networksActivityToolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Saved Networks");

    networksActivityView = findViewById(android.R.id.content);
    blueLossSettings = new BlueLossSettings(this);
    networkInfo = new NetworkInformation(this);
    networks = new Networks(this, networkInfo);
    discoverable = new Discoverable(blueLossSettings, networks);

    initializeDisplayContent();

    Button saveCurrentNetwork = findViewById(R.id.addCurrentNetworkButton);
    saveCurrentNetwork.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        networks.saveCurrentNetwork(v);
        discoverable.toggleDiscoverable();
        notifyDataChanged();
        
        networksLayoutManager.smoothScrollToPosition(
          networksRecyclerView,
          null,
          networksViewRecyclerAdapter.getItemCount() - 1
        );
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    notifyDataChanged();
  }

  private void initializeDisplayContent(){
    networksRecyclerView = findViewById(R.id.networksRecyclerView);
  
    networksLayoutManager = new LinearLayoutManager(this);
    networksRecyclerView.setLayoutManager(networksLayoutManager);

    networksViewRecyclerAdapter = new NetworksViewRecyclerAdapter(this, networks, networkInfo, discoverable);
    networksRecyclerView.setAdapter(networksViewRecyclerAdapter);
  }
  
  public static void notifyDataChanged(){
    networksViewRecyclerAdapter.notifyDataSetChanged();
  }
  
}