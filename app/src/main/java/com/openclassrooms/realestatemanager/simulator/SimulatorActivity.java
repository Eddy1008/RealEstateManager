package com.openclassrooms.realestatemanager.simulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySimulatorBinding;

public class SimulatorActivity extends AppCompatActivity {

    private ActivitySimulatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySimulatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setPreviousPageButton();
        setEstimateButton();
    }

    void setPreviousPageButton() {
        binding.simulatorPreviousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setEstimateButton() {
        binding.simulatorEstimateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.simulatorEdittextPrice.getText().toString().equals("")) {
                    binding.simulatorEdittextPrice.setError(getString(R.string.simulator_missing_info));
                } else if (binding.simulatorEdittextNumberOfMonthlyPayments.getText().toString().equals("")) {
                    binding.simulatorEdittextNumberOfMonthlyPayments.setError(getString(R.string.simulator_missing_info));
                } else if (binding.simulatorEdittextContributionAmount.getText().toString().equals("")) {
                    binding.simulatorEdittextContributionAmount.setError(getString(R.string.simulator_missing_info));
                } else {
                    int propertyPrice = Integer.parseInt(binding.simulatorEdittextPrice.getText().toString());
                    int nbPayment = Integer.parseInt(binding.simulatorEdittextNumberOfMonthlyPayments.getText().toString());
                    int contribution = Integer.parseInt(binding.simulatorEdittextContributionAmount.getText().toString());

                    int difference = propertyPrice - contribution;
                    int numberYearsInterest = nbPayment / 12;
                    float interestRate = 2;
                    float creditCost = difference * interestRate * numberYearsInterest / 100;
                    float amountToBorrow = difference + creditCost;
                    float monthlyPayment = amountToBorrow / nbPayment;

                    String diff = difference + " $";
                    String nbYearInterest = numberYearsInterest + " year of payment";
                    String interest = "interest rate = " + interestRate + " %";
                    String cost = "Credit cost = " + creditCost + " $";
                    String toBorrow = amountToBorrow + " $ to borrow";
                    String monthlyPay = monthlyPayment + " $ per month";

                    binding.simulatorTextviewDifference.setText(diff);
                    binding.simulatorTextviewYear.setText(nbYearInterest);
                    binding.simulatorTextviewInterest.setText(interest);
                    binding.simulatorTextviewCreditCost.setText(cost);
                    binding.simulatorTextviewAmountToBorrow.setText(toBorrow);
                    binding.simulatorTextviewResult.setText(monthlyPay);

                    binding.simulatorResultCardView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}