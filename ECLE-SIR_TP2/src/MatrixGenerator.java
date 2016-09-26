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


public class MatrixGenerator {

	public static void main(String[] args) {
		String chaine = "";
		Map<String, Integer> matrixMUT = new HashMap<String, Integer>();
		ArrayList<String> usagers = new ArrayList<String>();
		ArrayList<String> themes = new ArrayList<String>();
		String fichier = "res/log-reco.txt";
		
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
		
		
		// Réstitution de la matrice MUT dans un tableau d'int à 2 dimensions pour le fichier
		int[][] matrix = new int[usagers.size()+1][themes.size()+1];
		
		for(int i = 0; i < usagers.size(); i++){
			for(int j = 0; j < themes.size(); j++){
				if(matrixMUT.containsKey(usagers.get(i)+";"+themes.get(j))){
					matrix[i][j] = matrixMUT.get(usagers.get(i)+";"+themes.get(j));
				} else {
					matrix[i][j] = 0;
				}
			}
		}
		
		System.out.println("Pour la recommendation : ");
		System.out.println(usagers.get(usagers.size()-1));
		System.out.println(themes.get(themes.size()-1));
		
		
		// --------------------------- Création du fichier de la matrice MUT et MUT binaire
		try {
			FileWriter fw = new FileWriter ("res/matriceMUT.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			
			FileWriter fw2 = new FileWriter ("res/matriceMUT-binaire.txt");
			BufferedWriter bw2 = new BufferedWriter (fw2);
			PrintWriter fichierSortieBinaire = new PrintWriter (bw2); 
			
			for(int i = 0; i < usagers.size(); i++){
				for(int j = 0; j < themes.size(); j++){
					// MUT simple
					fichierSortie.print(matrix[i][j]);
					
					// MUT binaire
					if(matrix[i][j] != 0){
						fichierSortieBinaire.print(1);
					} else {
						fichierSortieBinaire.print(0);
					}
					
					if(j != themes.size()-1){
						fichierSortie.print(";");
						fichierSortieBinaire.print(";");
					}
						
				}
				fichierSortie.println();
				fichierSortieBinaire.println();
			}
			
			fichierSortie.close();
			fichierSortieBinaire.close();
			System.out.println("\nLe fichier de la matrice MUT a été créé !"); 
			System.out.println("\nLe fichier de la matrice MUT-binaire a été créé !"); 
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		// 
		
		// Création de la matrice Mtt dans un tableau d'int à 2 dimensions pour le fichier
		float[][] matrixMtt = new float[themes.size()+1][themes.size()+1];

		double MOneOne = 0;
		double MZeroOne = 0;
		double MOneZero = 0;
		
		float distanceJaccard = 0;
		
		for(int j = 0; j < themes.size(); j++){
			for(int k = 0; k < themes.size(); k++){
				
				for(int i = 0; i < usagers.size(); i++){
					
					// Pour calculer la distance après, l'intersection équivaut à MOneOne et l'union à MZeroOne + MOneZero + MOneOne
					if(matrix[i][j] != 0 && matrix[i][k] != 0){
						MOneOne++;
					} else if(matrix[i][j] == 0 && matrix[i][k] != 0){
						MZeroOne++;
					} else if(matrix[i][j] != 0 && matrix[i][k] == 0){
						MOneZero++;
					}
				}
			
				// Calcul de la distance entre la colonne j et la colonne k
				if(MZeroOne + MOneZero + MOneOne == 0){
					distanceJaccard = 1;
				} else {
					distanceJaccard = (float) (1 - (MOneOne / (MZeroOne + MOneZero + MOneOne)));
				}
				
				MOneOne = 0;
				MZeroOne = 0;
				MOneZero = 0;
	
				matrixMtt[j][k] = distanceJaccard;
				matrixMtt[k][j] = distanceJaccard;
			}
		}
		
		
		// --------------------------- Création du fichier de la matrice Mtt et Mtt binaire

		double seuil = 0.5;
		
		try {
			FileWriter fw = new FileWriter ("res/matriceMtt.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			
			FileWriter fw2 = new FileWriter ("res/matriceMtt-binaire.txt");
			BufferedWriter bw2 = new BufferedWriter (fw2);
			PrintWriter fichierSortieBinaire = new PrintWriter (bw2); 
			
			for(int i = 0; i < themes.size(); i++){
				for(int j = 0; j < themes.size(); j++){
					// Mtt simple
					fichierSortie.print(matrixMtt[i][j]);
					
					// Mtt binaire
					if(matrixMtt[i][j] < seuil){
						fichierSortieBinaire.print(1);
					} else {
						fichierSortieBinaire.print(0);
					}
					
					if(j != themes.size()-1){
						fichierSortie.print(";");
						fichierSortieBinaire.print(";");
					}
						
				}
				fichierSortie.println();
				fichierSortieBinaire.println();
			}
			
			fichierSortie.close();
			fichierSortieBinaire.close();
			System.out.println("\nLe fichier de la matrice Mtt a été créé !"); 
			System.out.println("\nLe fichier de la matrice Mtt-binaire a été créé !"); 
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		

		// --------------------------- Création du fichier MutR (non terminée)
		try {
			FileWriter fw = new FileWriter ("res/matriceMutR.txt");
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			
			// MutR
				
			for(int k = 0; k < themes.size(); k++){
				for(int l = 0; l < themes.size(); l++){
					if(matrixMtt[k][l] < seuil){
						for(int i = 0; i < usagers.size(); i++){
								if(matrix[i][k] != 0 && matrix[i][l] == 0){
									fichierSortie.print(matrixMtt[i][l]);
								} else if(matrix[i][k] == 0 && matrix[i][l] != 0){
									fichierSortie.print(matrixMtt[i][k]);
								}
							}
					}
				}
				fichierSortie.println();
			}
			
			fichierSortie.close();
			System.out.println("\nLe fichier de la matrice MutR a été créé !"); 
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	

}
