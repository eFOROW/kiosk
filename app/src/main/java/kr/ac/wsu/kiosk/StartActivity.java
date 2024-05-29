package kr.ac.wsu.kiosk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button pickup_Btn = (Button) findViewById(R.id.pickup_Button);
        Button sit_Btn = (Button) findViewById(R.id.sit_Button);

        Button.OnClickListener listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.pickup_Button) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra("info", "pickup");
                    startActivity(intent);
                } else if (id == R.id.sit_Button) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    intent.putExtra("info", "sit");
                    startActivity(intent);
                }
            }
        };
        pickup_Btn.setOnClickListener(listener);
        sit_Btn.setOnClickListener(listener);
    }
}