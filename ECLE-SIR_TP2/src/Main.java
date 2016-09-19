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
		String fichier = "res/matriceMUT.txt";
		
		int[][] tabsum = new int[8][8];
		
		// Lecture du fichier texte	
		try {
			InputStream ips = new FileInputStream(fichier); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			int indexLigne = 0;
			while((ligne = br.readLine()) != null){
				
				String[] parts = ligne.split("\\;");
				
				for(int i = 0; i < parts.length; i++){
					for(int j = 0; j < parts.length; j++){
						tabsum[i][j] += Integer.parseInt(parts[i]);
						tabsum[i][j] += Integer.parseInt(parts[j]);
					}
				}
				indexLigne++;
			}
			br.close(); 
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
					System.out.print(tabsum[i][j]+" ");
			}
			System.out.println();
		}
		
		// --------------------------- Création du fichier de la matrice MUT
		/*try {
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
		}*/
		
		
		
	}

}
