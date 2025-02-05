
package Modelos;

/**
 *
 * @author yoriel
 */
public class VeterinariosDTO {

  
      
    private int idVeterinario;
    private String nifVeterinario;
    private String nombreVeterinario;
    private String direccion;
    private String telefono;
    private String email;

 public VeterinariosDTO() {
     
 }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public String getNifVeterinario() {
        return nifVeterinario;
    }

    public void setNifVeterinario(String nifVeterinario) {
        this.nifVeterinario = nifVeterinario;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
       return "Veterinario [ID=" + idVeterinario + ", NIF=" + nifVeterinario + ", Nombre=" + nombreVeterinario + 
           ", Dirección=" + direccion + ", Teléfono=" + telefono + ", Email=" + email + "]";
}
    
  public VeterinariosDTO(int idVeterinario, String nifVeterinario, String nombreVeterinario, String direccion, String telefono, String email) {
        this.idVeterinario = idVeterinario;
        this.nifVeterinario = nifVeterinario;
        this.nombreVeterinario = nombreVeterinario;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        
        
    }
}


