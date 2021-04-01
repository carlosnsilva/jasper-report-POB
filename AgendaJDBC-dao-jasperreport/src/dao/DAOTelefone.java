/**
 * IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */
package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import modelo.Telefone;


public class DAOTelefone extends DAO<Telefone> {

	public  void create(Telefone t) 	{
		try{
			String sql="" ;
			sql= "insert into Telefone (idpessoa,numero) values ("+t.getPessoa().getId()+",'" + t.getNumero() + "')";
			PreparedStatement st  = con.prepareStatement(sql);
			st.executeUpdate();

			//obter o id do objeto gravado
			st=con.prepareStatement("select id from Telefone where numero= '" + t.getNumero() + "'");
			ResultSet rs = st.executeQuery();
			int id;
			if(rs.next()) {
				id = rs.getInt("id");
				t.setId(id);
			}
			st.close();
		}catch(Exception e){ 
			throw new RuntimeException(e.getMessage());
		}
	}

	public  Telefone read(Object chave) {
		String sql="";
		String numero = (String) chave ;
		Telefone t=null;
		int idtel;
		try{
			sql = "select * from telefone where numero = '"+ numero+"'";
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				idtel = rs.getInt("id");
				numero = rs.getString("numero");
				t = new Telefone(idtel,numero,null) ;
				// falta ler a pessoa relacionada !!!!
				st.close();
			}
			else
				t = null;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		return t;
	}

	public  Telefone update(Telefone t) 	{
		try{
			String sql="" ;
			sql= "update Telefone set numero = '"+ t.getNumero() + "'  where id = "+t.getId() ;
			PreparedStatement st =	con.prepareStatement(sql);
			st.executeUpdate();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		return t;
	}

	public  void delete(Telefone t)	{
		try{
			String sql="" ;
			sql= "delete from Telefone where numero = '"+t.getNumero() +"'" ;
			PreparedStatement st =	con.prepareStatement(sql);
			st.executeUpdate();
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}

	public  ArrayList<Telefone> readAll() 	{
		String numero ;
		int id, idpessoa;
		ArrayList<Telefone> resultado = new ArrayList<Telefone>();
		String sql="";
		try{
			sql = "select id,idpessoa, numero from telefone order by id";
			PreparedStatement st =	con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
				idpessoa = rs.getInt("idpessoa");
				numero = rs.getString("numero");
				//falta ler a pessoa relacionada !!!
				Telefone t = new Telefone(id,numero, null);
				resultado.add(t);
			}
			st.close();
		}catch(Exception e){ 
			throw new RuntimeException(e.getMessage());
		}
		return resultado;
	}
}
