package org.soralis_0912.reuicc_fix;

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

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.EsimIntro).setOnClickListener(view -> {
            try {
                Shell.getShell(shell -> {
                    Intent intent = new Intent();
                    intent.setClassName(EuiccGoogle, EsimIntro);
                    startActivity(intent);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "アクティビティの起動に失敗しました", Toast.LENGTH_SHORT).show();
            }
        });

        // EuiccSettings
        findViewById(R.id.EuiccSettings).setOnClickListener(view -> {
            try {
                Shell.getShell(shell -> {
                    Intent intent = new Intent();
                    intent.setClassName(EuiccGoogle, EuiccSettings);
                    startActivity(intent);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "アクティビティの起動に失敗しました", Toast.LENGTH_SHORT).show();
            }
        });

        // ProfileListを起動
        findViewById(R.id.ProfileList).setOnClickListener(view -> {
            try {
                Shell.getShell(shell -> {
                    Intent intent = new Intent();
                    intent.setClassName(EuiccGoogle, CurrentProfile);
                    startActivity(intent);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "アクティビティの起動に失敗しました", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
