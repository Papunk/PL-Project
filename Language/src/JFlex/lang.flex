import java.io.*;

%%

%class lexer
%standalone

%{
    String out;
    BufferedWriter outputFile;
    StateHandler h;
    int scopeLevel;
    Helper help;
    /// new
    Parser parser;
%}

%init{
    out = "";
    try {
        outputFile = new BufferedWriter(new FileWriter("outfilename"));
    }
    catch (Exception e) {}
    h = new StateHandler();
    help = new Helper();
    scopeLevel = 0; //TODO use this to add tabs

    /// new

    parser = new Parser();
%init}

%eof{
    try {
        outputFile.write(parser.out());
        outputFile.close();
    }
    catch (Exception e) {}
%eof}


number = [0-9]+
built_in_functions = print | factorial | exponent
id = [A-Za-z]+
operator = \+ | \- | \* | \/
rel_op = < | > | ==
newline = \n

%state ASSIGNMENT
%state EXPRESSION
%state FUNCTION
%state IF_CLAUSE
%state CONDITION

%%

<YYINITIAL> {
    var {
        yybegin(ASSIGNMENT);
    }

    {built_in_functions} {
        out += yytext() + "(";
        yybegin(FUNCTION);
    }

    if {
        out += help.tabulate(scopeLevel) + yytext() + " ";
        yybegin(IF_CLAUSE);
    }

    \} {
        scopeLevel--;
        yybegin(h.popState());
    }
}

<ASSIGNMENT> {
    {id} {
        out += help.tabulate(scopeLevel) + yytext();
    }

    \= {
        out += "=";
    }

    {number} {
        out += yytext();
        h.pushState(yystate());
        yybegin(EXPRESSION);
    }
}

// don't support identifiers just yet
<EXPRESSION> {
    {operator} {
        out += yytext();
    }

    {number} {
        out += yytext();
    }

    {newline} {
        int state = h.popState();
        if (state == FUNCTION) {
            out += ")\n";
        }
        else if (state == ASSIGNMENT) {
            out += "\n";
        }
        yybegin(YYINITIAL);
    }
}

<FUNCTION> {
    {number} {
        if (out.charAt(out.length() - 1) != '(') out += ",";
        out += yytext();
        h.pushState(yystate());
        yybegin(EXPRESSION);
    }

    {id} {
        if (out.charAt(out.length() - 1) != '(') out += ",";
        out += yytext();
    }
}

<IF_CLAUSE> {
    {number} {
        out += yytext();
        h.pushState(yystate());
        yybegin(CONDITION);
    }
}

<CONDITION> {
    {number} {
        out += yytext();
    }

    {rel_op} {
        out += yytext();
    }

    \{ {
        out += ":\n";
        scopeLevel++;
        // if EmptyStackExc, uncomment this:
        // h.pushState(yystate());
        yybegin(YYINITIAL);
    }
}