package com.rcdriver.dr.utils;


import com.rcdriver.dr.models.payment.PaymentMethod;

public interface OnItemPaymentSelected {
    void onItemSelected(PaymentMethod method);
}
