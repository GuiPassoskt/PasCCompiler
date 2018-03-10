/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pasccompiler;

/**
 *
 * @author Guilherme Passos
 */
public class Token {
  
    private String lexema;
    private Label label;
    private int linha;
    private int coluna;

    public Token(Label label, String lexema, int linha, int coluna) {
        this.lexema = lexema;
        this.label = label;
        this.linha = linha;
        this.coluna = coluna;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    
    @Override
    public String toString() {
        return "<" + label + ", \"" + lexema + "\">";
    }
    
}
