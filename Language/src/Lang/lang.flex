package Lang;
import LangTools.*;


%%


%class Lexer
%standalone
%public

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
num = [0-9]+\.[0-9]+|{int}|{id}
int = [1-9]+[0-9]*|0
string = \"{all}\"
bool = true|false|{id}
literal = {num}|{string}|{bool}
// operators
operator = \+|\-|\*|\/
rel_op = <|>|==|<=|>=|\!=
bool_op = {rel_op}|&&|\|\||\!
// expressions
math_exp = ({num}{operator})*{num}|{num}
rel_exp = ({bool}{rel_op})*{bool}|{bool}
// stuff
keywords = if|then|for|from|to|while|do|let|func|num|bool|string|true|false|break
all = .*?
id = [A-Za-z]+[A-Za-z0-9_]*
condition = {value}{wse}{bool_op}{wse}{value}|{value}
arrow = ->
eq = =
type = num|string|bool
func_return = {type}|void
return_stmt = {wse}(return{ws}{value}|return)
print = {wse}print{ws}{value}
scope_ctrl = break|continue
comment = \/\/{all}({newline}|{wse})
value = {id}|{literal}|{func_call}|{math_exp}
inp_arg_val = {id}|{literal}|{math_exp}|{rel_exp}
lb = \{
rb = \}
lp = \(
rp = \)
// other = ^{var_def}
ignore = {ws}|{comment} // things to be matched but ignored
// spaces
tab = \t
newline = \n
ws = [ ]+ // whitespace
wse = [ ]* // whitespace (counting empty)
// variables
var_def = {wse}let{ws}{var_assign}{ws}{arrow}{ws}{type}
var_assign = {wse}{id}{ws}{eq}{ws}{value}
// control flow
if_stmt = {wse}if{ws}{condition}{ws}then
for_loop = {wse}for{ws}{id}{ws}from{ws}{int}{ws}to{ws}{int}{ws}do
while_loop = {wse}while{ws}{condition}{ws}do
// functions
func_def = {wse}func{ws}{id}:{ws}{args}{ws}{arrow}{ws}{func_return}
func_call = {wse}{id}\({input_args}\)
arg = {type}{ws}{value}
args = ({arg},{ws})*{arg}|{wse}
input_args = ({inp_arg_val},{ws})*{inp_arg_val}|{wse}
// top level
stmt = {var_assign}{var_def}{if_stmt}{for_loop}{while_loop}{func_def}
scope = {lb}{newline}{stmt}{newline}{rb}
// states
%state LOOP
%state FUNC


%%


<YYINITIAL> {

    {var_def} {
        parser.var_def(parser.split(yytext()));
    }

    {var_assign} {
        parser.var_assign(parser.split(yytext()));
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

    {func_call} {
        parser.func_call(yytext());
    }

    {return_stmt} {
        parser.return_stmt(parser.split(yytext()));
    }

    {scope_ctrl} {
        parser.scope_ctrl(parser.split(yytext()));
    }

    {print} {
        parser.print(parser.split(yytext()));
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

    {ignore} {}
}

<FUNC> {
    {lb} {
        parser.lb(ScopeType.func);
        yybegin(YYINITIAL);
    }

    {ignore} {}
}