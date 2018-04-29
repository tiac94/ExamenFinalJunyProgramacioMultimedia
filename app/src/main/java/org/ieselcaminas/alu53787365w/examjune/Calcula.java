package org.ieselcaminas.alu53787365w.examjune;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Calcula extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private double acumula;
    private boolean erase;
    private enum Operator {NONE, ADD, SUBTRACT, MULTIPLY, DIVIDE}

    private double accumulator, operand;
    private Operator operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula);

        initCalculator();

        editText = (EditText) findViewById(R.id.editText);
        setButtonListeners();


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        final int id = bundle.getInt("id");
        editText.setText(name);

        Button buttonOk = (Button) findViewById(R.id.OK);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                if (id == 1234){
                    Intent intent = new Intent();
                    intent.putExtra("data", s);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                if (id == 1235){
                    Intent intent = new Intent();
                    intent.putExtra("data2", s);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void initCalculator() {
        operator = Operator.NONE;
        accumulator = 0;
        operand = 0;
        erase = true;
    }

    public void setButtonListeners() {
        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(this);
        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(this);
        Button b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(this);
        Button b4 = (Button) findViewById(R.id.button4);
        b4.setOnClickListener(this);
        Button b5 = (Button) findViewById(R.id.button5);
        b5.setOnClickListener(this);
        Button b6 = (Button) findViewById(R.id.button6);
        b6.setOnClickListener(this);
        Button b7 = (Button) findViewById(R.id.button7);
        b7.setOnClickListener(this);
        Button b8 = (Button) findViewById(R.id.button8);
        b8.setOnClickListener(this);
        Button b9 = (Button) findViewById(R.id.button9);
        b9.setOnClickListener(this);
        Button b0 = (Button) findViewById(R.id.button0);
        b0.setOnClickListener(this);
        Button backSpace = (Button) findViewById(R.id.buttonBack);
        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (s!=null && !s.equals("")) {
                   s = s.substring(0,s.length()-1);
                    editText.setText(s);
                }
            }
        });
        Button buttonC = (Button) findViewById(R.id.buttonC);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        Button comma = (Button) findViewById(R.id.buttonComma);
        comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (!s.contains(".")) {
                    s+=".";
                    editText.setText(s);
                    erase=false;
                }
            }
        });
        Button add = (Button) findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!erase) {
                    displayResult();
                }
                operator = Operator.ADD;
            }
        });
        Button sub = (Button) findViewById(R.id.buttonSub);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!erase) {
                    displayResult();
                }
                operator = Operator.SUBTRACT;
            }
        });
        Button mult = (Button) findViewById(R.id.buttonMult);
        mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!erase) {
                    displayResult();
                }
                operator = Operator.MULTIPLY;
            }
        });
        Button div = (Button) findViewById(R.id.buttonDiv);
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!erase) {
                    displayResult();
                }
                operator = Operator.DIVIDE;
            }
        });

        Button equal = (Button) findViewById(R.id.buttonEqual);
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!erase) {
                    displayResult();
                }
                operator=Operator.NONE;
            }
        });

    }

    public void displayResult() {
        String s = editText.getText().toString();
        operand = Double.parseDouble(s);
        accumulator = calculate();
        editText.setText(formatDisplay(accumulator));
        erase=true;
    }

    private double calculate() {
        switch (operator) {
            case ADD:
                return accumulator+operand;
            case SUBTRACT:
                return accumulator-operand;
            case DIVIDE:
                return accumulator/operand;
            case MULTIPLY:
                return accumulator*operand;

        }
        return operand;
    }

    private String formatDisplay(double number) {
        String s = String.format("%f", number);
        s = s.replaceAll("0+$", "");
        s = s.replaceAll("\\.$", "");
        return s;
    }

    public void onClick(View v) {

        Button b = (Button) v;
        switch (v.getId()) {
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
            case R.id.button0:
                if (erase) {
                    editText.setText("");
                    erase=false;
                }
                String s = editText.getText().toString();
                s += b.getText();
                editText.setText(s);
                break;
        }


    }


}