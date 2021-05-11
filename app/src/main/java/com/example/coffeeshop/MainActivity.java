package com.example.coffeeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 1;
    /**
     * This method is called when the plus button is clicked
     */
    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;

        displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked
     */
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();

            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Finds the user's name
        EditText nameField =(EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage=createOrderSummary(price,name,hasWhippedCream,hasChocolate);

        //displayMessage(priceMessage);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * this method calculate the price of the order
     * @param addChocolate is whether or not thr user wants chocolate topping
     * @param addWhippedCream is whether or not thr user wants whipped cream topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate) {
        //Price of 1 cup of coffee
        int basePrice =5;

        //Add $1 if the user wants whipped cream
        if(addWhippedCream){
            basePrice= basePrice +1;
        }

        //Add $2 if the user wants Chocolate
        if(addChocolate){
            basePrice= basePrice+2;
        }

        //calculate the total cost
        return quantity*basePrice;
    }
    /**
     *create summary of the order
     * @param  price of the order
     * @return  text summary
     */
    private String createOrderSummary(int price, String name,boolean addWhippedCream , boolean addChocolate){
        String priceMessage="Name:"+name;
        priceMessage+= "\n"+getString(R.string.add_Whipped_Cream)+addWhippedCream;
        priceMessage+= "\n"+getString(R.string.add_chocolate)+addChocolate;
        priceMessage+="\n"+getString(R.string.quantity)+ quantity;
        priceMessage+="\n"+getString(R.string.total) + price+"";
        priceMessage+="\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantityTextView);
        quantityTextView.setText(""+numberOfCoffees);
    }
    /**
     * This method display the given text on the screen
     */
    private void displayMessage(String message) {
        TextView order_summary_text_view = findViewById(R.id.order_summary_text_view);
        order_summary_text_view.setText(message);
    }
}