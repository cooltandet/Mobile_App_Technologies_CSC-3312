package edu.highpoint.tipapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // Variable declarations for UI components

    // Buttons
    Button buttonPlusTip;
    Button buttonMinusTip;

    // EditTexts
    EditText editTextNumberDecimalBillTotal;
    EditText editTextNumberTipPrecent;
    EditText editTextNumberDecimalTipAmount;
    EditText editTextNumberDecimalTotalDue;//editTextNumberDecimalFinal
    EditText editTextnumberPeopleinParty;
    EditText editTextNumberDecimalTotalPerPerson;
    SeekBar seekBarTipBarPercent;

    // Variables for calculations
    Double BillAmount;
    Integer TipPercent;
    Double TipAmount;
    Double FinalAmount;
    Double Money;

    Integer PeopleinParty;
    Double FinalAmountPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize buttons
        buttonPlusTip = (Button) findViewById(R.id.buttonPlusTip);
        buttonMinusTip = (Button) findViewById(R.id.buttonMinusTip);

        // Initialize EditTexts
        editTextNumberDecimalBillTotal = (EditText) findViewById(R.id.editTextNumberDecimalBillTotal) ;
        editTextNumberTipPrecent = (EditText) findViewById(R.id.editTextNumberTipPrecent);
        editTextNumberDecimalTipAmount = (EditText) findViewById(R.id.editTextNumberDecimalTipAmount);
        editTextNumberDecimalTotalDue = (EditText) findViewById(R.id.editTextNumberDecimalTotalDue);
        editTextnumberPeopleinParty = (EditText) findViewById(R.id.editTextNumberPeopleinParty);
        editTextNumberDecimalTotalPerPerson = (EditText) findViewById(R.id.editTextNumberDecimalTotalPerPerson);
        seekBarTipBarPercent = (SeekBar) findViewById(R.id.seekBarTipBar);

        // Initialize calculation variables
        BillAmount = 0.0;
        TipPercent = 20;
        TipAmount = 0.0;
        FinalAmount = 0.0;
        PeopleinParty = 1;
        FinalAmountPerson = 0.0;

        // Set default tip percentage
        seekBarTipBarPercent.setProgress(TipPercent);
        editTextNumberTipPrecent.setText(TipPercent.toString()+"%");

        // Set default People percentage
        editTextnumberPeopleinParty.setText(PeopleinParty.toString());

        // Initialize UI with default values
        editTextNumberDecimalTipAmount.setText("$" + String.format("%.2f", TipAmount));
        editTextNumberDecimalTotalDue.setText("$" + String.format("%.2f", FinalAmount));
        editTextNumberDecimalTotalPerPerson.setText("$" + String.format("%.2f", FinalAmountPerson));

        // Add a TextWatcher to the bill total EditText to automatically calculate the tip
        editTextNumberDecimalBillTotal.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Automatically calculate tip when user inputs the bill amount
                if (!editTextNumberDecimalBillTotal.getText().toString().isEmpty()) {
                    CalculateTip(null);  // Trigger tip calculation
                } else {
                    editTextNumberDecimalTipAmount.setText("");
                    editTextNumberDecimalTotalDue.setText("");
                }
            }public void afterTextChanged(Editable s) {}
        });

        // Add a listener to the SeekBar for tip percentage adjustment
        seekBarTipBarPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TipPercent = progress; // Update TipPercent with the seek bar's progress
                editTextNumberTipPrecent.setText(TipPercent.toString() + "%"); // Display tip percent in EditText
                CalculateTip(null); // Recalculate tip
            }public void onStartTrackingTouch(SeekBar seekBar){}public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Add a TextWatcher to automatically calculate the tip when bill amount is entered
        editTextNumberTipPrecent.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tipPercentText = s.toString().replace("%", "").trim();
                if (!tipPercentText.isEmpty()) {
                    try {
                        TipPercent = Integer.parseInt(tipPercentText);
                    }
                    catch (NumberFormatException e) {
                        TipPercent = 20; // Default value if parsing fails
                    }
                }
                else {
                    TipPercent = 0; // Default value if input is empty
                }
                // Sync the SeekBar progress with the new TipPercent
                seekBarTipBarPercent.setProgress(TipPercent);

                // Trigger tip calculation when user inputs the bill amount
                if (!editTextNumberDecimalBillTotal.getText().toString().isEmpty()) {
                    CalculateTip(null);  // Trigger tip calculation
                }
            }
            public void afterTextChanged(Editable s) {}
        });

        // Add a TextWatcher to the number of people EditText
        editTextnumberPeopleinParty.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}public void onTextChanged(CharSequence s, int start, int before, int count) {
                String PeopleinPartyText = s.toString();
                if (!PeopleinPartyText.isEmpty()) {
                    try {
                        PeopleinParty = Integer.parseInt(PeopleinPartyText);
                        if (PeopleinParty < 1) { // If input is less than 1, set it to 1
                            PeopleinParty = 1;
                        }
                    }
                    catch (NumberFormatException e) {
                        PeopleinParty = 1; // Default value if parsing fails
                    }
                }
                else {
                    PeopleinParty = 1; // Default value if input is empty
                }

                // Trigger tip calculation when user inputs the bill amount
                if (!editTextNumberDecimalBillTotal.getText().toString().isEmpty()) {
                    CalculateTip(null);  // Trigger tip calculation
                }
            }public void afterTextChanged(Editable s) {}
        });

    }
    // Method to increase the tip percentage
    public void IncreaseTip(View view) {
        TipPercent = TipPercent + 1; // Increment tip percentage
        if (TipPercent > 100) {
            TipPercent = 100; // Cap tip percentage at 100%
        }
        editTextNumberTipPrecent.setText(TipPercent.toString() + "%"); // Update UI
        CalculateTip(null); // Recalculate tip
    }

    // Method to decrease the tip percentage
    public void DecreaseTip(View view) {
        TipPercent = TipPercent - 1; // Decrement tip percentage
        if (TipPercent < 0) {
            TipPercent = 0; // Ensure tip percentage doesn't go below 0%
        }
        editTextNumberTipPrecent.setText(TipPercent.toString() + "%"); // Update UI
        CalculateTip(null); // Recalculate tip
    }

    // Method to increase the number of people in the party
    public void IncreasePeople(View view) {
        PeopleinParty = PeopleinParty + 1; // Increment number of people
        if (PeopleinParty > 100) {
            PeopleinParty = 100; // Cap the number of people at 100
        }
        editTextnumberPeopleinParty.setText(PeopleinParty.toString()); // Update UI
        CalculateTip(null); // Recalculate amounts
    }

    // Method to decrease the number of people in the party
    public void DecreasePeople(View view) {
        PeopleinParty = PeopleinParty - 1; // Decrement number of people
        if (PeopleinParty < 1) {
            PeopleinParty = 1; // Ensure at least 1 person
        }
        editTextnumberPeopleinParty.setText(PeopleinParty.toString()); // Update UI
        CalculateTip(null); // Recalculate amounts
    }

    // Predefined methods to set tip percentages
    public void Tip10(View view) {
        TipPercent = 10;
        editTextNumberTipPrecent.setText(TipPercent.toString() + "%");
        CalculateTip(null); // Recalculate tip
    }

    public void Tip15(View view) {
        TipPercent = 15;
        editTextNumberTipPrecent.setText(TipPercent.toString() + "%");
        CalculateTip(null); // Recalculate tip
    }

    public void Tip20(View view) {
        TipPercent = 20;
        editTextNumberTipPrecent.setText(TipPercent.toString() + "%");
        CalculateTip(null); // Recalculate tip
    }

    // Method to calculate tip and amounts
    public void CalculateTip(View view) {
        Money = 0.0; // Reset money
        BillAmount = 0.0; // Reset bill amount
        TipAmount = 0.0; // Reset tip amount
        // Get the bill amount
        if (editTextNumberDecimalBillTotal.getText().toString().matches("")) {
            BillAmount = 0.0; // Default if empty
        }
        else {
            BillAmount = Double.parseDouble(editTextNumberDecimalBillTotal.getText().toString());
        }

        // Calculate tip and total
        TipAmount = BillAmount * (TipPercent / 100.0); // Calculate tip amount
        editTextNumberDecimalTipAmount.setText("$" + String.format("%.2f", TipAmount)); // Display tip

        FinalAmount = BillAmount + TipAmount; // Calculate total amount due
        editTextNumberDecimalTotalDue.setText("$" + String.format("%.2f", FinalAmount)); // Display total due

        FinalAmountPerson = FinalAmount / PeopleinParty; // Calculate amount per person
        editTextNumberDecimalTotalPerPerson.setText("$" + String.format("%.2f", FinalAmountPerson)); // Display amount per person
    }
}


