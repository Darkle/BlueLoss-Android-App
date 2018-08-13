package net.ccoding.blueloss;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
  private final Discoverable discoverable;

  public NetworksViewRecyclerAdapter(NetworksViewActivity mContext, Networks networks, NetworkInformation networkInfo, Discoverable discoverable) {
    this.mContext = mContext;
    this.layoutInflater = LayoutInflater.from(mContext);
    this.networks = networks;
    this.networkInfo = networkInfo;
    this.discoverable = discoverable;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = layoutInflater.inflate(R.layout.network_list_item, parent, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    int currentPosition = holder.getAdapterPosition();
    holder.mCurrentPosition = currentPosition;
    LinkedHashMap<String,String> savedNetworks = networks.getSavedNetworks();
    List keys = new ArrayList<>(savedNetworks.keySet());
    List values = new ArrayList<>(savedNetworks.values());
  
    LinkedHashMap.Entry<String,String> networkEntry = Utils.getStringMapFirstEntry(networkInfo.getNetworkInfo());
    String bssidOfCurrentNetwork = networkEntry.getKey();
    
    if(bssidOfCurrentNetwork != null && bssidOfCurrentNetwork.equals(keys.get(currentPosition).toString())){
      holder.wifiIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_wifiblue));
    }
    /*
    Not sure why but if you don't reset the original icon, sometimes it uses the blue one. ¯\_(ツ)_/¯
    */
    else{
      holder.wifiIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_wifigrey));
    }

    holder.bssidTextView.setText(keys.get(currentPosition).toString());
    holder.ssidTextView.setText(values.get(currentPosition).toString());
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
      bssidTextView = itemView.findViewById(R.id.bssidTextView);
      ssidTextView = itemView.findViewById(R.id.ssidTextView);
      wifiIcon = itemView.findViewById(R.id.wifiIcon);
      networkTrashButton = itemView.findViewById(R.id.networkTrashButton);
      
      networkTrashButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          showDeleteConfirmDialog();
        }
      });
    }
    
    private void showDeleteConfirmDialog(){
      new AlertDialog.Builder(mContext)
        .setMessage("Are you sure you want to delete \""+ ssidTextView.getText().toString() +"\" from saved networks?")
        .setPositiveButton("Delete",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                removeNetworkAndNotifyDataChange();
                dialog.dismiss();
              }
            }
        )
        .setNegativeButton("Cancel",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
              }
            }
        )
        .create()
        .show();
    }
    
    private void removeNetworkAndNotifyDataChange(){
      networks.removeNetwork(
          bssidTextView.getText().toString(),
          ssidTextView.getText().toString()
      );
      discoverable.toggleDiscoverable();
      NetworksViewActivity.notifyDataChanged();
    }
  }
}
