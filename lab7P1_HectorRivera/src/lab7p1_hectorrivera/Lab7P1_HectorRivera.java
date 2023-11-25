package lab7p1_hectorrivera;


import java.util.Random;
import java.util.Scanner;

public class Lab7P1_HectorRivera {

    static Random rd = new Random(); 
    static Scanner sc = new Scanner(System.in);
    static Scanner scS = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("---------------Menu---------------");
        System.out.println("1.Tres en raya\n 2.Puntos de silla\n3.salir");
        int opc = sc.nextInt();
        
        while (opc!=3){

            switch(opc){
                
                case 1:{
                    char desc = 's';
                    do {
                        char[][] tab = new char[3][3]; 
                        System.out.println("Tablero Actual: ");
                        tab = GenerarTablero(tab);
                        print(tab);
                        int x,y,Cturn=1;
                        boolean winC = false;
                        char turn=' ';
                        while(Cturn<=9&&winC==false){
                            if (Cturn%2!=0) {
                                fillUser(tab);
                                turn='X';
                            }else{
                                fillAI(tab);
                                turn='0';
                            }
                            Cturn++;
                            if (Cturn>=5) {
                                winC=VerificarVictoria(tab,turn);  
                            }
                            print(tab);
                        }
                        if (winC==true) {
                            System.out.println(turn+" ha ganado");
                        }else{
                            System.out.println("Empate");
                        }
                        System.out.println("¿Quiere jugar otra vez? (s/n)");
                        desc = scS.next().charAt(0);
                    } while(desc == 's' ||desc == 'S');
                }break;
                
                case 2:{
                    char desc = 's';
                    do{
                        System.out.println("Ingrese el tamaño: ");
                        int tam = sc.nextInt();
                        tam=verificarNum(tam);
                        int[][] mat = new int[tam][tam];
                        mat =generarIntMatrizAleatoria(mat);
                        print2(mat);
                        encontrarPuntosSilla(mat);
                        System.out.println("");
                        System.out.println("¿Quiere probar otra vez? (s/n)");
                        desc = scS.next().charAt(0);
                    }while(desc == 's' ||desc == 'S');
                }break;    
                
                default:
                    System.out.println("");
                    System.out.println("Ingrese un valor valido");
                   
            }
            System.out.println("---------------Menu---------------");
            System.out.println("1.Tres en raya\n 2.Puntos de silla\n3.salir");
            opc = sc.nextInt();
        }
    }
    public static void print(char[][] mat){
        for (int i = 0; i < mat.length; i++) {
            System.out.print("[");
            for (int j = 0; j < mat.length; j++) {
                if(mat[i][j]!='1'){
                    System.out.print(" "+mat[i][j]);
                }else{
                    System.out.print(" ");
                }
                if (j<2) {
                    System.out.print(" ,");
   
                }
                
            }
            System.out.println(" ] ");
        }
    }
    
    //Metodos del X0
    public static char[][] GenerarTablero(char[][] mat){
        char[][] temp = mat;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                    temp[i][j] = '1';
            }
        }
        return temp;
    }
    public static char[][] fillUser(char[][] mat){
        System.out.println("Es el turno de: X" );
        int x=0,y=0;
        
        do{
        System.out.println("Ingrese fila (0, 1, 2): ");
        y = sc.nextInt();
        verificar(y);
        System.out.println("Ingrese columna (0, 1, 2): ");
        x = sc.nextInt();
        verificar(x);
            if (VerificarPosicionValida(y,x,mat) == false) {
                System.out.println("Ingrese una casilla vacia");
            }
        }while(VerificarPosicionValida(y,x,mat) == false);
        
        mat[y][x] = 'X';
        return mat;
    }
    public static char[][] fillAI(char[][] mat){
        System.out.println("Es el turno de: 0" );
        
        int y = rd.nextInt(2);
        int x = rd.nextInt(2);
        while (VerificarPosicionValida(y,x,mat) == false) {
           y = rd.nextInt(2);
           x = rd.nextInt(2); 
        }
        mat[y][x] = '0';
        return mat;
    }
    public static int verificar(int num){
        while(num<0&&num>2){
            System.out.println("Ingrese valor entre rango correcto (0, 1, 2): ");
            num = sc.nextInt();
        }
        return num;
    }
    public static boolean VerificarPosicionValida(int num1,int num2,char[][] mat){
        boolean check = false;
        if (mat[num1][num2]!='0'&&mat[num1][num2]!='X') {
            check = true;
        }
        return check;
    }
    public static boolean VerificarVictoria(char[][] mat,char turn){
        int contD1=0,contD2=0;
        
        for (int i = 0; i < mat.length; i++) {
            int contF=0,contC=0;
            for (int j = 0; j < mat.length; j++) {
                
                if (mat[i][j] == turn) {
                    contF++;
                }
                if( (i==j)&(mat[i][j]==turn) ){
                    contD1++;
                }
                if( (i+j==mat.length-1)&(mat[i][j]==turn) ){
                    contD2++;
                }
                if(mat[j][i] == turn){
                    contC++;
                }    
            }
            
            if (contF==3||contC==3) {
                return true;
            }
            
        }
        
        if (contD1==3||contD2==3) {
                return true;
        }
        return false;
    }
    //metodos punto silla
    public static int[][] generarIntMatrizAleatoria(int[][] mat){
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                mat[i][j] = rd.nextInt(99);
            }
        }
        return mat;
    }
    public static int verificarNum(int num){
        while(num<0){
            System.out.println("Ingrese valor entre rango correcto: ");
            num = sc.nextInt();
        }
        return num;
    }
    public static void encontrarPuntosSilla(int[][] mat){        
        boolean found=false;
        int ac = 0;
        
        for (int y = 0; y < mat.length; y++) {
            for (int x = 0; x < mat.length; x++) {
                ac = mat[y][x];
                int min=0,max=0;
                for (int i = 0; i < mat.length; i++) {
                    if (ac<=mat[x][i]) {
                        min+=1;
                    }
                    if (ac>=mat[i][x]) {
                        max+=1;
                    }
                }
                if (min==mat.length && max==mat.length) {
                    System.out.println("Punto silla: "+ac+"|poscicion: "+y+" "+x);
                    found=true;
                
                }
            }
        }
        if (found==false) {
            System.out.println("No hay punto silla");
        }
                   
    }
  
    public static void print2(int[][] mat){
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print("["+mat[i][j]+"]");
            }
            System.out.println("");
        }
    }
}
