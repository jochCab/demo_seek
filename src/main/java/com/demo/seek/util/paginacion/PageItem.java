package com.demo.seek.util.paginacion;

import org.springframework.data.domain.Sort;

public class PageItem {
    public static String DIRECTION_ASC = "ASC";

    private int numero;
    private boolean actual;

    public PageItem(int numero, boolean actual) {
        this.numero = numero;
        this.actual = actual;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public static Sort getOrdenamiento(String ordenadoPor) {
        String atributo = ordenadoPor != null ? ordenadoPor.split(";")[0] : "";
        Sort sort =  esAscendente(ordenadoPor) ? Sort.by(atributo).ascending() : Sort.by(atributo).descending();
        return sort;
    }

    public static boolean esAscendente(String ordenadoPor) {
        String[] _ordenadoPor = ordenadoPor.split(";");
        if (_ordenadoPor.length == 1) {
            return true;
        }
        return DIRECTION_ASC.toLowerCase().equals(ordenadoPor.split(";")[1].toLowerCase());
    }
}
