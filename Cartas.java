//Gustavo Mezomo e Vinicius Bergmann
public class Cartas {
    //atributos
    private int valor;
    private String naipe;
    
    // construtor
    public Cartas(int valor, String naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }
    
    //get
    public int getValor() {
        return valor;
    }

    public String getNaipe() {
        return naipe;
    }
    
    //ToString
    public String toString() {
        return valor + " de " + naipe;
    }
}