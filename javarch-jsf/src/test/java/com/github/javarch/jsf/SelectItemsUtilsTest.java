package com.github.javarch.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import static junit.framework.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class SelectItemsUtilsTest {

	private SelectItemsUtils instance;
	
	@Before
	public void setUp(){
		instance = new SelectItemsUtils();
		
	}
	
	@Test
	public void testRecebendoParametrosNull() {
		 List<SelectItem> selectItens = instance.createSelectItems(null, null, null, null);
		 assertNotNull(selectItens);
		 assertTrue(selectItens.size() == 0);
	}
	
	@Test
	public void testRecebendoCamposNull() {
		 List<SelectItem> selectItens = instance.createSelectItems(new ArrayList<SelectItem>(), null, null, null);
		 assertNotNull(selectItens);
		 assertTrue(selectItens.size() == 0);
	}
	
	
	@Test
	public void testRecebendoCamposValueNullAndLabelNull() {
		 List<SelectItem> selectItens = instance.createSelectItems( getPessoas(10) , null, null);
		 assertNotNull(selectItens);
		 assertTrue(selectItens.size() == 10);
		 for ( int i = 0 ; i < selectItens.size() ; i++ ){
			 String value = (String)selectItens.get(i).getValue();
			 String label = selectItens.get(i).getLabel();
			 String desc = selectItens.get(i).getDescription();
			 assertEquals("???null???", value);
			 assertEquals("???null???", label);
			 assertEquals(null, desc);
		 }
	}
	
	
	@Test
	public void testRecebendoCamposValueInvalidoAndLabelNull() {
		 List<SelectItem> selectItens = instance.createSelectItems( getPessoas(10) , "nomeNaoExiste", null);
		 assertNotNull(selectItens);
		 assertTrue(selectItens.size() == 10);
		 for ( int i = 0 ; i < selectItens.size() ; i++ ){
			 String value = (String)selectItens.get(i).getValue();
			 String label = selectItens.get(i).getLabel();
			 String desc = selectItens.get(i).getDescription();
			 assertEquals("???nomeNaoExiste???", value);
			 assertEquals("???null???", label);
			 assertEquals(null, desc);
		 }
	}
	
	
	@Test
	public void testRecebendoCamposValueAndLabelInvalidos() {
		 List<SelectItem> selectItens = instance.createSelectItems( getPessoas(10) , "nomeNaoExiste", "emailNaoExiste");
		 assertNotNull(selectItens);
		 assertTrue(selectItens.size() == 10);
		 for ( int i = 0 ; i < selectItens.size() ; i++ ){
			 String value = (String)selectItens.get(i).getValue();
			 String label = selectItens.get(i).getLabel();
			 String desc = selectItens.get(i).getDescription();
			 assertEquals("???nomeNaoExiste???", value);
			 assertEquals("???emailNaoExiste???", label);
			 assertEquals(null, desc);
		 }
	}
	
	
	@Test
	public void testRecebendoCamposValueAndLabelValidos() {
		 List<SelectItem> selectItens = instance.createSelectItems( getPessoas(10) , "nome", "email");
		 assertNotNull(selectItens);
		 assertTrue(selectItens.size() == 10);
		 for ( int i = 0 ; i < selectItens.size() ; i++ ){
			 String value = (String)selectItens.get(i).getValue();
			 String label = selectItens.get(i).getLabel();
			 String desc = selectItens.get(i).getDescription();
			 assertEquals("nome" + i, value);
			 assertEquals("email" + i, label);
			 assertEquals(null, desc);
		 }
	}



	@Test
	public void testRecebendoCamposValueAndLabelValidosAndOptional() {
		 List<SelectItem> selectItens = instance.createSelectItems( getPessoas(10) , "nome", "email", "Selecione...");
		 assertNotNull(selectItens);
		 assertTrue(selectItens.size() == 11);
		 
		 assertEquals("Selecione...", selectItens.get(0).getLabel());
		 assertEquals( null, selectItens.get(0).getValue());
		 for ( int i = 1 ; i < selectItens.size() ; i++ ){
			 String value = (String)selectItens.get(i).getValue();
			 String label = selectItens.get(i).getLabel();
			 String desc = selectItens.get(i).getDescription();
			 assertEquals("nome" + (i - 1), value);
			 assertEquals("email" + (i - 1), label);
			 assertEquals(null, desc);
		 }
	}

	
	private List<Pessoa> getPessoas(int quantidade){
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		for ( int i = 0 ; i < quantidade ; i++ ){
			pessoas.add( new Pessoa ("nome" + i, "email" + i));
		}
		return pessoas;
	}
	
	class Pessoa {
		
		private String nome;
		private String email;
		
		public Pessoa(String nome, String email) {
			 this.nome = nome;
			 this.email=  email;
		}
		
		public String getNome() {
			return nome;
		}
		
		public void setNome(String nome) {
			this.nome = nome;
		}
		
		public String getEmail() {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		
		
	}
}
