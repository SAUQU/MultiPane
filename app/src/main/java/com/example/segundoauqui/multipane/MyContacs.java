package com.example.segundoauqui.multipane;

/**
 * Created by segundoauqui on 8/10/17.
 */

public class MyContacs {
    String etName;
    String etOrigin;
    String etInstrument;
    byte[] b;
    int id;

    public String getEtName() {
        return etName;
    }

    public void setEtName(String etName) {
        this.etName = etName;
    }

    public String getEtOrigin() {
        return etOrigin;
    }

    public void setEtOrigin(String etOrigin) {
        this.etOrigin = etOrigin;
    }

    public String getEtInstrument() {
        return etInstrument;
    }

    public void setEtInstrument(String etInstrument) {
        this.etInstrument = etInstrument;
    }

    public byte[] getB() {
        return b;
    }

    public void setB(byte[] b) {
        this.b = b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyContacs(String etName, String etOrigin, String etInstrument, byte[] b, int id) {
        this.etName = etName;
        this.etOrigin = etOrigin;
        this.etInstrument = etInstrument;
        this.b = b;
        this.id = id;
    }
}