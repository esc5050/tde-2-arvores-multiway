import java.util.Scanner;

class No23 {
    int[] chaves = new int[3];
    No23[] filhos = new No23[4];
    int numChaves = 0;

    boolean estaCheio() {
        return numChaves == 3;
    }
    boolean ehFolha() {
        return filhos[0] == null;
    }
}

public class Arvore23 {
    private No23 raiz;

    public Arvore23() {
        raiz = new No23();
    }

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No23 inserirRec(No23 no, int valor) {
        if (no.ehFolha()) {
            inserirEmNo(no, valor);
        } else {
            int i = 0;
            while (i < no.numChaves && valor > no.chaves[i]) {
                i++;
            }
            No23 filho = inserirRec(no.filhos[i], valor);
            if (filho != no.filhos[i]) {
                inserirEmNo(no, filho.chaves[0], no.filhos[i], filho.filhos[1]);
            }
        }

        if (no.estaCheio()) {
            return dividirNo(no);
        }
        
        return no;
    }

    private No23 dividirNo(No23 noCheio) {
        No23 novoPai = new No23();
        No23 novoIrmao = new No23();
        
        novoPai.chaves[0] = noCheio.chaves[1];
        novoPai.numChaves = 1;

        novoIrmao.chaves[0] = noCheio.chaves[2];
        novoIrmao.numChaves = 1;

        noCheio.numChaves = 1;
        
        novoPai.filhos[0] = noCheio;
        novoPai.filhos[1] = novoIrmao;
        
        if(!noCheio.ehFolha()){
            novoIrmao.filhos[0] = noCheio.filhos[2];
            novoIrmao.filhos[1] = noCheio.filhos[3];
        }

        return novoPai;
    }

    private void inserirEmNo(No23 no, int valor) {
        int i = no.numChaves - 1;
        while (i >= 0 && valor < no.chaves[i]) {
            no.chaves[i + 1] = no.chaves[i];
            i--;
        }
        no.chaves[i + 1] = valor;
        no.numChaves++;
    }
    
     private void inserirEmNo(No23 no, int valor, No23 filhoEsquerdo, No23 filhoDireito) {
        int i = no.numChaves - 1;
        while (i >= 0 && valor < no.chaves[i]) {
            no.chaves[i + 1] = no.chaves[i];
            no.filhos[i+2] = no.filhos[i+1];
            i--;
        }
        no.chaves[i + 1] = valor;
        no.filhos[i+1] = filhoEsquerdo;
        no.filhos[i+2] = filhoDireito;
        no.numChaves++;
    }


    public void remover(int valor) {
        
        No23 no = buscarFolhaParaRemover(raiz, valor);
        if (no != null) {
            removerChaveDeNo(no, valor);
            System.out.println("-> Removido.");
        } else {
            System.out.println("-> Valor não encontrado!");
        }
    }

    private No23 buscarFolhaParaRemover(No23 no, int valor) {
        int i = 0;
        while (i < no.numChaves && valor > no.chaves[i]) i++;
        
        if(i < no.numChaves && no.chaves[i] == valor && no.ehFolha()){
            return no;
        }

        if (no.ehFolha()) {
            return null;
        }
        return buscarFolhaParaRemover(no.filhos[i], valor);
    }
    
    private void removerChaveDeNo(No23 no, int valor) {
        int i = 0;
        while (i < no.numChaves && valor != no.chaves[i]) i++;
        
        for (int j = i; j < no.numChaves - 1; j++) {
            no.chaves[j] = no.chaves[j+1];
        }
        no.numChaves--;
    }


    public void imprimir() {
        imprimirRec(raiz);
        System.out.println();
    }

    private void imprimirRec(No23 no) {
        if (no == null) return;
        
        for (int i = 0; i < no.numChaves; i++) {
            if (!no.ehFolha()) {
                imprimirRec(no.filhos[i]);
            }
            System.out.print(no.chaves[i] + " ");
        }

        if (!no.ehFolha()) {
            imprimirRec(no.filhos[no.numChaves]);
        }
    }

    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }
    
    private boolean buscarRec(No23 no, int valor) {
         if (no == null) return false;

        int i = 0;
        while (i < no.numChaves && valor > no.chaves[i]) i++;

        if (i < no.numChaves && valor == no.chaves[i]) {
            return true;
        }

        if (no.ehFolha()) {
            return false;
        }
        return buscarRec(no.filhos[i], valor);
    }
    

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        Arvore23 arvore = new Arvore23();

        while (true) {
            System.out.println("MENU ÁRVORE 2-3");
            System.out.println("1 - Inserir");
            System.out.println("2 - Buscar");
            System.out.println("3 - Remover");
            System.out.println("4 - Mostrar");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            int opcao = leitor.nextInt();
            if (opcao == 0) break;
            
            int valor;
            switch (opcao) {
                case 1:
                    System.out.print("Valor para inserir: ");
                    valor = leitor.nextInt();
                    arvore.inserir(valor);
                    System.out.println("-> Inserido.");
                    break;
                case 2:
                    System.out.print("Valor para buscar: ");
                    valor = leitor.nextInt();
                    System.out.println(arvore.buscar(valor) ? "-> Encontrado!" : "-> Não encontrado.");
                    break;
                case 3:
                    System.out.print("Valor para remover: ");
                    valor = leitor.nextInt();
                    arvore.remover(valor);
                    break;
                case 4:
                    System.out.print("Valores: ");
                    arvore.imprimir();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        leitor.close();
    }
}
