package co.edu.ufps.Examen_final.Dtos;

public class CompraResponse {

    private String status;  
    private String message;  
    private CompraResponseData data;  

    // Getters y Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CompraResponseData getData() {
        return data;
    }

    public void setData(CompraResponseData data) {
        this.data = data;
    }

   
    public static class CompraResponseData {
        private String numero;  
        private String total;   
        private String fecha;   

        public CompraResponseData(String numero, String total, String fecha) {
            this.numero = numero;
            this.total = total;
            this.fecha = fecha;
        }

        // Getters y Setters
        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }
}

