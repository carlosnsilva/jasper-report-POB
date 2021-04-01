package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

public class Pessoa{
	private int id;
	private String nome;
	private LocalDate dtcadastro  = LocalDate.now();
	private List<Telefone> telefones = new ArrayList<Telefone>();

	public Pessoa() {}

	//construção na fachada
	public Pessoa(String nome) {
		this.nome = nome;
	}
	public Pessoa(String nome, String num) {
		this.nome = nome;
		this.adicionar(new Telefone(num));
	}
	public Pessoa(String nome, ArrayList<Telefone> telefones) {
		this.nome = nome;
		this.telefones = telefones;
	}
	
	//construção no DAO
	public Pessoa(int id, String nome, LocalDate data) {
		this.id = id;
		this.nome = nome;
		this.dtcadastro = data;
	}

	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void adicionar(Telefone t){
		t.setPessoa(this);
		this.telefones.add(t);
	}

	public void remover(Telefone t){
		t.setPessoa(null);
		this.telefones.remove(t);
	}

	public Telefone localizar(String num){
		for(Telefone t : telefones)
			if (t.getNumero().equals(num))
				return t;
		return null;
	}



	@Override
	public String toString() {
		String classe = getClass().getSimpleName() + ":";
		String texto =  
				"id="+id+ ", nome=" + String.format("%8s", nome) + 
				", cadastro=" + dtcadastro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		texto += ", telefones:";
		for(Telefone t : telefones)
			texto+= t.getNumero() + ", ";

		return texto;
	}

	public LocalDate getDtcadastro() {
		return dtcadastro;
	}







}
