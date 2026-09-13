package org.telegram.messenger.gramify;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.widget.*;

import android.app.AlertDialog;
import org.telegram.messenger.AndroidUtilities;

/**
 * APP PIN LOCK - 709177
 * Application khulte hi ye pin mangega
 * 
 * APK Name: Devgram
 * Logo: Telegram Black Icon (https://uxwing.com/telegram-black-icon/)
 * Pin: 709177
 */

public class AppPinLock_709177 {

    public static final String APP_PIN = "709177"; // Aapne diya naya pin
    public static final String BOT_PIN = "56530"; // Purana bot wala pin
    public static final String PREF = "devgram_app_pin_pref";
    public static final String KEY_APP_UNLOCKED = "app_unlocked";

    // App open hote hi call karo - LaunchActivity me
    public static void checkAppPin(Context context, Runnable onUnlocked) {
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        // Agar already unlocked hai (same session me) to direct
        // Har baar app open pe pin mangna hai to ye check hata do
        // if (pref.getBoolean(KEY_APP_UNLOCKED, false)) { onUnlocked.run(); return; }

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(dp(context, 24), dp(context, 24), dp(context, 24), dp(context, 24));
        layout.setBackgroundColor(Color.BLACK);
        layout.setGravity(Gravity.CENTER);

        // Logo - Telegram Black Icon jaisa
        TextView logoIcon = new TextView(context);
        logoIcon.setText("✈️"); // Black telegram plane jaisa, ya ImageView se PNG use karo
        logoIcon.setTextSize(48);
        logoIcon.setGravity(Gravity.CENTER);
        logoIcon.setTextColor(Color.WHITE);
        // Gradient background black circle
        GradientDrawable logoBg = new GradientDrawable();
        logoBg.setShape(GradientDrawable.OVAL);
        logoBg.setColor(Color.BLACK);
        logoBg.setStroke(dp(context, 2), Color.WHITE);
        logoIcon.setBackground(logoBg);
        LinearLayout.LayoutParams logoLp = new LinearLayout.LayoutParams(dp(context, 80), dp(context, 80));
        logoLp.gravity = Gravity.CENTER;
        logoLp.bottomMargin = dp(context, 16);
        logoIcon.setLayoutParams(logoLp);
        layout.addView(logoIcon);

        TextView title = new TextView(context);
        title.setText("Devgram");
        title.setTextColor(Color.WHITE);
        title.setTextSize(28);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        TextView remastered = new TextView(context);
        remastered.setText("Remastered by Dev");
        remastered.setTextColor(0xFFAAAAAA);
        remastered.setTextSize(12);
        remastered.setGravity(Gravity.CENTER);
        remastered.setPadding(0, dp(context, 4), 0, dp(context, 20));
        layout.addView(remastered);

        TextView lockText = new TextView(context);
        lockText.setText("🔒 App Locked\nEnter Pin to Continue");
        lockText.setTextColor(Color.WHITE);
        lockText.setGravity(Gravity.CENTER);
        lockText.setPadding(0, 0, 0, dp(context, 16));
        layout.addView(lockText);

        EditText pinInput = new EditText(context);
        pinInput.setHint("Enter App Pin: 709177");
        pinInput.setHintTextColor(0xFF888888);
        pinInput.setTextColor(Color.WHITE);
        pinInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        pinInput.setGravity(Gravity.CENTER);
        pinInput.setBackground(createEditBg());
        pinInput.setPadding(dp(context, 16), dp(context, 16), dp(context, 16), dp(context, 16));
        layout.addView(pinInput);

        TextView hint = new TextView(context);
        hint.setText("Bot Feature Pin: 56530\nApp Pin: 709177");
        hint.setTextColor(0xFF666666);
        hint.setTextSize(10);
        hint.setGravity(Gravity.CENTER);
        hint.setPadding(0, dp(context, 12), 0, 0);
        layout.addView(hint);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(layout)
                .setCancelable(false) // Back press se band nahi hoga
                .setPositiveButton("Unlock App", null)
                .setNegativeButton("Exit", (d, w) -> {
                    System.exit(0);
                })
                .create();

        dialog.setOnShowListener(d -> {
            Button unlockBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            unlockBtn.setTextColor(Color.BLACK);
            unlockBtn.setBackgroundColor(Color.WHITE);
            unlockBtn.setOnClickListener(v -> {
                String entered = pinInput.getText().toString().trim();
                if (APP_PIN.equals(entered)) {
                    // pref.edit().putBoolean(KEY_APP_UNLOCKED, true).apply();
                    Toast.makeText(context, "✅ Welcome to Devgram - Remastered by Dev", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    onUnlocked.run();
                } else {
                    pinInput.setError("❌ Wrong App Pin! Use 709177");
                    Toast.makeText(context, "❌ Wrong Pin", Toast.LENGTH_SHORT).show();
                }
            });
            Button exitBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            exitBtn.setTextColor(Color.WHITE);
        });

        dialog.show();
    }

    // Logo ke liye - ic_launcher.xml me ye use karo
    // PNG jo download kiya hai: DevgramFixed/devgram_black_logo.png
    // Isko app/src/main/res/drawable/ic_launcher.png me daal do
    // Ya SVG use karo: https://uxwing.com/wp-content/themes/uxwing/download/brands-and-social-media/telegram-black-icon.svg

    private static GradientDrawable createEditBg() {
        GradientDrawable d = new GradientDrawable();
        d.setColor(0xFF1A1A1A);
        d.setCornerRadius(12);
        d.setStroke(2, Color.WHITE);
        return d;
    }

    private static int dp(Context c, int v) {
        return (int) (v * c.getResources().getDisplayMetrics().density);
    }

    /*
    Usage in LaunchActivity.java:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Pehle App Pin check
        AppPinLock_709177.checkAppPin(this, () -> {
            // Pin sahi hai tabhi aage ka onboarding + main app khulega
            showOnboardingOrMain();
        });
    }

    private void showOnboardingOrMain() {
        // Yahan aapka force join wala onboarding
        // https://t.me/tech_zone_dev + https://t.me/high_table_dev
    }
    */
}
