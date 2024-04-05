package com.oscar.collectionsandgenerics;


//Comparable Interface implements compareTo method
public class Estudiante implements Comparable<Estudiante>{

    private Integer codigo;
    private String nombre;
    private Integer edad;


    //return
//    0 - Current object equals to the argument
//    Positive - Current object is larger than argument
//    Negative - Current smaller is larger than argument

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estudiante that = (Estudiante) o;

        if (!codigo.equals(that.codigo)) return false;
        if (!nombre.equals(that.nombre)) return false;
        return edad.equals(that.edad);
    }

    @Override
    public int hashCode() {
        int result = codigo.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + edad.hashCode();
        return result;
    }

    @Override
    public int compareTo(Estudiante o) {
        return this.getCodigo()-o.getCodigo();

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }





    @Override
    public String toString() {
        return "Estudiante{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                '}';
    }

    public Estudiante(Integer codigo, String nombre, Integer edad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.edad = edad;
    }
}

