package relatorios;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class RodarRelatorio {
	
	
	
	
	public void executar(String relatorio, String path, String nome) {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/aula?user=root&password=root");

			InputStream is = this.getClass().getResourceAsStream(relatorio);

			JasperDesign jd = JRXmlLoader.load(is);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			
			Map<String, Object> par = new HashMap<>();
			par.put("empresa", "Teste");
			par.put("cidade", "Londrina");
			
			JasperPrint jp = JasperFillManager.fillReport(jr, par, con);
			
			JasperExportManager.exportReportToPdfFile(jp, path+nome);
			
			// Cria um visualizador completo (app)
			//JasperViewer.viewReport(jp, false);
			
			// Cria um visializador que pode ser incluindo em um JPane (Swing)
			//JRViewer jrv = new JRViewer(jp);
									
			con.close();
						
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
