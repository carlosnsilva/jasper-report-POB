package modelo;


/**IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */


 		 
public class Telefone{

	private int id;
	private String numero;
	private Pessoa pessoa;

	public Telefone (){}
	public Telefone(String numero) {
		this.numero = numero;
	}
	public Telefone(Pessoa p, String numero) {
		this.pessoa=p;
		this.numero = numero;
	}
	public Telefone(int id, String numero, Pessoa p) {
		this.id=id;
		this.numero = numero;
		this.pessoa = p;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//	--------------------RELACIONAMENTO--------------------------------
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	@Override
	public String toString() {

		return " id=" + id + ", "
				+ (numero != null ? "numero=" + numero + ", " : "")
				+ (pessoa != null ? "pessoa=" + pessoa.getNome()  : "");
	}

//
	
}
