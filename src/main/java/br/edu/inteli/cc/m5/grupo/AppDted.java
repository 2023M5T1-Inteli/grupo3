package br.edu.inteli.cc.m5.grupo;

import java.util.Optional;

import br.edu.inteli.cc.m5.dted.DtedDatabaseHandler;

/**
 * Demonstra o uso da classe DtedDatabaseHandler.
 * 
 * Este programa consulta a base de dados geográfica do Rio, cujos arquivos estão
 * em src/main/resources. Como os arquivos de dados geográficos poder ser inconvenientemente
 * grandes para serem armazenados no Git, só foram colocados nesta pasta os arquivos de
 * uma única região somente para facilitar os testes.
 * 
 * Os arquivos de múltiplas regiões não devem ser colocados no controle de versão, e podem
 * ser acessados utilizando o método Initialize (em lugar de InitializeFromResources) da
 * classe DtedDatabaseHandler.
 * 
 */
public class AppDted {
    public static void main(String[] args) {

        /** RIO sample **/
        DtedDatabaseHandler dbRio = new DtedDatabaseHandler();
        boolean dbRioOk = dbRio.InitializeFromResources("dted/Rio");

        if (dbRioOk) {
            // P1
            // Lon = -43.4855748
            // Lat = -22.5889042043
            // Alt = 1540 (valor esperado)
            Optional<Integer> ret1 = dbRio.QueryLatLonElevation(-43.4855748, -22.5889042043);
            System.out.println("ret1: " + ret1);
            
            // P2
            // Lon = -42.0024507838593
            // Lat = -23.0100316672148
            // Alt = 257 (valor esperado)
            Optional<Integer> ret2 = dbRio.QueryLatLonElevation(-42.0024507838593, -23.0100316672148);
            System.out.println("ret2: " + ret2);

            // P3
            // Lon = -43.5573428666663
            // Lat = -23.0696792891117
            // Alt = 247 (valor esperado)
            Optional<Integer> ret3 = dbRio.QueryLatLonElevation(-43.5573428666663, -23.0696792891117);
            System.out.println("ret3: " + ret3);

            // P4
            // Lon = -42.5794347619519
            // Lat = -22.359194448201
            // Alt = 1928 (valor esperado)
            Optional<Integer> ret4 = dbRio.QueryLatLonElevation(-42.5794347619519, -22.359194448201);
            System.out.println("ret4: " + ret4);
        }
        else {
            System.out.println("Não foi possível abrir a base com dados do Rio!");
        }

        /** 
         * Yosemite sample
        DtedDatabaseHandler dbYosemite = new DtedDatabaseHandler();
        boolean dbYoseOk = dbYosemite.Initialize("C:/Users/Inteli/Documents/Databases/Yosemite");

        if (dbYoseOk) {
            // P5
            // Lon = -119.2138645743
            // Lat = 37.8072336631
            // Alt = 3525 (valor esperado)
            Optional<Integer> ret5 = dbYosemite.QueryLatLonElevation(-119.2138645743, 37.8072336631);
            System.out.println("ret5: " + ret5);
        }
        else {
            System.out.println("Não foi possível abrir a base com dados de Yosemite!");
        }
        **/
        
    }
}