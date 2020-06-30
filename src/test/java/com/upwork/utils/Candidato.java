package com.upwork.utils;
//package Utils;

public class Candidato {
    String nombreCandidato;
    String titulo;
    double rate;
    int success;
    String countryText;

    public Candidato(String nombreCandidato, String titulo, double rate, int success, String countryText) {
        this.nombreCandidato = nombreCandidato;
        this.titulo = titulo;
        this.rate = rate;
        this.success = success;
        this.countryText = countryText;
    }

    public String getNombreCandidato() {
        return nombreCandidato;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getRate() {
        return rate;
    }

    public int getSuccess() {
        return success;
    }

    public String getCountryText() {
        return countryText;
    }

}
