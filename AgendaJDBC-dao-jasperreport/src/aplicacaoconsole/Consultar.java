/**
 * IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */
package aplicacaoconsole;
import fachada.Fachada;


public class Consultar {

	public Consultar(){	
		Fachada.inicializar();		
		System.out.println(Fachada.consultarPessoasNTelefones(3) );
		Fachada.finalizar();
	}


	//=================================================
	public static void main(String[] args) {
		new Consultar();
	}
	//=================================================

}
