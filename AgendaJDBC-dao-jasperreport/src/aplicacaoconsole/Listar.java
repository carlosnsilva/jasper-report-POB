/**
 * IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */
package aplicacaoconsole;

import fachada.Fachada;
import modelo.Pessoa;
import modelo.Telefone;

public class Listar {

	public Listar(){	
		try {
			Fachada.inicializar();

			System.out.println("Listagem de pessoas:");
			for(Pessoa p : Fachada.listarPessoas() )		
				System.out.println(p);

			System.out.println("\nListagem de telefones:");
			for(Telefone t : Fachada.listarTelefones())	
				System.out.println(t);


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			Fachada.finalizar();
			System.out.println("fim do programa");
		}
	}


	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
	//=================================================

}
