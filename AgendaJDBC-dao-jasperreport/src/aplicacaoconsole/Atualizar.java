package aplicacaoconsole;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import fachada.Fachada;


public class Atualizar {

	public Atualizar(){
		Fachada.inicializar();
		try {
			Fachada.alterarPessoa("paulo", "paula");
			System.out.println("alterando paulo para paula");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Fachada.alterarTelefone("32471234", "32470000");
			System.out.println("alterando telefone de maria para 32470000");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		Fachada.finalizar();
		System.out.println("fim do programa");
	}




	//=================================================
	public static void main(String[] args) {
		new Atualizar();
	}
}

