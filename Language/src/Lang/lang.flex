package Lang;
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
int = [1-9]+[0-9]*
string = \"{all}\"
bool = true|false
literal = {num}|{string}|{bool}
// stuff
all = .*?
id = [A-Za-z0-9_]+
operator = \+ | \- | \* | \/
rel_op = <|>|==|<=|>=|\!=
bool_op = {rel_op}|&&|\|\||\! // functions that return a boolean
condition = {value}{wse}{bool_op}{wse}{value}|{value} // TODO refine this
arrow = ->
eq = =
type = num|string|bool
commands = print | display | read | make
comment = \/\/{all}({newline}|{wse})
value = {id}|{literal} // TODO expand to include function calls
lb = \{
rb = \}
other = .*? // catch-all
ignore = {ws}|{comment} // things to be matched but ignored
// spaces
tab = \t
newline = \n
ws = [ ]+ // whitespace
wse = [ ]* // whitespace (counting empty)
// variables
var_def = {wse}let{ws}{var_assign}
var_assign = {wse}{id}{ws}{eq}{ws}{value}{ws}{arrow}{ws}{type}
// control flow
if_stmt = {wse}if{ws}{condition}{ws}then
for_loop = {wse}for{ws}{id}{ws}from{ws}{int}{ws}to{ws}{int}{ws}do
while_loop = {wse}while{ws}{condition}{ws}do
// functions
func_def = {wse}{id}{ws}\({wse}{args}{wse}\)
func_call = {wse}{id}\(\)
arg = {type}{ws}{value}
args = {args},|{arg} // check this one lol
// top level
stmt = {var_assign}{var_def}{if_stmt}{for_loop}{while_loop}{func_def}
scope = {lb}{newline}{stmt}{newline}{rb}



%%


<YYINITIAL> {

// IDEA: have different versions of {var_def} to identify defs with bools, ints, etc and then send that info to parser.var_def()
    {var_def} {
        parser.var_def(parser.split(yytext()));
    }

    {var_assign} {

    }

    {if_stmt} {
        parser.if_stmt(parser.split(yytext()));
    }

    {while_loop} {
        parser.while_loop(parser.split(yytext()));
    }

    {for_loop} {
        parser.for_loop(parser.split(yytext()));
    }


    // could be a function call or a variable assignment
    {id} {}


    {newline} {
        parser.newline();
    }

    {lb} {
        parser.lb();
    }

    {rb} {
        parser.rb();
    }

    {ignore} {}

    // error
    // {other} {
    //     parser.addError(ErrorType.SyntaxError, "Grammar does not match known pattern: " + yytext());
    // }
}

// <CONDITIONAL_BLOCK> {
//     {}
// }