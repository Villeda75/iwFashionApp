package sv.edu.udb.iwfashionapp.models;

public class Cliente {


    public Cliente(int id_cliente, String code_cliente,String nombres, String apellidos, String correo) {
        this.id_cliente = id_cliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.code_cliente=code_cliente;
    }

    private int id_cliente;

    public String getCode_cliente() {
        return code_cliente;
    }

    public void setCode_cliente(String code_cliente) {
        this.code_cliente = code_cliente;
    }

    private String code_cliente;
    private String nombres;
    private String apellidos;
    private String correo;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }



}
