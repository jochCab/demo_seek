package com.demo.seek.util.paginacion;


import org.springframework.data.domain.Page;

import java.util.List;

public class PageRender<T> {

    private List<T> data;
    private Integer paginaActual;
    private Long totalRegistros;

    public PageRender() {
    }

    public PageRender(Page<T> page) {
        this.data = page.getContent();
        this.paginaActual = page.getNumber();
        this.totalRegistros = page.getTotalElements();
    }

    public PageRender(List<T> data, Integer paginaActual, Long totalRegistros) {
        this.data = data;
        this.paginaActual = paginaActual;
        this.totalRegistros = totalRegistros;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public long getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
}
