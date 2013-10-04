package com.example.tipcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CalculatorActivity extends Activity {

	double tip;
	double price;

	TextView tvResult;
	EditText etPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		tvResult = (TextView) findViewById(R.id.tvResult);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etPrice.setOnKeyListener(new PriceListener());
	}

	public void pickTip(View tbSelected) {

		// set the tip
		CharSequence tipChars = ((ToggleButton)tbSelected).getText();
		if (tipChars != null) {
			String decimal = String.format("0.%s", tipChars.subSequence(0,2));
			tip = Double.valueOf(decimal);
		}

		// only one toggle can be on
		ViewGroup tbParent = (ViewGroup) tbSelected.getParent();
		if (tbParent != null) {
			int index = tbParent.getChildCount();
			while(index-- > -1) {
				ToggleButton tb = (ToggleButton) tbParent.getChildAt(index);
				if (tb != null) tb.setChecked(tbSelected == tb);
			}
		}

		render();

	}

	class PriceListener implements View.OnKeyListener {
		public boolean onKey(View view, int i, KeyEvent keyEvent) {
			Editable priceText = etPrice.getText();
			if (priceText == null) price = 0;
			else if ("".equals(priceText.toString())) price = 0;
			else price = Double.valueOf(priceText.toString());
			return render();
		}
	}

	public boolean render() {

		String result = "";
		String resultTip;
		String resultTotal;

		if (tip > 0 && price > 0) {
			double tipValue = price * tip;
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			resultTip   = formatter.format(tipValue);
			resultTotal = formatter.format(tipValue + price);
			result = String.format("Tip: %s\nTotal: %s", resultTip, resultTotal);
		}

		tvResult.setText(result);

		return false;

	}

}
