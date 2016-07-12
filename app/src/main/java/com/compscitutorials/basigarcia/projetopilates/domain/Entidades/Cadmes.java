package com.compscitutorials.basigarcia.projetopilates.domain.Entidades;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by eabreu on 06/10/2015.
 */
public class Cadmes  implements Serializable {
    public static String TABELA = "CADMES";

    public static String ID = "_id";
    public static String CAD_NAME = "CAD_NAME";
    public static String CAD_MES_ANO = "CAD_MES_ANO";


    private static final long serialVersionUID = 1L;
    private Integer cadId;
    private String cadName;
    private Collection<Cadmesdia> cadmesdiaCollection;
    private String cadMesANO;

    public Cadmes() {
    }

    public Cadmes(Integer cadId) {
        this.cadId = cadId;
    }

    public Cadmes(Integer cadId, String cadName) {
        this.cadId = cadId;
        this.cadName = cadName;
    }

    public Integer getCadId() {
        return cadId;
    }

    public void setCadId(Integer cadId) {
        this.cadId = cadId;
    }

    public String getCadName() {
        return cadName;
    }

    public void setCadName(String cadName) {
        this.cadName = cadName;
    }

    public Collection<Cadmesdia> getCadmesdiaCollection() {
        return cadmesdiaCollection;
    }

    public void setCadmesdiaCollection(Collection<Cadmesdia> cadmesdiaCollection) {
        this.cadmesdiaCollection = cadmesdiaCollection;
    }

    public String getCadMesANO() {
        return cadMesANO;
    }

    public void setCadMesANO(String cadMesANO) {
        this.cadMesANO = cadMesANO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cadId != null ? cadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cadmes)) {
            return false;
        }
        Cadmes other = (Cadmes) object;
        if ((this.cadId == null && other.cadId != null) || (this.cadId != null && !this.cadId.equals(other.cadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cadName;
    }

}
