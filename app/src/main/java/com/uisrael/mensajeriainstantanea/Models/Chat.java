package com.uisrael.mensajeriainstantanea.Models;

public class Chat {

    private String envia;
    private String recibe;
    private String mensaje;
    private boolean isseen;

    public Chat(String envia, String recibe, String mensaje, boolean isseen) {
        this.envia = envia;
        this.recibe = recibe;
        this.mensaje = mensaje;
        this.isseen = isseen;
    }

    public Chat(){

    }

    public String getEnvia() {
        return envia;
    }

    public void setEnvia(String envia) {
        this.envia = envia;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
