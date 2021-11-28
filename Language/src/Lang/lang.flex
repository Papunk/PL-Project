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
string = \".*?\"
bool = true|false
literal = {num}|{string}|{bool}
// stuff
id = [A-Za-z0-9_\-]+
operator = \+ | \- | \* | \/
rel_op = < | > | ==
arrow = ->
eq = =
type = num|string|bool
commands = print | display | read | make
comment = \/\/.*
value = {id}|{literal}
lb = \{
rb = \}
// keywords
if = if
for = for
while = while
// spaces
tab = \t
newline = \n
ws = [ ]+
wse = [ ]*
other = .*?
ignore = {ws}|{comment}
// terminals
var_def = let{ws}{var_assign}
var_assign = {id}{ws}{eq}{ws}{value}{ws}{arrow}{ws}{type}
func_call = {id}{ws}\({wse}{args}{wse}\)
arg = {type}{ws}{value}
args = {args},|{arg} // check this one lol


%%


<YYINITIAL> {

    {var_def} {
        parser.var_def(parser.split(yytext()));
    }

    // could be a function call or a variable assignment
    {id} {

    }


    {newline} {
        parser.newline();
    }

    {lb} {}
    {rb} {}

    // error
    {other} {
        parser.addError(ErrorType.SyntaxError, "Grammar does not match known pattern");
    }

    {ignore} {}
}