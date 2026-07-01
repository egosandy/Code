package com.rcdriver.mt.utils;

import com.rcdriver.mt.models.payment.PaymentMethod;

public interface OnItemPaymentSelected {
    void onItemSelected(PaymentMethod method);
}
