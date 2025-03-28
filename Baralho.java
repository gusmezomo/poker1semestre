//Gustavo Mezomo e Vinicius Bergmann
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Baralho {
    // atributos
    private List<Cartas> cartas;
    private int topo;
    
    // constutor 
    public Baralho() {
        cartas = new ArrayList<>();
        topo = 0;

        //Cartas
        String[] naipes = { "Paus", "Ouros", "Copas", "Espadas" };
        int[] valor = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

        for (String naipe : naipes) {
            for (int nome : valor) {
                Cartas carta = new Cartas(nome, naipe);
                cartas.add(carta);
            }
        }
    }
    
    // método de embaralhamento
    public void embaralha() {
        Random random = new Random();
        Collections.shuffle(cartas, random);
    }
    
    // método para dar as cartas
    public Cartas daCarta() {
        if (topo < cartas.size()) {
            Cartas carta = cartas.get(topo);
            topo++;
            return carta;
        } else {
            return null;
        }
    }
    
    //conferir cartas
    public boolean temCarta() {
        return topo < cartas.size();
    }
    
    //retornar uma lista de cartas    
    public List<Cartas> getCartas() {
        embaralha();
        return cartas;
    }
}