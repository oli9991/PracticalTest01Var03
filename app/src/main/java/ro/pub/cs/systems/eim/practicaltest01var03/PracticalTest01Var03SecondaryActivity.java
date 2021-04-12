package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    private TextView result;
    private Button correct, incorrect;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.corect:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.incorrect:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        result = (TextView) findViewById(R.id.result);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("extra")) {
            String message = intent.getStringExtra("extra");
            result.setText(message);
        }

        correct = (Button)findViewById(R.id.corect);
        correct.setOnClickListener(buttonClickListener);

        incorrect = (Button)findViewById(R.id.incorrect);
        incorrect.setOnClickListener(buttonClickListener);

    }
}