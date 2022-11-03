/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaeecomerce.Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author 821223900
 */
public class Query {
    private void pvDeletarUsuario(String id) {
        //1: Definir o comando SQL
        String sql = "DELETE FROM user WHERE login = ?";
        //2: Abrir uma conexão
        MySQL factory = new MySQL();
        try (Connection c = factory.obtemConexao()){
            //3: Pré compila o comando
            PreparedStatement ps = c.prepareStatement(sql);
            //4: Preenche os dados faltantes
            ps.setString(1, id);
            //5: Executa o comando
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void DeletarAdmin(String id) {
        pvDeletarUsuario(id);
    }
    
    public void DeletarCliente(String id) {
        pvDeletarUsuario(id);
    }
    
    private void pvInserirAdmin (String login){
    //1: Definir o comando SQL
    String sql = "INSERT INTO admin(login) VALUES (?)";
    //2: Abrir uma conexão
    MySQL factory = new MySQL();
    try (Connection c = factory.obtemConexao()){
        //3: Pré compila o comando
        PreparedStatement ps = c.prepareStatement(sql);
        //4: Preenche os dados faltantes
        ps.setString(1, login);
        //5: Executa o comando
        ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void InserirAdmin (String login){
        pvInserirAdmin(login);
    }
    
    private boolean pvUsuarioExiste (String loginIns) {
        //1: Definir o comando SQL
        String sql = "SELECT EXISTS(SELECT * FROM biblioteca.user WHERE login = ?);";
        //2: Abrir uma conexão
        MySQL factory = new MySQL();
        try (Connection c = factory.obtemConexao()) {
            //3: Pré compila o comando
            PreparedStatement ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, loginIns);
            //4: Executa o comando e guarda
            //o resultado em um ResultSet
            ResultSet rs = ps.executeQuery();
            //5: itera sobre o resultado
            rs.next();
            return rs.getInt(1) == 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean UsuarioExiste (String login) {
        return pvUsuarioExiste(login);
    }
    
    private String[] pvLogar(String loginIns) {
        String[] resultado = new String[2];
        //1: Definir o comando SQL
        String sql = "SELECT login, password FROM biblioteca.user WHERE login = ?";
        //2: Abrir uma conexão
        MySQL factory = new MySQL();
        try (Connection c = factory.obtemConexao()) {
            //3: Pré compila o comando
            PreparedStatement ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, loginIns);
            //4: Executa o comando e guarda
            //o resultado em um ResultSet
            ResultSet rs = ps.executeQuery();
            //5: itera sobre o resultado
            
            rs.next();
            
            resultado[0] = rs.getString("login");
            resultado [1] = rs.getString("password");
            
            
            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] notFound = {"Nenhum usuário encontrado"};
        return notFound;
    }
    
    public String[] Logar(String loginIns) {
        return pvLogar(loginIns);
    }
    
    private String pvPegarIdLivro(String nome) {
        MySQL factory = new MySQL();
        String Id = "";
        //1: Definir o comando SQL
        String sql = "SELECT * FROM biblioteca.book WHERE name = ?";
        try (Connection c = factory.obtemConexao()) {
            //3: Pré compila o comando
            PreparedStatement ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, nome);
            //4: Executa o comando e guarda
            //o resultado em um ResultSet
            ResultSet rs = ps.executeQuery();
            //5: itera sobre o resultado
            rs.next();
            Id = Integer.toString(rs.getInt("id"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Id;
    }
    
    public String PegarIdLivro(String nome) {
        return pvPegarIdLivro(nome);
    }
    
    private ArrayList<ArrayList<String>> pvPesquisarLivros(String nome) {
        ArrayList<ArrayList<String>> arr = new ArrayList<>();
        MySQL factory = new MySQL();
          
        //1: Definir o comando SQL
        String sql = "SELECT * FROM biblioteca.book WHERE name LIKE ?";
        try (Connection c = factory.obtemConexao()) {
            //3: Pré compila o comando
            PreparedStatement ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, "%" + nome + "%");
            //4: Executa o comando e guarda
            //o resultado em um ResultSet
            ResultSet rs = ps.executeQuery();
            //5: itera sobre o resultado
            int i = 0;
            while(rs.next()) {
                arr.add(new ArrayList());
                arr.get(i).add(Integer.toString(rs.getInt("id")));
                arr.get(i).add(rs.getString("name"));
                arr.get(i).add(Integer.toString(rs.getInt("price")));
                arr.get(i).add(Integer.toString(rs.getInt("stock")));
                arr.get(i).add(rs.getString("publisher"));
                arr.get(i).add(rs.getString("author"));
                arr.get(i).add(rs.getString("genero"));
                i++;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
    
    public ArrayList<ArrayList<String>> PesquisarLivros(String nome) {
        return pvPesquisarLivros(nome);
    }
    
    private ArrayList<ArrayList<String>> pvMostrarLivros() {
        ArrayList<ArrayList<String>> arr = new ArrayList<>();
        MySQL factory = new MySQL();
          
        //1: Definir o comando SQL
        String sql = "SELECT * FROM biblioteca.book";
        try (Connection c = factory.obtemConexao()) {
            //3: Pré compila o comando
            PreparedStatement ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //4: Executa o comando e guarda
            //o resultado em um ResultSet
            ResultSet rs = ps.executeQuery();
            //5: itera sobre o resultado
            int i = 0;
            while(rs.next()) {
                arr.add(new ArrayList());
                arr.get(i).add(Integer.toString(rs.getInt("id")));
                arr.get(i).add(rs.getString("name"));
                arr.get(i).add(Double.toString(rs.getDouble("price")));
                arr.get(i).add(Integer.toString(rs.getInt("stock")));
                arr.get(i).add(rs.getString("publisher"));
                arr.get(i).add(rs.getString("author"));
                arr.get(i).add(rs.getString("genero"));
                i++;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(arr);
        return arr;
    }
    
    public ArrayList<ArrayList<String>> MostrarLivros() {
        return pvMostrarLivros();
    }
    
    private String pvInserirLivro(String nome, String autor, String editora, double preco, int unidade, String genero) {
        String sql = "INSERT INTO book(price, stock, name, publisher, author, genero) VALUES (?, ?, ?, ?, ?,?)";
            //2: Abrir uma conexão
            MySQL factory = new MySQL();
            try (Connection c = factory.obtemConexao()) {
                //3: Pré compila o comando
                PreparedStatement ps = c.prepareStatement(sql);
                //4: Preenche os dados faltantes
                ps.setDouble(1, preco);
                ps.setInt(2, unidade);
                ps.setString(3, nome);
                ps.setString(4, editora);
                ps.setString(5, autor);
                ps.setString(6, genero);
                //5: Executa o comando
                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            //1: Definir o comando SQL
        String sql2 = "SELECT id FROM biblioteca.book WHERE name = ? AND publisher = ? AND author = ?;";
        try (Connection c = factory.obtemConexao()) {
            //3: Pré compila o comando
            PreparedStatement ps = c.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, nome);
            ps.setString(2, editora);
            ps.setString(3, autor);
            //4: Executa o comando e guarda
            //o resultado em um ResultSet
            ResultSet rs = ps.executeQuery();
            //5: itera sobre o resultado
            rs.next();
            return Integer.toString(rs.getInt("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Erro";
    }
    
    public String InserirLivro(String nome, String autor, String editora, double preco, int unidade, String genero) {
        return pvInserirLivro(nome, autor, editora, preco, unidade, genero);
    }
    
    private void pvInserirUsuario(String login, String senha, String email, String nome, int idade) {
        //1: Definir o comando SQL
            String sql = "INSERT INTO user(login, password, email, nome, idade) VALUES (?, ?, ?, ?, ?)";
            //2: Abrir uma conexão
            MySQL factory = new MySQL();
            try (Connection c = factory.obtemConexao()) {
                //3: Pré compila o comando
                PreparedStatement ps = c.prepareStatement(sql);
                //4: Preenche os dados faltantes
                ps.setString(1, login);
                ps.setString(2, senha);
                ps.setString(3, email);
                ps.setString(4, nome);
                ps.setInt(5, idade);
                //5: Executa o comando
                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    public void InserirUsuario(String login, String senha, String email, String nome, int idade) {
        pvInserirUsuario(login, senha, email, nome, idade);
    }
    
    private boolean pvChecarAdmin(String loginIns) {
        //1: Definir o comando SQL
        String sql = "SELECT login FROM biblioteca.admin WHERE login = ?";
        //2: Abrir uma conexão
        MySQL factory = new MySQL();
        try (Connection c = factory.obtemConexao()) {
            //3: Pré compila o comando
            PreparedStatement ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, loginIns);
            //4: Executa o comando e guarda
            //o resultado em um ResultSet
            ResultSet rs = ps.executeQuery();
            //5: itera sobre o resultado
            if(!rs.next()) {
                return false;
            } else {
                return true;
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean ChecarAdmin(String loginIns) {
        return pvChecarAdmin(loginIns);
    }
}
