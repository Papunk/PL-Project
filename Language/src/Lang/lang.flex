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

// TODO DEFINE EXPRESSIONS (BOTH MATHEMATICAL AND BOOLEAN =====================================
// types
num = [0-9]+\.[0-9]+|[0-9]*
int = [1-9]+[0-9]*
string = \"{all}\"
bool = true|false
literal = {num}|{string}|{bool}
// stuff
all = .*?
id = [A-Za-z]+[A-Za-z0-9_]*
operator = \+ | \- | \* | \/
rel_op = <|>|==|<=|>=|\!=
bool_op = {rel_op}|&&|\|\||\! // functions that return a boolean
condition = {value}{wse}{bool_op}{wse}{value}|{value} // TODO refine this
arrow = ->
eq = =
type = num|string|bool
func_return = {type}|void
return_stmt = {wse}(return{ws}{value}|return)
commands = print|display|read|make
scope_ctrl = break|continue
comment = \/\/{all}({newline}|{wse})
value = {id}|{literal} // TODO expand to include function calls
lb = \{
rb = \}
lp = \(
rp = \)
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
func_def = {wse}func{ws}{id}:{ws}{args}{ws}{arrow}{ws}{func_return}
func_call = {wse}{id}\(\)
arg = {type}{ws}{value}
args = ({arg},{wse})*{arg}|{wse}
// top level
stmt = {var_assign}{var_def}{if_stmt}{for_loop}{while_loop}{func_def}
scope = {lb}{newline}{stmt}{newline}{rb}

%state LOOP
%state FUNC


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
        yybegin(LOOP);
    }

    {for_loop} {
        parser.for_loop(parser.split(yytext()));
        yybegin(LOOP);
    }

    {func_def} {
        parser.func_def(parser.split(yytext()));
        yybegin(FUNC);
    }

    {return_stmt} {
        parser.return_stmt(parser.split(yytext()));
    }

    {scope_ctrl} {
        parser.scope_ctrl(parser.split(yytext()));
    }

    {newline} {
        parser.newline();
    }

    {lb} {
        parser.lb(ScopeType.main);
    }

    {rb} {
        parser.rb();
    }

    {ignore} {}

    // error | Problem: matches everything (longest match rule)
    // {other} {
    //     parser.addError(ErrorType.SyntaxError, "Grammar does not match known pattern: " + yytext());
    // }
}

<LOOP> {
    {lb} {
        parser.lb(ScopeType.loop);
        yybegin(YYINITIAL);
    }
}

<FUNC> {
    {lb} {
        parser.lb(ScopeType.func);
        yybegin(YYINITIAL);
    }
}