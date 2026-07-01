package com.rcdriver.mt.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.rcdriver.mt.R;
import com.rcdriver.mt.utils.IPrintToPrinter;
import com.rcdriver.mt.utils.WoosimPrnMng;
import com.rcdriver.mt.utils.printerFactory;
import com.rcdriver.mt.utils.printerWordMng;

import com.woosim.printer.WoosimCmd;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
public class PrintNota implements IPrintToPrinter {
    String name, price, qty, weight;
    double cost_total, subTotal, totalPrice;
    Bitmap bm;
    //DecimalFormat f;
    private Context context;
    List<HashMap<String, String>> orderDetailsList;
    String currency, shopName, shopAddress, shopEmail, shopContact, invoiceId, orderDate, orderTime, customerName, footer, tax, discount,bayar,kembalian;
    public PrintNota(Context context, String shopName, String shopAddress, String shopEmail, String shopContact, String invoiceId, String orderDate, String orderTime, String customerName, String footer, double subTotal, double totalPrice, String tax, String discount, String currency,String bayar,String kembalian) {
        this.context = context;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopEmail = shopEmail;
        this.shopContact = shopContact;
        this.invoiceId = invoiceId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.customerName = customerName;
        this.footer = footer;
        this.subTotal = subTotal;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.discount = discount;
        this.currency = currency;
        this.bayar = bayar;
        this.kembalian = kembalian;
        // f = new DecimalFormat("#0.00");


        //DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
     //   databaseAccess.open();


        //get data from local database

        //orderDetailsList = databaseAccess.getOrderDetailsList(invoiceId);
    }
    public static String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number).replaceAll(",00", "");
    }
    @Override
    public void printContent(WoosimPrnMng prnMng) {

        double getDiscount=Double.parseDouble(discount);
        double getTax=Double.parseDouble(tax);
        printerWordMng wordMng = printerFactory.createPaperMng(context);
        prnMng.printStr(shopName, 2, WoosimCmd.ALIGN_CENTER);
        prnMng.printStr(shopAddress, 1, WoosimCmd.ALIGN_CENTER);
        // prnMng.printStr("Email: " + shopEmail, 1, WoosimCmd.ALIGN_CENTER);
        prnMng.printStr("Kontak: " + shopContact, 1, WoosimCmd.ALIGN_CENTER);
        prnMng.printStr("Nota: " + invoiceId, 1, WoosimCmd.ALIGN_CENTER);
        prnMng.printStr("Waktu: " + orderTime + " " + orderDate, 1, WoosimCmd.ALIGN_CENTER);
        prnMng.printStr(customerName, 1, WoosimCmd.ALIGN_CENTER);

        //   prnMng.printStr("Email: " + shopEmail, 1, WoosimCmd.ALIGN_CENTER);

        prnMng.printStr("--------------------------------");
        prnMng.printStr("Barang   Harga   Jml   Total", 1, WoosimCmd.ALIGN_LEFT);
        prnMng.printStr("--------------------------------");

        for (int i = 0; i < orderDetailsList.size(); i++) {
            name = orderDetailsList.get(i).get("product_name");
            price = orderDetailsList.get(i).get("product_price");
            qty = orderDetailsList.get(i).get("product_qty");
            weight = orderDetailsList.get(i).get("product_weight");
            cost_total = Integer.parseInt(qty) * Double.parseDouble(price);
            //   prnMng.leftRightAlign(name.trim(), " " + formatRupiah(Double.parseDouble(price)) + " x" + qty + " " + formatRupiah(cost_total));
            prnMng.printStr(name.trim(), 1, WoosimCmd.ALIGN_LEFT);
            prnMng.printStr(formatRupiah(Double.parseDouble(price))+"   "+qty+" "+formatRupiah(cost_total), 1, WoosimCmd.ALIGN_LEFT);
        }

        prnMng.printStr("--------------------------------");
        prnMng.printStr("Total: " + formatRupiah(subTotal), 1, WoosimCmd.ALIGN_RIGHT);
        prnMng.printStr("Ppn (+): " + formatRupiah(getTax), 1, WoosimCmd.ALIGN_RIGHT);
        prnMng.printStr("Diskon (-): " +  formatRupiah(getDiscount), 1, WoosimCmd.ALIGN_RIGHT);
        prnMng.printStr("--------------------------------");
        prnMng.printStr("Total Harga: " + formatRupiah(totalPrice), 1, WoosimCmd.ALIGN_RIGHT);
        prnMng.printStr("Dibayar: " + formatRupiah(Double.valueOf(bayar)), 1, WoosimCmd.ALIGN_RIGHT);
        prnMng.printStr("Kembali: " + formatRupiah(Double.valueOf(kembalian)), 1, WoosimCmd.ALIGN_RIGHT);
        prnMng.printStr(footer, 1, WoosimCmd.ALIGN_CENTER);

        prnMng.printNewLine();

        //print barcode
        prnMng.printPhoto(bm);

        prnMng.printNewLine();
        prnMng.printNewLine();
        //Any finalization, you can call it here or any where in your running activity.
        printEnded(prnMng);
    }

    @Override
    public void printEnded(WoosimPrnMng prnMng) {
        //Do any finalization you like after print ended.
        if (prnMng.printSucc()) {
            Toast.makeText(context, R.string.print_succ, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, R.string.print_error, Toast.LENGTH_LONG).show();
        }
    }
}
