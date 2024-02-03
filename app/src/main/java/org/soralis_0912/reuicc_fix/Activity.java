package org.soralis_0912.reuicc_fix;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.topjohnwu.superuser.Shell;

public class Activity extends android.app.Activity {

    private static final String EuiccGoogle = "com.google.android.euicc";
    private static final String EuiccUI = "com.android.euicc.ui";
    private static final String EsimIntro = EuiccUI + ".dsds.EsimIntroActivity";
    private static final String EuiccSettings = EuiccUI + ".settings.EuiccSettingsActivity";
    private static final String CurrentProfile = EuiccUI + ".settings.CurrentProfileListActivity";
    private static final String ACTION_MANAGE_EMBEDDED_SUBSCRIPTIONS = "android.service.euicc.action.MANAGE_EMBEDDED_SUBSCRIPTIONS";


    public void tryStartActivity(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "アクティビティが存在しません", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "起動が拒否されました", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.EsimIntro).setOnClickListener(view -> {

            Shell.getShell(shell -> {

                Intent intent = new Intent();
                intent.setClassName(EuiccGoogle, EsimIntro);
                tryStartActivity(intent);
            });
        });

        // EuiccSettings
        findViewById(R.id.EuiccSettings).setOnClickListener(view -> {

            Shell.getShell(shell -> {

                Intent intent = new Intent();
                intent.setClassName(EuiccGoogle, EuiccSettings);
                tryStartActivity(intent);
            });
        });

        // ProfileListを起動
        findViewById(R.id.ProfileList).setOnClickListener(view -> {

            Shell.getShell(shell -> {

                Intent intent = new Intent();
                intent.setClassName(EuiccGoogle, CurrentProfile);
                intent.setAction(ACTION_MANAGE_EMBEDDED_SUBSCRIPTIONS);
                tryStartActivity(intent);
            });
        });

    }
}
