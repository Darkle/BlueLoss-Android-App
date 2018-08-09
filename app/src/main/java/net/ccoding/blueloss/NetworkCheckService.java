package net.ccoding.blueloss;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import io.hypertrack.smart_scheduler.Job;
import io.hypertrack.smart_scheduler.SmartScheduler;

public class NetworkCheckService extends JobIntentService {
  private static final int jobId = 1;
  private static final int fiveSecondsInMilliseconds = 5000;
  private static final String jobPeriodicTaskTag = "net.ccoding.blueloss.periodicNetworkCheck";
//  private BlueLossSettings blueLossSettings;
//  private Networks networks;
//  private Discoverable discoverable;
//  private NetworkInformation networkInfo;
//  private NetworkCheckService networkCheckService;

  {
    Logger.addLogAdapter(new AndroidLogAdapter());
  }

  // Needs a zero argument constructor for some reason.
//  public NetworkCheckService() {
//  }
//
//  public NetworkCheckService() {
////    this.blueLossSettings = new BlueLossSettings(this);
////    this.networkInfo = new NetworkInformation(this);
////    this.networks = new Networks(this, networkInfo);
////    this.discoverable = new Discoverable(blueLossSettings, networks);
//    this.blueLossSettings = blueLossSettings;
//    this.networkInfo = networkInfo;
//    this.networks = networks;
//    this.discoverable = discoverable;
//  }

  public void enqueueWork(Context context, Intent work) {
    enqueueWork(context, NetworkCheckService.class, jobId, work);
  }

  @Override
  protected void onHandleWork(@NonNull Intent intent) {
//    Logger.d(discoverable);
    SmartScheduler.JobScheduledCallback callback = new SmartScheduler.JobScheduledCallback() {
      @Override
      public void onJobScheduled(Context context, Job job) {
        Logger.d("onJobScheduled called");

        BlueLossSettings blueLossSettings = new BlueLossSettings(NetworkCheckService.this);
        NetworkInformation networkInfo = new NetworkInformation(NetworkCheckService.this);
        Networks networks = new Networks(NetworkCheckService.this, networkInfo);
        Discoverable discoverable = new Discoverable(blueLossSettings, networks);

        if(discoverable.shouldSetToDiscoverable()){
          discoverable.setDiscoverable();
        }
        else {
          discoverable.setUnDiscoverable();
        }
      }
    };

    Job job = new Job.Builder(jobId, callback, Job.Type.JOB_TYPE_PERIODIC_TASK, jobPeriodicTaskTag)
        .setRequiredNetworkType(Job.NetworkType.NETWORK_TYPE_ANY)
        .setPeriodic(fiveSecondsInMilliseconds)
        .build();

    SmartScheduler.getInstance(NetworkCheckService.this).addJob(job);
  }
}
