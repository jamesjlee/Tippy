package in.jamesjlee.tippy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main extends Activity {
    public EditText bill;
    public EditText billBox;
    public EditText tip;
    public EditText tipBox;
    public EditText tipAmount;
    public EditText tipAmountBox;
    public EditText grandTotal;
    public EditText grandTotalBox;
    public Button calc;
    public TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bill = (EditText) findViewById(R.id.bill);
        billBox = (EditText) findViewById(R.id.billBox);
        tip = (EditText) findViewById(R.id.tip);
        tipBox = (EditText) findViewById(R.id.tipBox);
        tipAmount = (EditText) findViewById(R.id.tipAmount);
        tipAmountBox = (EditText) findViewById(R.id.tipAmountBox);
        grandTotal = (EditText) findViewById(R.id.grandTotal);
        grandTotalBox = (EditText) findViewById(R.id.grandTotalBox);
        error = (TextView) findViewById(R.id.error);
        calc = (Button) findViewById(R.id.calc);
        error.setText("");

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText("");
                if (billBox.getText().toString().matches("\\d+|\\d*\\.\\d+|\\d+\\.\\d*") && tipBox.getText().toString().matches("\\d+|\\d*\\.\\d+|\\d+\\.\\d*")) {
                    if(billBox.getText().toString() != null && billBox.getText().toString() != "" && tipBox.getText().toString() != null && tipBox.getText().toString() != "") {
                        BigDecimal bill = new BigDecimal(billBox.getText().toString());
                        BigDecimal tip = new BigDecimal(tipBox.getText().toString());
                        BigDecimal oneHundred = new BigDecimal("100");
                        BigDecimal tipPercent = tip.divide(oneHundred, 3, RoundingMode.HALF_EVEN);
                        BigDecimal tipAmount = bill.multiply(tipPercent);
                        BigDecimal grandTotal = bill.add(tipAmount);
                        tipAmountBox.setText(tipAmount.setScale(2, RoundingMode.HALF_EVEN).toString());
                        grandTotalBox.setText(grandTotal.setScale(2, RoundingMode.HALF_EVEN).toString());
                    }
                }
                else {
                    error.setText("Please enter numbers only.");
                }
            }
        });

        tipBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromInputMethod(tipBox.getApplicationWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        billBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromInputMethod(billBox.getApplicationWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
