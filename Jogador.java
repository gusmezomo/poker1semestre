//Gustavo Mezomo e Vinicius Bergmann
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    // atributos
    private String nome;
    private List<Cartas> mao;
    private int quantFichas;
    private int aposta;
    private int forcaMao;
    
    //construtor
    public Jogador(String nome, int quantquantFichas){
        this.nome = nome;
        this.mao = new ArrayList<>();
        this.quantFichas = quantquantFichas;
        aposta = 0;
        forcaMao = 0;
    }

    //get
    
    public int getAposta(){
        return aposta;
    }
    
    public String getNome() {
        return nome;
    }

    public int getQuantFichas() {
        return quantFichas;
    }

    public int getForcaMao() {
        return forcaMao;
    }
    
    // método para adicionar cartas para o jogador
    public void receberCarta(Cartas carta) {
        mao.add(carta);
    }
    
    // Ordenar as cartas em ordem crescente
    public void definirTipoJogo() {
        int n = mao.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Cartas cartaAtual = mao.get(j);
                Cartas proximaCarta = mao.get(j + 1);
                if (cartaAtual.getValor() > proximaCarta.getValor()) {
                    //trocar as cartas de posição
                    mao.set(j, proximaCarta);
                    mao.set(j + 1, cartaAtual);
                }
            }
        }

        //Verificação das combinações do jogo
        
        //Royal Flush
        boolean isRoyalFlush = mao.get(0).getValor() == 10 && isStraightFlush();
        if (isRoyalFlush) {
            forcaMao = 22;
            return;
        }

        //Straight Flush
        boolean isStraightFlush = isStraightFlush();
        if (isStraightFlush) {
            forcaMao = 21;
            return;
        }

        //Quadra
        boolean isQuadra = isQuadra();
        if (isQuadra) {
            forcaMao = 20;
            return;
        }

        //Full House
        boolean isFullHouse = isFullHouse();
        if (isFullHouse) {
            forcaMao = 19;
            return;
        }

        //Flush
        boolean isFlush = isFlush();
        if (isFlush) {
            forcaMao = 18;
            return;
        }

        //Straight
        boolean isStraight = isStraight();
        if (isStraight) {
            forcaMao = 17;
            return;
        }

        //Trinca
        boolean isTrinca = isTrinca();
        if (isTrinca) {
            forcaMao = 16;
            return;
        }

        //Dois Pares
        boolean isDoisPares = isDoisPares();
        if (isDoisPares) {
            forcaMao = 15;
            return;
        }

        //Um Par
        boolean isPar = isPar();
        if (isPar) {
            forcaMao = 14;
            return;
        }

        // Nenhuma combinação específica
        forcaMao = mao.get(4).getValor();
    }

    private boolean isStraightFlush(){
        return isStraight() && isFlush();
    }

    private boolean isQuadra() {
        int count = 1;
        for (int i = 0; i < mao.size() - 1; i++) {
            if (mao.get(i).getValor() == mao.get(i + 1).getValor()) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 1;
            }
        }
        return false;
    }

    private boolean isFullHouse() {
        boolean isTrinca = false;
        boolean isPar = false;

        for (int i = 0; i < mao.size() - 2; i++) {
            if (mao.get(i).getValor() == mao.get(i + 1).getValor()
                    && mao.get(i).getValor() == mao.get(i + 2).getValor()) {
                isTrinca = true;
            }
        }

        for (int i = 0; i < mao.size() - 1; i++) {
            if (mao.get(i).getValor() == mao.get(i + 1).getValor()) {
                if (!isTrinca) {
                    isPar = true;
                } else {
                    isPar = false;
                }
            }
        }

        return isTrinca && isPar;
    }

    private boolean isFlush() {
        return mao.get(0).getNaipe() == mao.get(1).getNaipe() &&
                mao.get(0).getNaipe() == mao.get(2).getNaipe() &&
                mao.get(0).getNaipe() == mao.get(3).getNaipe() &&
                mao.get(0).getNaipe() == mao.get(4).getNaipe();
    }

    private boolean isStraight() {
        int firstValue = mao.get(0).getValor();
        int lastValue = mao.get(mao.size() - 1).getValor();
        return lastValue - firstValue == 4;
    }

    private boolean isTrinca() {
        for (int i = 0; i < mao.size() - 2; i++) {
            if (mao.get(i).getValor() == mao.get(i + 1).getValor()
                    && mao.get(i).getValor() == mao.get(i + 2).getValor()) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoisPares() {
        int countPairs = 0;
        for (int i = 0; i < mao.size() - 1; i++) {
            if (mao.get(i).getValor() == mao.get(i + 1).getValor()) {
                countPairs++;
                i++;
            }
        }
        return countPairs == 2;
    }

    private boolean isPar() {
        for (int i = 0; i < mao.size() - 1; i++) {
            if (mao.get(i).getValor() == mao.get(i + 1).getValor()) {
                return true;
            }
        }
        return false;
    }

    public Cartas getCartaMaisAlta() {
        return mao.get(4);
    }

    public void mostrarMao() {
        for (Cartas carta : mao) {
            System.out.println(carta);
        }
    }
    
    // método que faz a troca
    public void pedirTroca(List<Cartas> baralho) {
        // Solicitar as cartas para o jogador trocar
        System.out.println(getNome() + "Digite o número correspondente as cartas que deseja trocar:");

        for (int i = 0; i < mao.size(); i++) {
            System.out.println((i + 1) + ". " + mao.get(i));
        }

        System.out.println("Depois de escolhido as cartas a serem trocadas, digite 0 para confirmar");

        List<Integer> cartasParaTrocar = new ArrayList<>();

        int opcao = 0;
        for (int i = 0; i < 5; i++) {
            opcao = Teclado.leInt();

            if (opcao < 0 || opcao > mao.size()) {
                System.out.println("Opção inválida. Digite novamente.");
            } else if (opcao != 0) {
                cartasParaTrocar.add(opcao - 1);

            } else if (opcao == 0) {
                break;
            }
        }

        // Trocar as cartas
        for (int indice : cartasParaTrocar) {
            Cartas cartaTrocada = mao.get(indice);
            Cartas novaCarta = baralho.remove(0); // Remove a primeira carta
            mao.set(indice, novaCarta);
            baralho.add(cartaTrocada); // Adiciona a carta trocada ao baralho novamente
        }
        mostrarMao();
        System.out.println("");
    }
    
    // verifica se o jogador tem fichas para apostar
    public boolean podeApostar(int valorAposta) {
        return quantFichas >= valorAposta;
    }
    
    // método para apostas 
    public void apostar(int valor){ 
        if (valor > quantFichas) {
            System.out.println("Não há fichas suficientes para fazer essa aposta.");
            System.out.println("");
            return;
        }

        quantFichas -= valor;
        aposta += valor;
        System.out.println(nome + " fez uma aposta de " + valor + " fichas.");
        System.out.println("");

    }
    
    //receber fichas
    public void receberFichas(int quantidade) { 
        quantFichas += quantidade;
    }
    //limpar a mão
    public void limparMao() {
        mao.clear();
    }
}