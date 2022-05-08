package com.example.project39.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.razorpay.Checkout;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project39.Adaptor.CartListAdapter;
import com.example.project39.Helper.ManagementCart;
import com.example.project39.Interface.ChangeNumberItemsListener;
import com.example.project39.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CartListActivity extends AppCompatActivity implements PaymentResultListener {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    EditText etName;
    EditText etEmail;
    EditText etAddress;
    EditText etNumber;
    TextView btnCheckout;
    Spinner etDate;
    Spinner etTime;
    double percentTax = 0.02;
    double delivery = 10;

    DatabaseReference deliveryDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        Checkout.preload(getApplicationContext());

        etName = findViewById(R.id.Name);
        etEmail = findViewById(R.id.Email);
        etAddress = findViewById(R.id.Address);
        etNumber = findViewById(R.id.Phone);
        btnCheckout = findViewById(R.id.Checkoutbtn);
        etDate = findViewById(R.id.spinnerDate);
        etTime = findViewById(R.id.spinnerTime);

        deliveryDbRef = FirebaseDatabase.getInstance().getReference().child("DeliveryDB");
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDeliveryData();

            }
        });


        managementCart = new ManagementCart(this);

        initView();
        initList();
        CalculateCart();
        bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.cartView);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void CalculateCart() {

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100;

        totalFeeTxt.setText("LKR" + itemTotal);
        taxTxt.setText("LKR" + tax);
        deliveryTxt.setText("LKR" + delivery);
        totalTxt.setText("LKR" + total);
    }

    private void insertDeliveryData() {
        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100;
        String samount = String.valueOf(total);
        int amount = Math.round(Float.parseFloat(samount) * 100);


        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String address = etAddress.getText().toString();
        String number = etNumber.getText().toString();
        String date = etDate.getSelectedItem().toString();
        String time = etTime.getSelectedItem().toString();

        Delivery delivery = new Delivery(name, address, number, time, date, email);

        if (name.isEmpty()) {
            etName.setError("Full Name is Required..!");
            etName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError("Email is Required..!");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter valid Email Address..!");
            etEmail.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            etAddress.setError("Address is Required..!");
            etAddress.requestFocus();
            return;
        }
        if (number.isEmpty()) {
            etNumber.setError("Number is Required..!");
            etNumber.requestFocus();
            return;
        }
        if (number.length() < 10) {
            etNumber.setError("Number must contain 10..!");
            etNumber.requestFocus();
            return;
        } else {
            deliveryDbRef.push().setValue(delivery);
            Toast.makeText(this, "Data Inserted..!", Toast.LENGTH_SHORT).show();
        }

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_A6zIiaelohpvKE");

        JSONObject object = new JSONObject();
        try {
            object.put("name", "PizzaHut");
            object.put("description", "Pizza Order Payment");
            object.put("theme.color", "");
            object.put("amount", amount);
            object.put("prefill.contact", "1234567890");
            object.put("prefill.email", "demo@gmail.com");
            checkout.open(this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Sucsess: " + s, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));


    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment UnSucsess: " + i, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }

}