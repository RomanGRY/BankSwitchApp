package com.bankapp.privat_nbu_Switch;

public class NBUBankItem {
    private String exchangedate;
    private String txt;
    private Double rate;
    private String cc;

    public NBUBankItem() {}

    public NBUBankItem(String txt, Double rate, String cc) {
        this.txt = txt;
        this.rate = rate;
        this.cc = cc;
    }

    public String getExchangedate() {
        return exchangedate;
    }

    public void setExchangedate(String exchangedate) {
        this.exchangedate = exchangedate;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}
