package fr.oms.dataloader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import fr.oms.metier.Sport;
import fr.oms.modele.Manager;


public class SportParser {

	private List<Sport> listeReference;
	private List<Sport> listeEnCoursDeTraitement;
	private List<Sport> listeDesSportsARemplir;

	public SportParser() {
		
		listeReference=Manager.getInstance().getListeSport();
	}

	public List<Sport> parse(String chaineADecouper){	
		listeDesSportsARemplir=new ArrayList<Sport>();
		Sport sport=null;
		if(!chaineADecouper.equals("")){
			while(chaineADecouper.length()>2){
				listeEnCoursDeTraitement=recupereLaListeDesSportsCommencantPar(chaineADecouper.charAt(0));
				sport=recupererUnSport(chaineADecouper.trim());
				if(sport!=null){
					listeDesSportsARemplir.add(sport);
					chaineADecouper=chaineADecouper.substring(sport.getNom().length());
				}else{
					break;
				}
			}

		}
		return listeDesSportsARemplir;
	}

	private List<Sport> recupereLaListeDesSportsCommencantPar(char charAt) {
		ArrayList<Sport> tmp=new ArrayList<Sport>();
		for(Sport s:listeReference){
			if(s.getNom().charAt(0)==charAt){
				tmp.add(s);
			}
		}
		return tmp;
	}

	/*private void afficherLesSport(List<String> liste) {
		for(String s:liste){
			System.out.println(s);
		}		
	}*/

	private Sport recupererUnSport(String chaine){
		//System.out.println("Je dois couper : "+chaine);
		String compare=String.valueOf(chaine.charAt(0));
		Pattern pattern = null;
		Matcher matcher;

		//On part de 1 puisque le premier caractère est deja récupéré précedement
		int i=1;
		//Tant qu'on a plus d'un sport dans la liste
		while(listeEnCoursDeTraitement.size()>1){
			if(chaine.length()>i){
				compare+=String.valueOf(chaine.charAt(i));
			}else{
				return Manager.getInstance().recupereLeSport(compare);
			}

			try{
				pattern=Pattern.compile("^"+compare+".*");			
			}catch(PatternSyntaxException e){
				//pattern=Pattern.compile("^"+compare+".*");	
			}

			//System.out.println(pattern);
			Iterator<Sport> iter = listeEnCoursDeTraitement.iterator();
			while (iter.hasNext()) {
				String s=iter.next().getNom();
				matcher=pattern.matcher(s);
				if(!matcher.matches()){
					iter.remove();
					//System.out.println("Je viens de virer :"+s);
				}
			}
			//si on a plus de sport => sport primaire (Ex: SKI, GYMNASTIQUE) 
			if(listeEnCoursDeTraitement.size()==0){
				if(compare.contains(")")){
					//System.out.println("Je retourne parce que vide : "+compare.substring(0, compare.length()));
					return Manager.getInstance().recupereLeSport(compare.substring(0, compare.length()));
				}
				//System.out.println("Je retourne parce que vide : "+compare.substring(0, compare.length()-1));
				return Manager.getInstance().recupereLeSport(compare.substring(0, compare.length()-1));
			}
			i++;
		}
		//System.out.println("Le sport a ajouter : "+listeEnCoursDeTraitement.get(0));
		return listeEnCoursDeTraitement.get(0);
	}
}