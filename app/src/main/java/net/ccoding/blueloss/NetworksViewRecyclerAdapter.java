package net.ccoding.blueloss;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class NetworksViewRecyclerAdapter extends RecyclerView.Adapter<NetworksViewRecyclerAdapter.ViewHolder>{
  private final Context mContext;
  private final LayoutInflater layoutInflater;
  private final Networks networks;
  private final NetworkInformation networkInfo;

  public NetworksViewRecyclerAdapter(Context mContext, Networks networks, NetworkInformation networkInfo) {
    this.mContext = mContext;
    this.layoutInflater = LayoutInflater.from(mContext);
    this.networks = networks;
    this.networkInfo = networkInfo;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = layoutInflater.inflate(R.layout.network_list_item, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.mCurrentPosition = position;
    LinkedHashMap<String,String> savedNetworks = networks.getSavedNetworks();
    List keys = new ArrayList<>(savedNetworks.keySet());
    List values = new ArrayList<>(savedNetworks.values());

    LinkedHashMap.Entry<String,String> networkEntry = Utils.getStringMapFirstEntry(networkInfo.getNetworkInfo());
    String bssidOfCurrentNetwork = networkEntry.getKey();

    if(bssidOfCurrentNetwork != null && bssidOfCurrentNetwork.equals(keys.get(position))){
      holder.wifiIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_wifiblue));
    }

    holder.bssidTextView.setText((String)keys.get(position));
    holder.ssidTextView.setText((String)values.get(position));
  }

  @Override
  public int getItemCount() {
    LinkedHashMap<String,String> savedNetworks = networks.getSavedNetworks();
    return savedNetworks.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    public final TextView ssidTextView;
    public final TextView bssidTextView;
    public final ImageView wifiIcon;
    public final ImageButton networkTrashButton;
    public int mCurrentPosition;

    public ViewHolder(View itemView) {
      super(itemView);
      ssidTextView = itemView.findViewById(R.id.ssidTextView);
      bssidTextView = itemView.findViewById(R.id.bssidTextView);
      wifiIcon = itemView.findViewById(R.id.wifiIcon);
      networkTrashButton = itemView.findViewById(R.id.networkTrashButton);

      networkTrashButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          networks.removeNetwork(
            bssidTextView.getText().toString(),
            ssidTextView.getText().toString()
          );
        }
      });
    }
  }
}
