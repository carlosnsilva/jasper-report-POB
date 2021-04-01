package aplicacaoconsole;
/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

import fachada.Fachada;
import modelo.Pessoa;


public class Cadastrar {

	public Cadastrar(){
		try {
			Fachada.inicializar();
			
			System.out.println("cadastrando...");
			Pessoa p;
			p=Fachada.cadastrarTelefone("joao","988880000");
			p=Fachada.cadastrarTelefone("joao","988881111");	
			p=Fachada.cadastrarTelefone("maria","987882222");
			p=Fachada.cadastrarTelefone("maria","988883333");
			p=Fachada.cadastrarTelefone("maria","32471234");
			p=Fachada.cadastrarTelefone("jose","987884444");
			
			p=Fachada.cadastrarAluno("paulo", 9.0);
			p=Fachada.cadastrarTelefone("paulo","988885555");
			
			p=Fachada.cadastrarProfessor("fausto", 1000);
			
//			Fachada.criarApelidos("joao", new String[] {"jo", "joaozinho"});
//			Fachada.criarApelidos("maria", new String[] {"mary", "mar"});
//			Fachada.criarApelidos("jose", new String[] {"zezinho", "zezao"});
//			Fachada.criarApelidos("paulo", new String[] {"paulao"});
			
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}
		finally {
			Fachada.finalizar();
		}
		System.out.println("fim do programa");
	}


	public void cadastrar(){

	}	


	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}


