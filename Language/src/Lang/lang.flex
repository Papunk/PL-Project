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
%eof}


num = [0-9]+\.[0-9]+ | [0-9]*
commands = print | display | read | make
id = [A-Za-z]+[A-Za-z | 0-9 | _ | \-]*
operator = \+ | \- | \* | \/
rel_op = < | > | ==
newline = \n

%state var_def
%state exp
%state FUNCTION
%state IF_CLAUSE
%state CONDITION

%%

<YYINITIAL> {
    let {id} = * -> {
        parser.receive(yytext(), ParserMessage.var_def);
        // yybegin(ASSIGNMENT);
    }

    if {
        // parser.receive(yytext());
    }

    for {

    }

    while {

    }
}

// <ASSIGNMENT> {
//     // {id} {
//     //     out += help.tabulate(scopeLevel) + yytext();
//     // }

//     // \= {
//     //     out += "=";
//     // }

//     // {number} {
//     //     out += yytext();
//     //     h.pushState(yystate());
//     //     yybegin(EXPRESSION);
//     // }
// }

// // don't support identifiers just yet
// <EXPRESSION> {
//     // {operator} {
//     //     out += yytext();
//     // }

//     // {number} {
//     //     out += yytext();
//     // }

//     // {newline} {
//     //     int state = h.popState();
//     //     if (state == FUNCTION) {
//     //         out += ")\n";
//     //     }
//     //     else if (state == ASSIGNMENT) {
//     //         out += "\n";
//     //     }
//     //     yybegin(YYINITIAL);
//     // }
// }

// <FUNCTION> {
//     // {number} {
//     //     if (out.charAt(out.length() - 1) != '(') out += ",";
//     //     out += yytext();
//     //     h.pushState(yystate());
//     //     yybegin(EXPRESSION);
//     // }

//     // {id} {
//     //     if (out.charAt(out.length() - 1) != '(') out += ",";
//     //     out += yytext();
//     // }
// }

// <IF_CLAUSE> {
//     // {number} {
//     //     out += yytext();
//     //     h.pushState(yystate());
//     //     yybegin(CONDITION);
//     // }
// }

// <CONDITION> {
//     // {number} {
//     //     out += yytext();
//     // }

//     // {rel_op} {
//     //     out += yytext();
//     // }

//     // \{ {
//     //     out += ":\n";
//     //     scopeLevel++;
//     //     // if EmptyStackExc, uncomment this:
//     //     // h.pushState(yystate());
//     //     yybegin(YYINITIAL);
//     // }
// }