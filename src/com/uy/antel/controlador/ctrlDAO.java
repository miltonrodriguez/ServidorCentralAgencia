package com.uy.antel.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.uy.antel.modelo.BReporteVentaMensualDiaria;
import com.uy.antel.modelo.BReporteVentaMensualFranja;

public class ctrlDAO {

	private static String LOOKUP_DATASOURSE_MySqlAGENCIA = "java:jboss/datasources/MySqlAGENCIA";
	private static ctrlDAO instance = null;

	public static ctrlDAO getInstance() {
		if (instance == null) {
			instance = new ctrlDAO();
		}
		return instance;
	}

	public static Connection getConexion() {
		Connection conn = null;
		try {
			InitialContext initContext;
			try {
				initContext = new InitialContext();
				DataSource ds = (DataSource) initContext.lookup(LOOKUP_DATASOURSE_MySqlAGENCIA);
				conn = ds.getConnection();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
		}
		return conn;

	}

	/**
	 * 
	 * @param nroTicket
	 * @param fechaVenta
	 * @param fechaIniE
	 * @param cantMinutos
	 * @param importeTotal
	 * @param matricula
	 * @param nroTerminal
	 * @throws Exception
	 */
	public void altaTicket(int nroTicket, Date fechaVenta, Date fechaIniE, int cantMinutos, int importeTotal,
			String matricula, int nroTerminal) throws Exception {

		Connection conn = getConexion();
		PreparedStatement ps_auto = conn.prepareStatement("select idAuto from auto where matricula=?");
		ps_auto.setString(1, matricula);
		ResultSet rs_auto = ps_auto.executeQuery();
		if (rs_auto.next()) {
			// Existe el auto
			conn.setAutoCommit(false);
			PreparedStatement ps_insert_ticket = conn.prepareStatement(
					"insert into ticket (nroTicket,fechaVenta,fechaIniE,cantMinutos,ImporteTotal,fk_auto,nroTerminal) values (?,?,?,?,?,?,?)");
			ps_insert_ticket.setInt(1, nroTicket);
			ps_insert_ticket.setTimestamp(2, new java.sql.Timestamp(fechaVenta.getTime()));
			ps_insert_ticket.setTimestamp(3, new java.sql.Timestamp(fechaIniE.getTime()));
			ps_insert_ticket.setInt(4, cantMinutos);
			ps_insert_ticket.setInt(5, importeTotal);
			ps_insert_ticket.setInt(6, rs_auto.getInt("idAuto"));
			ps_insert_ticket.setInt(7, nroTerminal);
			ps_insert_ticket.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			rs_auto.close();
			ps_auto.close();
			ps_insert_ticket.close();
			conn.close();
		} else {
			rs_auto.close();
			ps_auto.close();
			conn.close();
			// ERROR
			throw new Exception("NO existe un auto con esa matricula en el sistema");

		}

	}

	/**
	 * 
	 * @param matricula
	 * @throws Exception
	 */
	public void altaAuto(String matricula) throws Exception {

		Connection conn = getConexion();
		PreparedStatement ps_auto = conn.prepareStatement("select * from auto where matricula=?");
		ps_auto.setString(1, matricula);
		ResultSet rs_auto = ps_auto.executeQuery();
		if (rs_auto.next()) {
			// Existe el auto
			rs_auto.close();
			ps_auto.close();
			conn.close();
		} else {
			conn.setAutoCommit(false);
			PreparedStatement ps_secuencia = conn
					.prepareStatement("select secuencia from secuencias where nombre='auto'");
			ResultSet rs_secuencia = ps_secuencia.executeQuery();
			int secuencia;
			if (rs_secuencia.next()) {
				secuencia = rs_secuencia.getInt("secuencia");
				secuencia++;
			} else {
				throw new SQLException("No se pudo conseguir la secuencia");
			}
			PreparedStatement ps_update_secuencia = conn
					.prepareStatement("update secuencias set secuencia=? where nombre='auto'");
			ps_update_secuencia.setInt(1, secuencia);
			ps_update_secuencia.executeUpdate();
			PreparedStatement ps_insert_auto = conn
					.prepareStatement("insert into auto (matricula, idAuto) values (?,?)");
			ps_insert_auto.setString(1, matricula);
			ps_insert_auto.setInt(2, secuencia);
			ps_insert_auto.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			rs_secuencia.close();
			ps_secuencia.close();
			ps_update_secuencia.close();
			rs_auto.close();
			ps_auto.close();
			ps_insert_auto.close();
			conn.close();
		}
	}

	/**
	 * si nroterminal = -1 el login es de la web
	 * 
	 * @param usuario
	 * @param password
	 * @param nroTerminal
	 * @return
	 * @throws Exception
	 */
	public boolean login(String usuario, String password, int nroTerminal) throws Exception {

		Connection conn = getConexion();
		PreparedStatement ps_usuario = conn.prepareStatement("select * from usuario where usuario=? and password=?");
		ps_usuario.setString(1, usuario);
		ps_usuario.setString(2, password);
		ResultSet rs_usuario = ps_usuario.executeQuery();
		if (rs_usuario.next()) {
			// Existe el usuario

			PreparedStatement ps_rol_terminal = conn.prepareStatement(
					"select t.nroTerminal from (((rol_usuario ru join rol ro on ru.fk_rol = ro.rol) join rol_terminal rt on rt.fk_rol = ro.rol) join terminal t on rt.fk_terminal = t.nroTerminal) where ru.fk_usuario = ? and t.nroTerminal = ?");
			ps_rol_terminal.setInt(1, rs_usuario.getInt("id"));
			ps_rol_terminal.setInt(2, nroTerminal);
			ResultSet rs_rol_terminal = ps_rol_terminal.executeQuery();

			if (rs_rol_terminal.next()) {
				// El usuario se puede loguear en la terminal
				rs_usuario.close();
				ps_usuario.close();
				rs_rol_terminal.close();
				ps_rol_terminal.close();
				conn.close();
				return true;
			}
			// el usuario no se puede loguear en la terminal
			rs_rol_terminal.close();
			ps_rol_terminal.close();
			rs_usuario.close();
			ps_usuario.close();
			conn.close();
			if (nroTerminal != -1)
				throw new Exception("El usuario no se puede loguear en la terminal");
			else
				throw new Exception("El usuario no se puede loguear en la Web");
		}
		// El usuario no existe
		rs_usuario.close();
		ps_usuario.close();
		conn.close();
		throw new Exception("Usuario o password incorrecta");
	}

	/**
	 * 
	 * @param nroTicket
	 * @return
	 */
	public Pair<Integer, String> validarNroTicketAgencia(int nroTicket) {
		Pair<Integer, String> error;
		error = new ImmutablePair<Integer, String>(0, "");
		Connection conn = getConexion();
		PreparedStatement ps_ticket;
		try {
			ps_ticket = conn.prepareStatement("select * from ticket where nroTicket = ?");
			ps_ticket.setInt(1, nroTicket);
			ResultSet rs_ticket = ps_ticket.executeQuery();
			if (!rs_ticket.next())
				// El ticket fue vendido por la agencia
				error = new ImmutablePair<Integer, String>(102,
						"El numero de ticket no corresponde con un ticket vendido por la agencia.");
			else {
				if (rs_ticket.getTimestamp("fechaIniE").before(new Date()))
					error = new ImmutablePair<Integer, String>(103, "El ticket ya fue utilizado.");
				else {
					ps_ticket = conn
							.prepareStatement("select * from ticket where nroTicket = ? and fk_anulacion is null");
					ps_ticket.setInt(1, nroTicket);
					rs_ticket = ps_ticket.executeQuery();
					if (!rs_ticket.next())
						// El ticket fue vendido por la agencia
						error = new ImmutablePair<Integer, String>(104, "El ticket fue anulado previamente");
				}
			}
			rs_ticket.close();
			ps_ticket.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error = new ImmutablePair<Integer, String>(201, "Error del sistema");
		}
		return error;
	}

	/**
	 * 
	 * @param nroTicket
	 * @param codigo
	 * @param nroTerminal
	 * @param fecha
	 */
	public void altaCancelacion(int nroTicket, int codigo, int nroTerminal, Date fecha) {
		// TODO Auto-generated method stub
		Connection conn = getConexion();
		PreparedStatement ps_ticket;
		try {
			ps_ticket = conn.prepareStatement("select * from ticket where nroTicket = ?");
			ps_ticket.setInt(1, nroTicket);
			ResultSet rs_ticket = ps_ticket.executeQuery();
			if (rs_ticket.next()) {
				// El ticket fue vendido por la agencia y no esta anulado
				conn.setAutoCommit(false);
				PreparedStatement ps_insert_anulacion = conn
						.prepareStatement("insert into anulacion (codigo,fecha,nroTerminal) values (?,?,?)");
				ps_insert_anulacion.setInt(1, codigo);
				ps_insert_anulacion.setTimestamp(2,new java.sql.Timestamp(fecha.getTime()));
				ps_insert_anulacion.setInt(3, nroTerminal);
				ps_insert_anulacion.executeUpdate();
				rs_ticket.updateInt("fk_anulacion", codigo);
				conn.commit();
				conn.setAutoCommit(true);
				ps_insert_anulacion.close();
			}
			rs_ticket.close();
			ps_ticket.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<BReporteVentaMensualDiaria> getReporteVentaMensualDiaria(int mes, int anio){
		ArrayList<BReporteVentaMensualDiaria> reporteMensual = new ArrayList<BReporteVentaMensualDiaria>();
        try {
        	Connection conn = getConexion();        	
        	PreparedStatement ps_reporte = conn.prepareStatement("SELECT MONTH(t.fechaVenta) AS mes, DAY(t.fechaVenta) AS dia,SUM(t.importeTotal) as importeTotal, count(t.nroTicket) as cantTicket FROM ticket t where  MONTH(t.fechaVenta) = ? AND YEAR(t.fechaVenta)=? AND isnull(t.fk_anulacion) GROUP BY mes, dia ORDER BY dia asc");
        	ps_reporte.setInt(1, mes);
        	ps_reporte.setInt(2, anio);
        	
        	ResultSet rs_reporte = ps_reporte.executeQuery();             
            while (rs_reporte.next()) {            	
            	reporteMensual.add(new BReporteVentaMensualDiaria(rs_reporte.getInt("dia"),rs_reporte.getInt("importeTotal"),rs_reporte.getInt("cantTicket")));
            } 
            rs_reporte.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return reporteMensual;
	}
	
	public ArrayList<BReporteVentaMensualFranja> getReporteVentaMensualFranja(int mes, int anio){
		ArrayList<BReporteVentaMensualFranja> reporteMensual = new ArrayList<BReporteVentaMensualFranja>();
        try {
        	Connection conn = getConexion();        	
        	PreparedStatement ps_reporte = conn.prepareStatement("SELECT MONTH(t.fechaVenta) AS mes, HOUR(t.fechaVenta) AS hora,SUM(t.importeTotal) as importeTotal, count(t.nroTicket) as cantTicket FROM ticket t where  MONTH(t.fechaVenta) = ? AND YEAR(t.fechaVenta)=? AND isnull(t.fk_anulacion) GROUP BY mes, hora ORDER BY hora asc");
        	ps_reporte.setInt(1, mes);
        	ps_reporte.setInt(2, anio);
        	
        	ResultSet rs_reporte = ps_reporte.executeQuery();             
            while (rs_reporte.next()) {            	
            	reporteMensual.add(new BReporteVentaMensualFranja(rs_reporte.getInt("hora"),rs_reporte.getInt("importeTotal"),rs_reporte.getInt("cantTicket")));
            } 
            rs_reporte.close();                           
            conn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return reporteMensual;
	}

}
