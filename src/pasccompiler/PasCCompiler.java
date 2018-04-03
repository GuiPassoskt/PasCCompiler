/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pasccompiler;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Guilherme Passos
 */
public class PasCCompiler {
    
    private static final int END_OF_FILE = -1; // contante para fim do arquivo
    private static int lookahead = 0; // armazena o último caractere lido do arquivo	
    public static int linha = 1; // contador de linhas
    public static int coluna = 1; // contador de linhas
    private RandomAccessFile arquivoReferencia; // referencia para o arquivo
    private static TS tabelaSimbolos; // tabela de simbolos
    
    public PasCCompiler(String input_data) {
		
        // Abre arquivoReferencia de input_data
	try {
            arquivoReferencia = new RandomAccessFile(input_data, "r");
	}
	catch(IOException e) {
            System.out.println("Erro de abertura do arquivo " + input_data + "\n" + e);
            System.exit(1);
	}
	catch(Exception e) {
            System.out.println("Erro do programa ou falha da tabela de simbolos\n" + e);
            System.exit(2);
	}
    }
    
    // Fecha arquivoReferencia de input_data
    public void fechaArquivo() {

        try {
            arquivoReferencia.close();
        }
	catch (IOException errorFile) {
            System.out.println ("Erro ao fechar arquivo\n" + errorFile);
            System.exit(3);
	}
    }
    
    //Reporta erro para o usuário
    public void sinalizaErro(String mensagem) {
        System.out.println("[Erro Lexico]: " + mensagem + "\n");
    }
    
    //Volta uma posição do buffer de leitura
    public void retornaPonteiro(){

        try {
            // Não é necessário retornar o ponteiro em caso de Fim de Arquivo
            if (lookahead != END_OF_FILE) {
                arquivoReferencia.seek(arquivoReferencia.getFilePointer() - 1);
            }    
        }
        catch(IOException e) {
            System.out.println("Falha ao retornar a leitura\n" + e);
            System.exit(4);
        }
    }
    

    // Obtém próximo token
    public Token proxToken() {

	StringBuilder lexema = new StringBuilder();
	int estado = 1;
	char c;
		
	while(true) {
            c = '\u0000'; // null char
            
            // avança caractere
            try {
                lookahead = arquivoReferencia.read(); 
		if(lookahead != END_OF_FILE) {
                    c = (char) lookahead;
                }
            }
            catch(IOException e) {
                System.out.println("Erro na leitura do arquivo");
                System.exit(3);
            }
            
            // movimentacao do automato
            switch(estado) {
                case 1:
                    if (lookahead == END_OF_FILE)
                        return new Token(Label.EOF, "EOF", linha, coluna);
                    else if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                        // Permance no estado = 1
                        if(c == '\n')  {
                            
                        }
                        else if(c == '\t') {
                           
                        }
                    }
                    else if (Character.isLetter(c)){
                        lexema.append(c);
                        estado = 14;
                    }
                    else if (Character.isDigit(c)) {
                        lexema.append(c);
                        estado = 12;
                    }
                    else if (c == '<') {
                        estado = 6;
                    }
                    else if (c == '>') {
                        estado = 9;
                    }
                    else if (c == '=') {
                        estado = 2;
                    }
                    else if (c == '!') {
                        estado = 4;
                    }
                    else if (c == '/') {
                        estado = 16;
                    }
                    else if (c == '*') {
                        estado = 18;
                        return new Token(Label.OP_MULT, "*", linha, coluna);
                    }
                    else if(c == '+') {
                        estado = 19;
                        return new Token(Label.OP_AD, "+", linha, coluna);
                    }
                    else if(c == '-') {
                        estado = 20;
                        return new Token(Label.OP_MIN, "-", linha, coluna);
                    }
                    else if(c == ';') {
                        estado = 21;
                        return new Token(Label.SMB_SEM, ";", linha, coluna);
                    }
                    else if(c == ',') {
                        estado = 25;
                        return new Token(Label.SMB_COM, ",", linha, coluna);
                    }
                    else if(c == '(') {
                        estado = 22;
                        return new Token(Label.SMB_OPA, "(", linha, coluna);
                    }
                    else if(c == ')') {
                        estado = 23;
                        return new Token(Label.SMB_CPA, ")", linha, coluna);
                    }
                    else if(c == '{') {
                        estado = 22;
                        return new Token(Label.SMB_OBC, "{", linha, coluna);
                    }
                    else if(c == '}') {
                        estado = 23;
                        return new Token(Label.SMB_CBC, "}", linha, coluna);
                    }
                    else if(c == '"') {
                        estado = 24;
                    }
                    else {
                        sinalizaErro("Caractere invalido " + c + " na linha " + linha + " e coluna " + coluna);
                        return null;
                    }
                    break;
                    
                case 2:
                    if (c == '=') { // Estado 3
                        estado = 3;
                        return new Token(Label.OP_EQ, "==", linha, coluna);
                    }
                    else {
                        retornaPonteiro();
                        return new Token(Label.OP_ASS, "=", linha, coluna);
                    }
                    
		case 4:
                    if (c == '=') { // Estado 5
                        estado = 5;
			return new Token(Label.OP_NE, "!=", linha, coluna);
                    }
                    else {
                        retornaPonteiro();
                        sinalizaErro("Token incompleto para o caractere ! na linha " + linha + " e coluna " + coluna);
			return null;
                    }
                    
                case 6:
                    if (c == '=') { // Estado 7
                        estado = 7;
			return new Token(Label.OP_LE, "<=", linha, coluna);
                    }
                    else { // Estado 8
                        estado = 8;
			retornaPonteiro();
			return new Token(Label.OP_LT, "<", linha, coluna);
                    }
                    
                case 9:
                    if (c == '=') { // Estado 10
                        estado = 10;
                        return new Token(Label.OP_GE, ">=", linha, coluna);
                    }
                    else { // Estado 11
                        estado = 11;
                        retornaPonteiro();
                        return new Token(Label.OP_GT, ">", linha, coluna);
                    }
                    
                case 12:
                    if (Character.isDigit(c)) {
                        lexema.append(c);
                        // Permanece no estado 12
                    }
                    else if (c == '.') {
                        lexema.append(c);
                        estado = 26;
                    }
                    else { // Estado 13
                        estado = 13;
                        retornaPonteiro();						
			return new Token(Label.COM_NUM, lexema.toString(), linha, coluna);
                    }
                    break;
                    
		case 14:
                    if (Character.isLetterOrDigit(c) || c == '_') {
                        lexema.append(c);
			// Permanece no estado 14
                    }
                    else { // Estado 15
                        estado = 15;
			retornaPonteiro();  
                        Token token = tabelaSimbolos.retornaToken(lexema.toString());
                        
                        if (token == null) {
                            return new Token(Label.ID, lexema.toString(), linha, coluna);
                        }
                        return token;
                    }
                    break;
                    
                case 16:
                    if (c == '/') {
                        estado = 17;
                    }
                    else {
                        retornaPonteiro();
			return new Token(Label.OP_DIV, lexema.toString(), linha, coluna);
                    }
                    break;
                    
                case 17:
                    if (c == '\n') {
                        
                    } 
                    // Se vier outro, permanece no estado 17
                    break;
                    
                case 24:
                    if (c == '"') {
                        estado = 25;
                        return new Token(Label.LIT, lexema.toString(), linha, coluna);
                    }
                    else if (lookahead == END_OF_FILE) {
                        sinalizaErro("String deve ser fechada com \" antes do fim de arquivo");
			return null;
                    }
                    else { // Se vier outro, permanece no estado 24
                        lexema.append(c);
                    }
                    break;
                    
                case 26:
                    if (Character.isDigit(c)) {
                        lexema.append(c);
                        estado = 27;
                    }
                    else {
                        sinalizaErro("Padrao para double invalido na linha " + linha + " coluna " + coluna);
			return null;
                    }
                    break;
                    
                case 27:
                    if (Character.isDigit(c)) {
                        lexema.append(c);
                    }
                    else {
                        retornaPonteiro();						
			return new Token(Label.COM_CHAR, lexema.toString(), linha, coluna);
                    }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PasCCompiler lexer = new PasCCompiler("programinha.pasc"); // parametro do Lexer: Um programa de acordo com a gramatica
	Token token;
        tabelaSimbolos = new TS();

	// Enquanto não houver erros ou não for fim de arquivo:
	do {
            token = lexer.proxToken();
            
            // Imprime token
	    if(token != null) {
                System.out.println("Token: " + token.toString() + "\t Linha: " + linha + "\t Coluna: " + coluna);
                
                // Verificar se existe o lexema na tabela de símbolos
                
            }
	     
	} while(token != null && token.getLabel() != Label.EOF);
	lexer.fechaArquivo();
        
    }
    
}
