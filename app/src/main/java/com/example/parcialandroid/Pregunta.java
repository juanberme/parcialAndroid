package com.example.parcialandroid;

public class Pregunta {

    private String pregunta;
    private String id;
    private String puntos;
    private String estado;



    public Pregunta() { }

    public Pregunta(String id, String pregunta, String puntos, String estado) {
        this.pregunta = pregunta;
        this.id = id;
        this.puntos = puntos;
        this.estado = estado;


    }

    public String getPregunta() { return pregunta; }

    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPuntos() { return puntos; }

    public void setPuntos(String puntos) { this.puntos = puntos; }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }



}
