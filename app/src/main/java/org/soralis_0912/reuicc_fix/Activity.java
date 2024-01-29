package org.soralis_0912.reuicc_fix;

import android.content.Intent;
import android.os.Bundle;

public class Activity extends android.app.Activity {

    private static final String EuiccGoogle = "com.google.android.euicc";
    private static final String EuiccUI = "com.android.euicc.ui";
    private static final String EsimIntro = EuiccUI + ".dsds.EsimIntroActivity";
    private static final String EuiccSettings = EuiccUI + ".settings.EuiccSettingsActivity";

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // EsimIntro
        findViewById(R.id.EsimIntro).setOnClickListener(view -> startActivity(new Intent().setClassName(EuiccGoogle, EsimIntro)));
        // EuiccSettings
        findViewById(R.id.EuiccSettings).setOnClickListener(view -> startActivity(new Intent().setClassName(EuiccGoogle, EuiccSettings)));

    }
}