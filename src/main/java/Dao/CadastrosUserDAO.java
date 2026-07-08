package Dao;

import connection.ConnectionFactory;
import model.CadastroUsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import util.SenhaHash;

public class CadastrosUserDAO {
	private int idPerfil;
	
	public void verificarOuInserir(String userDadoTarget, String tabela_coluna, Connection con) throws SQLException {
	    // sei que tem que tomar cuidado com INJECTION SQL, mas o valor de fora não entra com o PreparedStatement
		   if (userDadoTarget == null || userDadoTarget.trim().isBlank()) {
		        return;
		    }

		    String sqlSelect = "SELECT 1 FROM " + tabela_coluna + " WHERE " + tabela_coluna + " = ?";
		    try (PreparedStatement stmtSelect = con.prepareStatement(sqlSelect)) {
		        stmtSelect.setString(1, userDadoTarget);
		        try (ResultSet rs = stmtSelect.executeQuery()) {
		            if (rs.next()) {
		                return;
		            }
		        }
		    }

		    String sqlInsert = "INSERT INTO " + tabela_coluna + " (" + tabela_coluna + ") VALUES (?)";
		    try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert)) {
		        stmtInsert.setString(1, userDadoTarget);
		        stmtInsert.executeUpdate();
		    }
	        
	}

	
	public void cadastrarPerfilUsuario(CadastroUsuarioModel user, Connection con, String[] listUser) throws SQLException, RuntimeException {		
		String sqlInsert = "INSERT INTO perfilUsuario (usuario, senha, funcao, grupoPermissao) VALUES"
				+ " (?,?,?,?)";
		try(PreparedStatement stmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);){
			
			String senhaHash = SenhaHash.gerarHash(user.getSenha()); 
			
			listUser = new String[] {user.getUsuario(), senhaHash, user.getFuncao(), user.getGrupoAcesso()};
			
			for(int i=0; i<listUser.length; i++) {
				if(listUser[i] == null || listUser[i].trim().isBlank()) {
					throw new RuntimeException("Dados obrigatorios.");
				}
				stmt.setString(i + 1, listUser[i]);
			}
			
			stmt.executeUpdate();
			
			try(ResultSet rs  = stmt.getGeneratedKeys();){
				rs.next();
				this.idPerfil = rs.getInt(1);
			}
		}
	}
	
	public void cadastrarCepUsuario(CadastroUsuarioModel user, Connection con, String[] listUser) throws SQLException {
		String sql = "INSERT INTO cepUsuario (idPerfil, cep, estado, cidade, logradouro, bairro, complemento, numero) VALUES"
				+ "(?,?,?,?,?,?,?,?)";
		try(PreparedStatement stmt = con.prepareStatement(sql);){
			
			listUser = new String[] {user.getCep(), user.getEstado(), user.getCidade(),
					user.getLogradouro(), user.getBairro(), user.getComplemento()};
			
			for(int i=0; i<listUser.length; i++) {
				if(listUser[i] == null || listUser[i].trim().isEmpty()) {
					stmt.setNull(i+2, java.sql.Types.VARCHAR);
				}else {
					stmt.setString(i+2, listUser[i]);
				}
			}
			
			if(user.getNumero() == null || user.getNumero().trim().isEmpty()) {
				stmt.setNull(8, java.sql.Types.INTEGER);
			}else {
				stmt.setInt(8, Integer.parseInt(user.getNumero()) );
				
			}
			
			stmt.setInt(1, this.idPerfil);
			
			stmt.executeUpdate();
		}
		
	}
	
	public void cadastrarDadosUsuario(CadastroUsuarioModel user, Connection con, String[] listUser) throws SQLException, RuntimeException {
		
		String sql = "INSERT INTO dadosUsuario (idPerfil, nome, sobrenome, cpf ,sexo, email, telefone, dtaNascimento) VALUES"
				+ "(?,?,?,?,?,?,?,?)";
		try(PreparedStatement stmt = con.prepareStatement(sql);){
			listUser = new String[]{user.getNome(), user.getSobrenome(), user.getCpf()};
			int sizeOld = listUser.length;
			for(int i=0; i<sizeOld; i++) {
				if(listUser[i] == null || listUser[i].trim().isEmpty()) {
					throw new RuntimeException("Dados Obrigatorio");
				}else {
					stmt.setString(i+2, listUser[i]);
				}
			}
			
			listUser = new String[] {user.getSexo(), user.getEmail(), user.getTelefone()};
			
			for(int i=0; i<listUser.length; i++) {
				if(listUser[i] == null || listUser[i].trim().isEmpty()) {
					stmt.setNull(i + sizeOld + 2, java.sql.Types.VARCHAR);
				}else {
					stmt.setString(i + sizeOld + 2, listUser[i]);
					
				}
			}
			
			if(user.getDtaNascimento() == null || user.getDtaNascimento().trim().isEmpty()) {
				stmt.setNull(8, java.sql.Types.DATE);
			}else {
				LocalDate userData = LocalDate.parse(user.getDtaNascimento());
				stmt.setDate(8, java.sql.Date.valueOf(userData) );
				
			}
		
		stmt.setInt(1, this.idPerfil);
		
		stmt.executeUpdate();
		}
			
	}
	
    public boolean cadastrar(CadastroUsuarioModel user){
    	Connection con =null;
        try{
        	con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);
            
            String[] listUser = new String[]{user.getGrupoAcesso(), "grupoPermissao",
            		user.getFuncao(), "funcao", 
            		user.getEstado(), "estado",
            		user.getCidade(), "cidade"};
            
            for(int i = 0; i<listUser.length; i += 2) {
            	verificarOuInserir(listUser[i], listUser[i+1], con);
            }
            
            
            cadastrarPerfilUsuario(user, con, listUser);
            cadastrarCepUsuario(user, con, listUser);
    		cadastrarDadosUsuario(user, con, listUser);
    		
    	     con.commit();
    	     con.setAutoCommit(true);
    		return true;
        } catch (Exception e){
        	if(con != null) {
        		try {
        			con.rollback();
        		}catch(SQLException ea){
        			ea.printStackTrace();
        		}
        	}
            e.printStackTrace();
            return false;
        }
    }
}
