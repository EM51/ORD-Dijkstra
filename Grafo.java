package Projeto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;



public class Grafo {
    Vertice vertices[];
    int matrizAdj[][];    
    ArrayList<Vertice> ListaAdj;
    Stack<Integer> pilha = new Stack<>();
    
    public Grafo(String file) throws IOException{
        matrizAdj = Le_arq(file);
    }    
    
    public ArrayList<Vertice> crialistAdj(int[][] matrizAdj){
        
        ArrayList<Vertice> minhaLista = new ArrayList<>();
        for(int i = 0; i < matrizAdj.length; i++){
            minhaLista.add(new Vertice());
            
        }
        for(int i = 0; i < minhaLista.size(); i++){            
            for(int j = i; j < matrizAdj.length; j++){
                
                if(i == j){
                    continue;
                    
                }
                
                minhaLista.get(i).adjacente.add(minhaLista.get(j));
                minhaLista.get(j).adjacente.add(minhaLista.get(i));
                
            }
        }
        return minhaLista;
    }
        
   public int[][] Le_arq(String file) throws FileNotFoundException, IOException {
        
        String  filename = file;
        String arquivo = "everaldo/ORD "+filename;
        
        FileReader arq =  new FileReader(arquivo);       
        BufferedReader ler_arq = new BufferedReader(arq);
        
        String linha = ler_arq.readLine();
        linha.replace("\t", " ");
        
        int num_vertices = Integer.parseInt(linha);
        
        matrizAdj = new int[num_vertices][num_vertices];
        
        int tam_vec = 0;
        for(int i = 1; i <= num_vertices; i++){
            tam_vec += (num_vertices - i);
        }
        
        String[] ar;
        int[] vec = new int[tam_vec];
        int aux = 0;
        
        do{
            linha = ler_arq.readLine();
            
            if(linha == null) break;
            String lin;
            lin = linha.replace("\t", " ");
            
            ar = lin.split(" ");
            
            for(int i = 0; i< ar.length; i++){              
                vec[aux] = Integer.parseInt(ar[i]);
                aux++;
            }
                                 
        }while(linha != null);
        
        int aux2 = 0;        
        for(int i = 0; i < matrizAdj.length; i++){
            for(int j = i; j < matrizAdj.length; j++){
                if(i == j){
                    matrizAdj[i][j] = 0;
                }else{
                    matrizAdj[i][j] = vec[aux2];
                    matrizAdj[j][i] = matrizAdj[i][j];
                    aux2++;
                }
            }
        }               
               
        for(int i = 0; i < matrizAdj.length; i++){
            System.out.println(" ");
            for(int j = 0; j < matrizAdj.length; j++){                
                System.out.print(matrizAdj[i][j]+"\t");
            }            
        }
        return matrizAdj;        
    }
   
    public void preencheVertice(){
        vertices = new Vertice[ListaAdj.size()];
        
        for(int i = 0; i < ListaAdj.size(); i++){            
            vertices[i] = ListaAdj.get(i);
            
        }
    }
    
    public void relax(Vertice atual, Vertice proximo, int peso_adj, FilaMinima f){ 
        
        if(proximo.valor > atual.valor + peso_adj){
            proximo.valor = atual.valor + peso_adj;
            f.heap_decreaseKey(proximo.id, proximo.valor);
            proximo.pred = atual.id_orig;
            
        }
        
    }
    
    public void dijkstra(Vertice origem){
        origem.valor = 0;
        
        FilaMinima fila = new FilaMinima(this.vertices);
        fila.preencher(this.vertices);
        
        fila.construirHeap();
                       
        while(fila.tamHeap != 0){           
            Vertice u = fila.heap_extractMin();
               
                for(int i = 0; i < u.adjacente.size(); i++){ 
                   relax(u, u.adjacente.get(i), this.matrizAdj[u.id_orig][u.adjacente.get(i).id_orig], fila);                    
                }
        }
        
        int i = vertices.length-1;
        pilha.push(vertices[i].id_orig);
        while(vertices[i].valor != 0){            
            pilha.push(vertices[i].pred);
            i = vertices[i].pred;            
        }
        
        System.out.println("\n\nID: " + vertices[vertices.length-1].id_orig + "\tvalor: " + vertices[vertices.length-1].valor);
        System.out.print("\nCaminho de 0 até n-1:");
        while(!pilha.empty()){
            
            System.out.print(" " + pilha.pop());
        }
        System.out.println("");
        
        
    }    
}
