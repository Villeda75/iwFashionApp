package sv.edu.udb.iwfashionapp.models;

public class CustomDesign_Request {

    private String name;
    private String email;

    public CustomDesign_Request(String name, String email, String tel, String message, String nombreArchivo, String base64textString) {
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.message = message;
        this.nombreArchivo = nombreArchivo;
        this.base64textString = base64textString;
    }

    private String tel;
    private String message;
    private String nombreArchivo;
    private String base64textString;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getBase64textString() {
        return base64textString;
    }

    public void setBase64textString(String base64textString) {
        this.base64textString = base64textString;
    }



}
