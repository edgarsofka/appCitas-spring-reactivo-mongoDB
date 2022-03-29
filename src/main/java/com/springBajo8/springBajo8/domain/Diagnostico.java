package com.springBajo8.springBajo8.domain;

import java.util.List;

public class Diagnostico {

    private List<String> padecimientos;

    private List<String> tratamientos;

    public List<String> getPadecimientos() {
        return padecimientos;
    }

    public void setPadecimientos(List<String> padecimientos) {
        this.padecimientos = padecimientos;
    }

    public List<String> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<String> tratamientos) {
        this.tratamientos = tratamientos;
    }
}
