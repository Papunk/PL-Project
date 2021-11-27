package Lang;
import java.io.*;
import LangTools.*;


%%

%class Lexer
%standalone

%{
    Parser parser;
%}

%init{
    parser = new Parser();
%init}

%eof{
    parser.end();
    System.out.println("\nLexer: End of execution");
%eof}


num = [0-9]+\.[0-9]+ | [0-9]*
commands = print | display | read | make
id = [A-Za-z0-9_\-]+
operator = \+ | \- | \* | \/
rel_op = < | > | ==
newline = \n
space = [ ]*


%state VAR_ASSIGN


%%

<YYINITIAL> {
    let {
        parser.setState(State.var_def);
    }

    if {

    }

    for {

    }

    while {

    }

    {id} {
        parser.receive(yytext(), TokenType.ID);
    }

    {newline} {
        parser.receive(yytext(), TokenType.newline);
    }

    {space} {
        
    }
}

// <VAR_ASSIGN> {

// }