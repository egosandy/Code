package com.rcdriver.cs.utils;


import com.rcdriver.cs.models.payment.PaymentMethod;

public interface OnItemPaymentSelected {
    void onItemSelected(PaymentMethod method);
}
