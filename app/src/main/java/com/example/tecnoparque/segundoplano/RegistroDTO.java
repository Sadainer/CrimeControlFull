package com.example.tecnoparque.segundoplano;

/**
 * Created by TECNOPARQUE on 08/04/2015.
 */
public class RegistroDTO {
    private String UsuarioCedula ;
    private String Local;
    private String Direccion ;
    private String IDRed ;



    public String getUsuarioCedula() {
        return UsuarioCedula;
    }
    public void setUsuarioCedula(String usuarioCedula) {
        UsuarioCedula = usuarioCedula;
    }
    public String getLocal() {
        return Local;
    }
    public void setLocal(String local) {
        Local = local;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
    public String getIDRed() {
        return IDRed;
    }
    public void setIDRed(String iDRed) {
        IDRed = iDRed;
    }
}
