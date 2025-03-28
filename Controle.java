//Gustavo Mezomo e Vinicius Bergmann
import java.util.ArrayList;
import java.util.List;

public class Controle{
    //atributos
    private List<Jogador> jogadores;
    private Baralho baralho;
    private int pot;
    private int apostaAtual;
    private int BB;
    private int SB;
    
    //construtor
    public Controle(){
        jogadores = new ArrayList<>();
        baralho = new Baralho();
        pot = 0;
        apostaAtual = 10; //Valor inicial
        BB = 0;
        SB = 4; 
    }
    
    //adicionar jogador
    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }
    
    // distribui as cartas
    public void distribuirCartas() {
        for (int i = 0; i < 5; i++) {
            for (Jogador jogador : jogadores) {
                Cartas carta = baralho.daCarta();
                jogador.receberCarta(carta);
            }
        }
    }
    
    //executa os metodos do jogo
    public void jogarRodada(){   
        baralho.embaralha();
        distribuirCartas();
        fazerApostas();
        rodadaTroca();
        fazerApostas();
        mostrarCartas();
        definirVencedor();
        distribuirPote();
        limparMaosEPote();
    }

    // rodada de troca
    private void rodadaTroca() {
        for (Jogador jogador : jogadores) {
            jogador.pedirTroca(baralho.getCartas());
        }
    }
    
    // método das apostas
    private void fazerApostas() {
        int currentPlayerIndex = (BB + 1) % jogadores.size();
        boolean allPlayersDone = false;
        boolean firstLoop = true;

        while (!allPlayersDone) {
            Jogador currentPlayer = jogadores.get(currentPlayerIndex);

            if (currentPlayer.podeApostar(apostaAtual)) {
                System.out.println("Jogador:" + currentPlayer.getNome());
                currentPlayer.mostrarMao();

                int acao;
                if (firstLoop && currentPlayerIndex == SB) {
                    acao = Teclado.leInt(
                            "O que você deseja fazer?\n1. Completar\n2. Aumentar a aposta\n3. Desistir");
                } else {
                    acao = Teclado.leInt("O que você deseja fazer?\n1. Completar\n2. Aumentar a aposta\n3. Desistir");
                }
                for (int i = 0; i < 1; i++) {
                    if (acao == 1) {
                        currentPlayer.apostar(apostaAtual);
                        break;
                    } else if (acao == 2) {
                        int novaAposta = Teclado.leInt("Informe o valor da nova aposta:");
                        currentPlayer.apostar(novaAposta);
                        apostaAtual = novaAposta;
                        break;
                    } else if (acao == 3) {
                        jogadores.remove(currentPlayer);
                        if (currentPlayerIndex < BB) {
                            BB--;
                            if (currentPlayerIndex < SB) {
                                SB--;
                            }
                        } else if (currentPlayerIndex == BB && BB == SB) {
                            SB--;
                        }
                        break;
                    } else {
                        System.out.println("Ação inválida. Completando a aposta.");
                        currentPlayer.apostar(apostaAtual);
                        break;
                    }
                }
            } else {
                jogadores.remove(currentPlayer);
                if (currentPlayerIndex < BB) {
                    BB--;
                    if (currentPlayerIndex < SB) {
                        SB--;
                    }
                } else if (currentPlayerIndex == BB && BB == SB) {
                    SB--;
                }
            }

            if (jogadores.size() == 1 || currentPlayerIndex == BB) {
                allPlayersDone = true;
            } else {
                currentPlayerIndex = (currentPlayerIndex + 1) % jogadores.size();
                firstLoop = false;
            }
        }
    }
    
    //mostrar as cartas 
    private void mostrarCartas() {
        for (Jogador jogador : jogadores) {
            System.out.println("Jogador: " + jogador.getNome());
            jogador.mostrarMao();
        }
    }
    
    //vencedor 
    private void definirVencedor() {
        Jogador vencedor = jogadores.get(0); 
        int empate = 0;

        for (int i = 1; i < jogadores.size(); i++) {
            Jogador jogadorAtual = jogadores.get(i);

            if (jogadorAtual.getForcaMao() > vencedor.getForcaMao()) {
                vencedor = jogadorAtual;
            } else if (jogadorAtual.getForcaMao() == vencedor.getForcaMao()) {
                if (jogadorAtual.getCartaMaisAlta().getValor() > vencedor.getCartaMaisAlta().getValor()) {
                    vencedor = jogadorAtual;
                } else if (jogadorAtual.getCartaMaisAlta().getValor() == vencedor.getCartaMaisAlta().getValor()) {
                    empate = 1;
                }
            }
        }

        if (empate == 0) {
            System.out.println("O vencedor é: " + vencedor.getNome());
        } else {
            System.out.println("Não houve vencedores pois ocorreu um empate!");
        }
    }

    private void distribuirPote() {
        for (Jogador jogador : jogadores) {
            jogador.receberFichas(pot / jogadores.size());
        }
    }

    private void limparMaosEPote() {
        for (Jogador jogador : jogadores) {
            jogador.limparMao();
        }
        pot = 0;
        apostaAtual = 10; // Valor inicial
        BB = (BB + 1) % jogadores.size();
        SB = (BB + 3) % jogadores.size();
    }
    
    //main
    public static void main(String[] args) {
        Controle jogo = new Controle();

        jogo.adicionarJogador(new Jogador("Joao", 200));
        jogo.adicionarJogador(new Jogador("Pedro", 200));
        jogo.adicionarJogador(new Jogador("Luiz", 200));
        jogo.adicionarJogador(new Jogador("Carlos", 200));
        jogo.adicionarJogador(new Jogador("Eduardo", 200));

        jogo.jogarRodada();
    }
}