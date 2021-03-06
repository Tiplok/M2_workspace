import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.lang.*;
import java.util.*;


public class Main {

	public static void main(String[] args) {
		String chaine = "";
		Map<String, Integer> matrixMUT = new HashMap<String, Integer>();
		ArrayList<String> usagers = new ArrayList<String>();
		ArrayList<String> themes = new ArrayList<String>();
		String fichier = "res/Log-clients-themes.txt";
		
		// Lecture du fichier texte	
		try {
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while((ligne = br.readLine()) != null){
				String[] parts = ligne.split("\\;");
				
				// On vérifie qu'on a bien 3 parties : id, usager, thème
				if(parts.length == 3){
					
					// Si l'usager n'est pas dans la liste, on l'ajoute
					if(!usagers.contains(parts[1])){
						usagers.add(parts[1]);
					}

					// Si le thème n'est pas dans la liste, on l'ajoute
					if(!themes.contains(parts[2])){
						themes.add(parts[2]);
					}
					
					// Si il y a déjà une association entre l'usager et le thème trouvée, on incrémente
					if(matrixMUT.containsKey(parts[1]+";"+parts[2])){
						matrixMUT.put(parts[1]+";"+parts[2], matrixMUT.get(parts[1]+";"+parts[2])+1);
						
					// Sinon, on ajoute cette association
					} else {
						matrixMUT.put(parts[1]+";"+parts[2], 1);
					}

					
				}
				
				chaine += ligne + "\n";
			}
			br.close(); 
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		// Réstitution de la matrice dans un tableau d'int à 2 dimensions pour le fichier
		int[][] matrix = new int[usagers.size()+1][themes.size()+1];
		
		for(int i = 0; i < usagers.size(); i++){
			for(int j = 0; j < themes.size(); j++){
				matrix[i][j] = matrixMUT.get(usagers.get(i)+";"+themes.get(j));
			}
		}
		
		// --------------------------- DEBUT de l'affichage sur la console
		
		System.out.println("Nombre d'usagers : "+usagers.size());
		for(int i = 0; i < usagers.size(); i++) {   
		    System.out.println("Usager "+(i+1)+" : "+usagers.get(i));
		}  
		
		System.out.println();
		
		System.out.println("Nombre de thèmes : "+themes.size());
		for(int i = 0; i < themes.size(); i++) {   
		    System.out.println("Thème "+(i+1)+" : "+themes.get(i));
		}
		
		System.out.println();
		
		System.out.println("Matrice MUT :");  
	    printMap(matrixMUT);
	    
	    // --------------------------- FIN de l'affichage sur la console
	    
	    
		// --------------------------- Création du fichier des usagers
		try {
			FileWriter fw = new FileWriter ("res/Liste-clients.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw);
			
			for(int i = 0; i < usagers.size(); i++) {   
			    fichierSortie.println(usagers.get(i));
			} 
			
			fichierSortie.close();
			System.out.println("\nLe fichier de la liste des usagers a été créé !"); 
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		// --------------------------- Création du fichier des thèmes
		try {
			FileWriter fw = new FileWriter ("res/Liste-themes.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw);
			
			for(int i = 0; i < themes.size(); i++) {   
			    fichierSortie.println(themes.get(i));
			} 
			
			fichierSortie.close();
			System.out.println("\nLe fichier de la liste des thèmes a été créé !"); 
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		// --------------------------- Création du fichier de la matrice MUT
		try {
			FileWriter fw = new FileWriter ("res/matriceMUT.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			
			for(int i = 0; i < usagers.size(); i++){
				for(int j = 0; j < themes.size(); j++){
					fichierSortie.print(matrix[i][j]);
					if(j != themes.size()-1){
						fichierSortie.print(";");
					}
						
				}
				fichierSortie.println();
			}
			
			fichierSortie.close();
			System.out.println("\nLe fichier de la matrice MUT a été créé !"); 
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}
	
	public static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}

}
