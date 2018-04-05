/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pasccompiler;

import java.util.HashMap;

/**
 *
 * @author Guilherme Passos
 */
public class TS {
    
    private HashMap<Token, Parametro> tabelaSimbolos; // Tabela de símbolos do ambiente

    public TS() {
        tabelaSimbolos = new HashMap();

        // Inserindo as palavras reservadas
        Token word;
        word = new Token(TagToken.KW, "program", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "if", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "else", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "while", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "write", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "read", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "num", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "char", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "not", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "or", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
        
        word = new Token(TagToken.KW, "and", 0, 0);
        this.tabelaSimbolos.put(word, new Parametro());
    }
    
    public void put(Token w, Parametro i) {
        tabelaSimbolos.put(w, i);
    }

    // Retorna um identificador de um determinado token
    public Parametro getIdentificador(Token w) {
        Parametro infoParametro = (Parametro) tabelaSimbolos.get(w);
        return infoParametro;
    }

    // Pesquisa na tabela de símbolos se há algum tokem com determinado lexema
    // vamos usar esse metodo somente para diferenciar ID e KW
    public Token retornaToken(String lexema) {
        for (Token token : tabelaSimbolos.keySet()) {
            if (token.getLexema().equals(lexema)) {
                return token;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        String saida = "";
        int i = 1;
        for (Token token : tabelaSimbolos.keySet()) {
            saida += ("posicao " + i + ": \t" + token.toString()) + "\n";
            i++;
        }
        return saida;
    }
}
