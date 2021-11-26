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
id = [A-Za-z]+[A-Za-z | 0-9 | _ | \-]*
operator = \+ | \- | \* | \/
rel_op = < | > | ==
newline = \n


%state VAR_ASSIGN


%%

<YYINITIAL> {
    let {
        parser.setState(State.var_def);
        yystate();
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
        parser.receive(yytext(), TokenType.n);
    }
}

// <VAR_ASSIGN> {

// }