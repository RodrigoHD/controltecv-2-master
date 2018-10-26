package com.ittepic.controltec.utilidades;

import java.util.ArrayList;

public class ActualizarInicio {

    ArrayList<Categorias> mCategorias = new ArrayList<Categorias>();


    public ActualizarInicio(ArrayList<Categorias> mCategorias) {
        this.mCategorias = mCategorias;
    }


    public ActualizarInicio(){

        Categorias a = new Categorias(constantes.CATEGORIA_NEUMATICA_BASICA,
                                        constantes.ICONO_NEUMATICA_BASICA,
                                        new ActualizarPracticas(constantes.CATEGORIA_NEUMATICA_BASICA).getSize());

        Categorias b = new Categorias(constantes.CATEGORIA_NEUMATICA_AVANZADA,
                                        constantes.ICONO_NEUMATICA_AVANZADA,
                                        new ActualizarPracticas(constantes.CATEGORIA_NEUMATICA_AVANZADA).getSize());

        this.mCategorias.add(a);
        this.mCategorias.add(b);

    }

    public ArrayList<Categorias> getCategorias() {
        return this.mCategorias;
    }

    public int getSize(){return this.mCategorias.size();}
}

