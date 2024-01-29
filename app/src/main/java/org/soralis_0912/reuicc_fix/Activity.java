package org.soralis_0912.reuicc_fix;

import android.widget.Toast;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import rikka.shizuku.Shizuku;
import rikka.shizuku.ShizukuBinderWrapper;
import rikka.shizuku.server.ShizukuService;
import rikka.sui.Sui;
import rikka.shizuku.ShizukuApiConstants;



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
            ShizukuBinderWrapper wrapper = ShizukuService.getBinder();
            // EsimIntro
            findViewById(R.id.EsimIntro).setOnClickListener(view -> {
                try {
                    Intent intent = new Intent();
                    intent.setClassName(EuiccGoogle, EsimIntro);
                    ShizukuService.transactRemote(wrapper, ShizukuApiConstants.REQUEST_CODE_AUTHENTICATE, null, null);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "アクティビティの起動に失敗しました", Toast.LENGTH_SHORT).show();
                }
            });
            // EuiccSettings
            findViewById(R.id.EuiccSettings).setOnClickListener(view -> {
                try {
                    Intent intent = new Intent();
                    intent.setClassName(EuiccGoogle, EuiccSettings);
                    ShizukuService.transactRemote(wrapper, ShizukuApiConstants.REQUEST_CODE_AUTHENTICATE, null, null);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "アクティビティの起動に失敗しました", Toast.LENGTH_SHORT).show();
                }
            });

            // ProfileListを起動
            findViewById(R.id.ProfileList).setOnClickListener(view -> {
                try {
                    Intent intent = new Intent();
                    intent.setClassName(EuiccGoogle, CurrentProfile);
                    ShizukuService.transactRemote(wrapper, ShizukuApiConstants.REQUEST_CODE_AUTHENTICATE, null, null);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "アクティビティの起動に失敗しました", Toast.LENGTH_SHORT).show();
                }
            });
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
