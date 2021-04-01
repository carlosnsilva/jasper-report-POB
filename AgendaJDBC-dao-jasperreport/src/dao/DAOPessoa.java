/**
 * IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */
package dao;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.Pessoa;
import modelo.Telefone;


public class DAOPessoa extends DAO<Pessoa> {

	public  void create(Pessoa p) {
		try{
			PreparedStatement st=con.prepareStatement("insert into Pessoa (nome,dtcadastro) values (?,?)");
			st.setString(1,p.getNome());
			st.setDate(2, Date.valueOf( p.getDtcadastro() )); //st.setDate(1, Timestamp.valueOf( p.getDtcadastro() ));
			st.executeUpdate();

			//obter o id do objeto gravado
			st=con.prepareStatement("select id from Pessoa where nome=?");
			st.setString(1,p.getNome());
			ResultSet rs = st.executeQuery();
			int id;
			if(rs.next()) {
				id = rs.getInt("id");
				p.setId(id);
			}
			st.close();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}

	public  Pessoa read(Object chave) 	{
		Pessoa pess=null;
		int idtel;
		int id;
		LocalDate dt;
		String numero ;
		try{
			String nome = (String) chave ;
			PreparedStatement st = con.prepareStatement("select * from Pessoa where nome =  ?");
			st.setString(1,nome);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				id = rs.getInt("id");
				nome = rs.getString("nome");
				dt= rs.getDate("dtcadastro").toLocalDate();  
				pess = new Pessoa(id,nome,dt);
				
				//ler seus telefones
				st = con.prepareStatement("select * from telefone where idpessoa = ?");
				st.setInt(1,id);
				rs = st.executeQuery();
				while(rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					pess.adicionar(new Telefone(idtel, numero, pess) );
				}
				st.close();
			}
			else
				pess = null;
		}catch(Exception e){ 
			throw new RuntimeException(e.getMessage());
		}
		return pess;

	}


	public Pessoa update(Pessoa p) 	{
		try{
			PreparedStatement st =con.prepareStatement("update Pessoa set nome = '"+ p.getNome() + "'  where id = "+p.getId());
			int i = st.executeUpdate();
			st.close();
		}catch(Exception e){ }
		return p;
	}

	public  void delete(Pessoa p) {
		try{
			//cascata
			PreparedStatement st =	con.prepareStatement("delete from Telefone where idpessoa = ?");
			st.setInt(1, p.getId());
			st.executeUpdate();
			
			st =con.prepareStatement("delete from Pessoa where id = ?");
			st.setInt(1, p.getId());
			int i = st.executeUpdate();
			
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}

	}

	public  ArrayList<Pessoa> readAll() {
		int id, idtel;
		LocalDate dt;
		String nome,numero ;
		ArrayList<Pessoa> resultados = new ArrayList<Pessoa>();
		try{
			//ler todas as pessoas
			PreparedStatement st =	con.prepareStatement("select id, nome, dtcadastro from pessoa order by id");
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
				nome = rs.getString("nome");
				dt= rs.getDate("dtcadastro").toLocalDate();
				Pessoa p = new Pessoa(id,nome,dt);
				resultados.add(p);
			}
			//ler os telefones de cada pessoa
			for(Pessoa p:resultados){
				st = con.prepareStatement("select * from telefone where idpessoa = "+ p.getId());
				rs = st.executeQuery();
				while(rs.next()) {
					idtel = rs.getInt("id");
					numero = rs.getString("numero");
					p.adicionar(new Telefone(idtel,numero,p) );
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		return resultados;

	}

	//----------------------CONSULTAS----------------------------

	public  ArrayList<Pessoa> consultarPessoasNTelefones(int n){
		int id;
		String nome ;
		LocalDate dt;
		ArrayList<Pessoa> resultados = new ArrayList<Pessoa>();
		String sql="";
		try{
			sql = "select p.id,nome,dtcadastro,count(*) from pessoa p, telefone t where p.id=t.idpessoa "+
					" group by p.id having count(*) = " + n;
			PreparedStatement st =	con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");		
				nome = rs.getString("nome");
				dt= rs.getDate("dtcadastro").toLocalDate();
				Pessoa p = new Pessoa(id,nome,dt);
				resultados.add(p);
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		return resultados;
	}

}
