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
public enum Label {
     // fim de arquivo
    EOF,
    
    //Operadores
    OP_EQ,
    OP_NE,
    OP_GT,
    OP_LT,
    OP_GE,
    OP_LE,
    OP_AD,
    OP_MIN,
    OP_MULT,
    OP_DIV,
    OP_ASS,
    
    //Simbolos
    SMB_OBC,
    SMB_CBC,
    SMB_OPA,
    SMB_CPA,
    SMB_COM,
    SMB_SEM,
    
    // palavra reservada
    KW,
    
    //identificador
    ID,
    
    //Literal
    LIT,
    
   //Constantes
    COM_NUM,
    COM_CHAR;
    
}
