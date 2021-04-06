package fachada;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import dao.DAO;
import dao.DAOPessoa;
import dao.DAOTelefone;
import modelo.Aluno;
import modelo.Pessoa;
import modelo.Professor;
import modelo.Telefone;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Fachada {
	private static DAOPessoa daopessoa = new DAOPessoa();  
	private static DAOTelefone daotelefone = new DAOTelefone();  


	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}

	public static Pessoa cadastrarPessoa(String nome) throws  Exception{
		DAO.begin();	
		Pessoa p = daopessoa.read(nome);
		if(p != null) {
			DAO.rollback();
			throw new Exception("pessoa ja cadastrado:" + nome);
		}
		p = new Pessoa(nome);
		daopessoa.create(p);	
		DAO.commit();
		return p;
	}

	public static Pessoa cadastrarTelefone(String nome, String numero) throws  Exception{
		DAO.begin();	
		Pessoa p = daopessoa.read(nome);
		if(p == null) {
			p = new Pessoa(nome);
			daopessoa.create(p);		//pessoa nova
		}
		Telefone t = daotelefone.read(numero);
		if (t!=null){
			DAO.rollback();
			throw new Exception("numero ja cadastrado:" + numero);
		}
		t=new Telefone(numero);
		p.adicionar(t);
		daopessoa.update(p);

		daotelefone.create(t);//daotelefone.update(t);

		DAO.commit();
		return p;
	}	

	public static Aluno cadastrarAluno(String nome, double nota) 
			throws  Exception{
		DAO.begin();	
		Pessoa p = daopessoa.read(nome);
		if(p != null) {
			DAO.rollback();
			throw new Exception("cadastrar aluno - pessoa ja cadastrado:" + nome);
		}

		Aluno a = new Aluno(nome, nota);
		daopessoa.create(a);	
		DAO.commit();
		return a;
	}

	public static Professor cadastrarProfessor(String nome, double salario) 
			throws  Exception{
		DAO.begin();	
		Pessoa p = daopessoa.read(nome);
		if(p != null) {
			DAO.rollback();
			throw new Exception("cadastrar professor - pessoa ja cadastrado:" + nome);
		}
		Professor prof = new Professor(nome,salario);
		daopessoa.create(prof);	
		DAO.commit();
		return  prof;
	}

	//	public static void criarApelidos(String nome, String[] apelidos) throws  Exception{
	//		DAO.begin();	
	//		Pessoa p = daopessoa.read(nome);	
	//		if(p == null) {
	//			DAO.rollback();	
	//			throw new Exception("pessoa inexistente:" + nome);
	//		}
	//		
	//		p.adicionarApelidos(apelidos);
	//		
	//		daopessoa.update(p);		
	//		DAO.commit();
	//	}

	public static Pessoa alterarPessoa(String nome, String novonome) throws Exception{
		DAO.begin();		
		Pessoa p = daopessoa.read(nome);	//usando  chave primaria
		if (p==null) {
			DAO.rollback();
			throw new Exception("nome inexistente:" + nome);
		}
		p.setNome(novonome); 			
		p=daopessoa.update(p);     	
		DAO.commit();
		return p;
	}

	public static Telefone alterarTelefone(String numero, String novonumero) throws Exception{
		DAO.begin();		
		Telefone t = daotelefone.read(numero);	
		if (t==null) {
			DAO.rollback();	
			throw new Exception("alterar telefone - numero inexistente:" + numero);
		}
		Telefone t2 = daotelefone.read(novonumero);	
		if (t2!=null) {
			DAO.rollback();	
			throw new Exception("alterar telefone - novo numero ja existe:" + novonumero);
		}
		t.setNumero(novonumero); 	//trocar		
		t=daotelefone.update(t);     	
		DAO.commit();	
		return t;
	}

	public static void excluirPessoa(String n) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(n);
		if (p==null) {
			DAO.rollback();	
			throw new Exception("nome inexistente:" + n);
		}
		daopessoa.delete(p);  //pessoa e telefones removidos em cascata
		DAO.commit();
	}

	public static void excluirTelefone(String numero) throws Exception {
		DAO.begin();
		Telefone t = daotelefone.read(numero);
		if (t==null) {
			DAO.rollback();
			throw new Exception("numero inexistente:" + numero);
		}


		//		Pessoa p = t.getPessoa();
		//		p.remover(t);
		//		t.setPessoa(null);
		//		daopessoa.update(p);
		daotelefone.delete(t);	
		DAO.commit();
	}

	//	public static void excluirTelefonesFixos(String nome) throws Exception {
	//		DAO.begin();
	//		Pessoa p = daopessoa.read(nome);
	//		if (p==null) {
	//			DAO.rollback();	
	//			throw new Exception("nome inexistente:" + nome);
	//		}
	//		int cont=0;
	//		Iterator<Telefone> iterator = p.getTelefones().iterator();
	//		while(iterator.hasNext()){
	//			Telefone t = (Telefone) iterator.next();
	//
	//			if (!t.ehCelular()) {
	//				t.setPessoa(null);		//desliga o telefone
	//				iterator.remove();		
	//				daotelefone.delete(t);	//apaga o telefone
	//				cont++;
	//			}
	//		}
	//		if(cont==0) {
	//			DAO.rollback();
	//			throw new Exception(nome + " nao possui telefone fixo");
	//		}
	//
	//		daopessoa.update(p);
	//		DAO.commit();
	//
	//	}

	public static List<Pessoa> listarPessoas(){
		return daopessoa.readAll();
	}

	public static List<Telefone> listarTelefones(){
		return daotelefone.readAll();
	}

	/**********************************************************
	 * 
	 * RELATORIO JASPER REPORT 
	 * 
	 * compila relat�rio e abre a janela de visualiza��o
	 * 
	 * **********************************************************/
	public static void gerarRelatorioJasper(String arqjrxml) throws Exception {
		try {
			Connection con = DAO.getConnection();  //conexao JDBC
			if (con==null) 
				throw new Exception("conex�o inexistente");
			
			InputStream istream = Fachada.class.getClassLoader().getResourceAsStream(arqjrxml);
			JasperReport report = JasperCompileManager.compileReport(istream);
			
			HashMap<String,Object>  parametros = new HashMap<>();   //obrigatorio mesmo vazio
			JasperPrint print = JasperFillManager.fillReport(report, parametros, con);
			JasperViewer.viewReport(print, false);  //false n�o fecha a janela principal da aplica��o
		}
		catch(JRException e) {
			throw new Exception ("erro de relat�rio:"+e.getMessage());
		}
	
	}

	
	public static void gerarRelatorioJasperParametro(String arqjrxml, String nom) throws Exception {
		try {
			Connection con = DAO.getConnection(); 	//conexao JDBC
			if (con==null) 
				throw new Exception("conex�o inexistente");
			
			InputStream istream = Fachada.class.getClassLoader().getResourceAsStream(arqjrxml);
			JasperReport report = JasperCompileManager.compileReport(istream);
			
			HashMap<String,Object>  parametros = new HashMap<>();   //obrigatorio mesmo vazio
			parametros.put("pnome", nom);
			
			JasperPrint print = JasperFillManager.fillReport(report, parametros, con);
			JasperViewer.viewReport(print, false);  //false n�o fecha a janela principal da aplica��o
		}
		catch(JRException e) {
			throw new Exception ("erro de relat�rio:"+e.getMessage());
		}
	
	}

	/**********************************************************
	 * 
	 * CONSULTAS 
	 * 
	 **********************************************************/
	
	public static String consultarPessoasNTelefones(int n) {
		List<Pessoa> result = daopessoa.consultarPessoasNTelefones(n);

		String texto = "Consultar pessoas com "+n+" telefones";
		if (result.isEmpty())  
			texto += "nenhum resultado";
		else 
			for(Pessoa p: result)
				texto += "\n" + daopessoa.read(p.getNome());
		return texto;
	}


}
