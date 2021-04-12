package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private EditText firstNumber;
    private EditText secondNumber;
    private Button plus;
    private Button minus;
    private TextView total;
    private Button secondActivity;

    private IntentFilter intentFilter = new IntentFilter();

    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

            boolean firstIsNumber = Pattern.matches("[0-9]+", firstNumber.getText().toString());
            boolean secondIsNumber = Pattern.matches("[0-9]+", secondNumber.getText().toString());

            Log.d("Check_Number", "Numbers are " + firstIsNumber);

            if(view.getId() == R.id.second_activity) {
                Integer result = Integer.parseInt(firstNumber.getText().toString()) + Integer.parseInt(secondNumber.getText().toString());
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
                String message = "Operation was " + firstNumber.getText().toString() + " " + ((Button) view).getText().toString() + " " + secondNumber.getText().toString();
                message = message.concat(" ");
                message =  message.concat("And total was : ");
                message = message.concat(result.toString());

                intent.putExtra("extra", message);
                startActivityForResult(intent, 1);

            } else {
                if (!firstIsNumber) {
                    Toast toast = Toast.makeText(PracticalTest01Var03MainActivity.this, "First input is wrong. Must contain integer", Toast.LENGTH_LONG);
                    toast.show();
                } else if (!secondIsNumber) {
                    Toast toast = Toast.makeText(PracticalTest01Var03MainActivity.this, "Second input is wrong. Must contain integer", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Integer result = 0;
                    if (view.getId() == R.id.plus) {
                        result = Integer.parseInt(firstNumber.getText().toString()) + Integer.parseInt(secondNumber.getText().toString());
                    }
                    if (view.getId() == R.id.minus) {
                        result = Integer.parseInt(firstNumber.getText().toString()) - Integer.parseInt(secondNumber.getText().toString());
                    }
                    total.setText(firstNumber.getText().toString() + " " + ((Button) view).getText().toString() + " " + secondNumber.getText().toString() + " = " + result);

                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                    intent.putExtra("first", firstNumber.getText().toString());
                    intent.putExtra("second", secondNumber.getText().toString());
                    getApplicationContext().startService(intent);
                }
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("first", firstNumber.getText().toString());
        savedInstanceState.putString("second", secondNumber.getText().toString());
        savedInstanceState.putString("total", total.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Toast toast = Toast.makeText(PracticalTest01Var03MainActivity.this,savedInstanceState.getString("first") + "\n" + savedInstanceState.getString("second") + "\n" +  savedInstanceState.getString("total") , Toast.LENGTH_LONG);
//          toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
            toast.show();
        } else {
            Log.d("restoring", "No entry was saved!");
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("service", intent.getStringExtra("service_extra"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        firstNumber = (EditText)findViewById(R.id.first);
        secondNumber = (EditText)findViewById(R.id.second);
        secondActivity = (Button)findViewById(R.id.second_activity);

        plus = (Button)findViewById(R.id.plus);
        minus = (Button)findViewById(R.id.minus);
        total = (TextView)findViewById(R.id.total);


        plus.setOnClickListener(buttonClickListener);
        minus.setOnClickListener(buttonClickListener);
        secondActivity.setOnClickListener(buttonClickListener);

        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.plus");
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.minus");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}