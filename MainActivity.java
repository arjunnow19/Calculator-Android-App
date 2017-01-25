package com.ceetle.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null; //class double rather than primitive type double. why used the class?
    //since even "0" is a  valid input, we need to assign a null value there.
//    private Double operand2 = null;
    private String pendingOperation = "=";
    private static final String TAG = "Activity Main";
    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //bundle is the the object that is used to carry the data around.
        Log.d(TAG, "onCreate: in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);

        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //here onClck function picks any buttons you pass

                Button b = (Button) v;
                newNumber.append(b.getText().toString());

            }
        };

        //here we have created an listener object and then passed the lister objec to various
        //set of buttons.

        button0.setOnClickListener(listener); button1.setOnClickListener(listener);
        button2.setOnClickListener(listener); button3.setOnClickListener(listener);
        button4.setOnClickListener(listener); button5.setOnClickListener(listener);
        button6.setOnClickListener(listener); button7.setOnClickListener(listener);
        button8.setOnClickListener(listener); button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener buttonNegListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button b = (Button) v;


                String value = newNumber.getText().toString();
                if (value.length() == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        Double doublevalue = Double.valueOf(value);
                        doublevalue *= -1;

                        newNumber.setText(doublevalue.toString());

                    } catch (NumberFormatException e) {
                        //new number was "-" or "."

                        newNumber.setText(" ");
                    }

                }


            }
        };
        buttonNeg.setOnClickListener(buttonNegListener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here onclick function picks any button that you pass.

                Button b = (Button) v;
                String operator = b.getText().toString();
                String value = newNumber.getText().toString();

                try {
                    Double doubleValue = Double.valueOf(value); //we are changing the value to double.
                    //when we changed by passing the value to the ValueOf() class.
                    performOperation(doubleValue, operator);

                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = operator;
                displayOperation.setText(pendingOperation); //show what operation is going there
            }
        };
        buttonDivide.setOnClickListener(opListener); buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener); buttonPlus.setOnClickListener(opListener);
        buttonEquals.setOnClickListener(opListener);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //here we are saving objects as STATICS variables. pendingOperation to STATE_PENDING_OPERATION
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }
    private void performOperation(Double value, String operation) //these are parameters.
    {
        if (null == operand1) {
            operand1 = value;
        } else {
            // operand2 = value;

            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            //if the pending operation is = then assign to operation otherwise look at the following
            //switch cases
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0; //since you are dividing by zero result should be zero.
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
            }
        }
        //dont append, set text.

        result.setText(operand1.toString());
        newNumber.setText(" ");
    }
}







