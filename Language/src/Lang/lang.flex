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

// types
num = [0-9]+\.[0-9]+|[0-9]*
string = \"*\"
bool = true|false
literal = {num}|{string}|{bool}
//
id = [A-Za-z0-9_\-]+
operator = \+ | \- | \* | \/
rel_op = < | > | ==
arrow = ->
eq = =
type = num|string|bool
commands = print | display | read | make
// spaces
tab = \t
newline = \n
space = [ ]*


%%


// types of statements

let {
    parser.setState(State.var_def);
}

// literals

{literal} {
    parser.receive(yytext(), TokenType.literal);
}


//


{type} {
    parser.receive(yytext(), TokenType.type);
}


{id} {
    parser.receive(yytext(), TokenType.id);
}

{newline} {
    parser.receive(yytext(), TokenType.newline);
}

{space} {

}

{eq} {
    parser.receive(yytext(), TokenType.eq);
}

{arrow} {
    parser.receive(yytext(), TokenType.arrow);
}

// function call
{id}\( {
}
