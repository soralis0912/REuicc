package org.soralis_0912.reuicc_fix;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import rikka.shizuku.Shizuku;
import rikka.shizuku.ShizukuBinderWrapper;
import rikka.shizuku.ShizukuSystemProperties;
import rikka.sui.Sui;


public class Activity extends android.app.Activity {

    private static final String EuiccGoogle = "com.google.android.euicc";
    private static final String EuiccUI = "com.android.euicc.ui";
    private static final String EsimIntro = EuiccUI + ".dsds.EsimIntroActivity";
    private static final String EuiccSettings = EuiccUI + ".settings.EuiccSettingsActivity";
    private static final String CurrentProfile = EuiccUI + ".settings.CurrentProfileListActivity";
    private boolean suiAlive;
    private boolean isRoot;


    private void onRequestPermissionsResult(int requestCode, int grantResult) {
        boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;
    }
    
    private final Shizuku.OnRequestPermissionResultListener REQUEST_PERMISSION_RESULT_LISTENER = this::onRequestPermissionsResult;

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Shizuku.addRequestPermissionResultListener(REQUEST_PERMISSION_RESULT_LISTENER);
        setContentView(R.layout.main);
        suiAlive =  checkPermission(9000);
        isRoot = Shizuku.getUid() == 0;
        if(suiAlive && isRoot){
            // EsimIntro
            findViewById(R.id.EsimIntro).setOnClickListener(view -> startActivity(new Intent().setClassName(EuiccGoogle, EsimIntro)));
            // EuiccSettings
            findViewById(R.id.EuiccSettings).setOnClickListener(view -> startActivity(new Intent().setClassName(EuiccGoogle, EuiccSettings)));
            // EuiccSettings
            findViewById(R.id.ProfileList).setOnClickListener(view -> startActivity(new Intent().setClassName(EuiccGoogle, CurrentProfile)));
        }
        else{
        }
    }

    @Override
    protected void onDestroy() {
        Shizuku.removeRequestPermissionResultListener(REQUEST_PERMISSION_RESULT_LISTENER);
    }

    private boolean checkPermission(int code) {
        if (Shizuku.isPreV11()) {
          // Pre-v11 is unsupported
          return false;
        }
      
        if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
          // Granted
          return true;
        } else if (Shizuku.shouldShowRequestPermissionRationale()) {
          // Users choose "Deny and don't ask again"
          return false;
        } else {
          // Request the permission
          Shizuku.requestPermission(code);
          return false;
        }
      }
}
