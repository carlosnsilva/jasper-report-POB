/**
 * IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 * @author Prof Fausto Ayres
 */


package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public abstract class DAO<T> implements DAOInterface<T>  {
	protected static Connection con;
	
	private final static boolean DROP = false;

	
	public static void open()  {
		abrirBancoPostgres();		//------------- POSTGRESQL
		//abrirBancoMysql();			//------------- MYSQL
	}

	public static void close() {
		if(con!=null){
			try {
				con.close();
			}catch(Exception e) {
				System.out.println("problema no fechamaneto da conexão:"+e.getMessage());
			}
		}
	}

	public abstract void create(T t) ;
	public abstract T read(Object chave) ;
	public abstract T update(T t) ;
	public abstract void delete(T t) ;
	public abstract ArrayList<T> readAll();

	public static void begin() throws Exception{
		con.setAutoCommit(false);
	}

	public static void commit() throws Exception{
		con.commit();
	}

	public static void rollback() throws Exception{
		con.rollback();
	}


	public static void abrirBancoPostgres()  {
		try {
			String url;
			PreparedStatement st;
			ResultSet rs;

			/*
			 * CRIAR / DROPAR DATABASE
			 */
			//verificar se database 'agenda' existe
			url = "jdbc:postgresql://localhost:5432/";
			con = DriverManager.getConnection(url,"carlos","carlos");	
			st = con.prepareStatement("select * from pg_database where datname='agenda'");
			rs = st.executeQuery();
			if (!rs.next()) {
				st = con.prepareStatement("CREATE database agenda");
				st.executeUpdate();
				st.close();
			}
			else
				if(DROP) {
					System.out.println("--DROP=true");
					//cancelar conexoes ativas
					st = con.prepareStatement("SELECT pg_terminate_backend (pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = 'agenda'  AND pg_stat_activity.pid <> pg_backend_pid();");
					st.executeQuery();
					st = con.prepareStatement("DROP database agenda");
					st.executeUpdate();
					st = con.prepareStatement("CREATE database agenda");
					st.executeUpdate();
					st.close();
				}
			con.close();
			
			/*
			 * CONECTAR DATABASE agenda
			 */
			url= "jdbc:postgresql://localhost:5432/IFPB";
			con = DriverManager.getConnection(url,"carlos","carlos");						
			con.setAutoCommit(false);
			//verificar sPreparedStatement ste tabela ja existe no banco, senao cria
			st = con.prepareStatement("select * from pg_tables where tableowner = 'postgres' and tablename= 'pessoa'");
			rs = st.executeQuery();
			if (rs.next()) 
				return;//sair

			//criar tabelas
			st = con.prepareStatement("create table Pessoa(id SERIAL, nome varchar(30), dtcadastro date, primary key (id) ); ");
			st.executeUpdate();
			st = con.prepareStatement("create table Telefone(id SERIAL, idpessoa integer, numero varchar(30),primary key (id), foreign key (idpessoa) references pessoa );");
			st.executeUpdate();
			con.commit();
			st.close();
			rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void abrirBancoMysql()  	{
		try {
			String url;
			PreparedStatement st;
			
			//apagar database 'agenda' 
			url = "jdbc:mysql://localhost:3306";
			con = DriverManager.getConnection(url,"root",""); 	
			if(DROP) {
				System.out.println("--DROP=true");
				st = con.prepareStatement("DROP database IF  EXISTS agenda");
				st.executeUpdate();
			}
			//criar database 'agenda' 
			st = con.prepareStatement("CREATE database IF NOT EXISTS agenda");
			st.executeUpdate();
			con.close();

			//conectar database 'agenda' (existente ou novo)
			url= "jdbc:mysql://localhost:3306/agenda";
			con = DriverManager.getConnection(url,"root",""); 	
			con.setAutoCommit(false);

			//verificar se tabela ja existe no banco, senao cria
			st = con.prepareStatement("create table IF NOT EXISTS Pessoa(id integer not null AUTO_INCREMENT, nome varchar(30), "+
					" dtcadastro date, primary key (id) ); ");
			st.executeUpdate();
			st = con.prepareStatement("create table IF NOT EXISTS Telefone(id integer not null AUTO_INCREMENT, idpessoa integer, numero varchar(30),primary key (id), foreign key (idpessoa) references pessoa(id) );");
			st.executeUpdate();
			con.commit();
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static Connection getConnection() throws Exception {
		return con;
	}

}

