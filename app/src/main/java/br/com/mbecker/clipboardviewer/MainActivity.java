package br.com.mbecker.clipboardviewer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CharSequence emptyText;
    private CharSequence errorReadText;
    private CharSequence successCopyText;
    private CharSequence successClearText;
    private ClipboardManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyText = getResources().getText(R.string.empty);
        errorReadText = getResources().getText(R.string.error_read_text);
        successCopyText = getResources().getText(R.string.success_copy_text);
        successClearText = getResources().getText(R.string.success_clear_text);
        cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView textView = findViewById(R.id.editTextTextMultiLine);

        if (cm.hasPrimaryClip()) {
            ClipData clip = cm.getPrimaryClip();
            textView.setText(clip.getItemAt(0).getText());
        } else {
            Toast.makeText(getApplicationContext(), errorReadText, Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.btnClear).setOnClickListener(view -> {
            cm.setPrimaryClip(ClipData.newPlainText(emptyText, emptyText));
            textView.setText(cm.getPrimaryClip().getItemAt(0).getText());

            Toast.makeText(getApplicationContext(), successClearText, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnCopy).setOnClickListener(view -> {
            cm.setPrimaryClip(ClipData.newPlainText(emptyText, textView.getText()));
            Toast.makeText(getApplicationContext(), successCopyText, Toast.LENGTH_SHORT).show();
        });
    }
}