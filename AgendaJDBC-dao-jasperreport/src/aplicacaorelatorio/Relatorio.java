
/**
 * IFPB - Curso SI - Disciplina de PERSISTENCIA DE OBJETOS
 *	
 * Aplicação JasperReport 
 */

package aplicacaorelatorio;
import fachada.Fachada;

public class Relatorio {
	public Relatorio() {
		Fachada.inicializar();		
		try {
			Fachada.gerarRelatorioJasper("jasperreport/Agenda.jrxml");
			Fachada.gerarRelatorioJasper("jasperreport/AgendaGrupo.jrxml");
			
			//usando o nome como parametro da consulta ao banco
			Fachada.gerarRelatorioJasperParametro("jasperreport/AgendaParametrizada.jrxml", "joao");
	
		} catch (Exception e) {
			System.out.println("-->"+e.getMessage());
		}		
		Fachada.finalizar();
	}

	
	//=================================================
		public static void main(String[] args) {
			new Relatorio();
		}

}
