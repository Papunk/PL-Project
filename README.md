# PL-Project

### Synopsis

Our (presently unnamed) programming language sets out to provide powerful tools for data analysis and visualization at a high level of abstraction. The language will be implemented in Java and will thus have access to Java's robust support for creating and managing data structures while maintaining a simple syntax that draws inspiration from modern scripting languages. Users should not have to worry about the implementation details behind most constructs.

### Syntax Draft

#### Comments
```
// Single line comments

/* 
Multi-line comments  
*/
```

#### Variables and functions
```
make variableName value // variable declaration (type is inferred upon first assignment)
variableName = value // variable assignment
assign value to variableName // variable assignment

turn variableName to newType // type casting

dict dictName () // empty dictionary declaration
dictName at key // access the value that matces the key

array arrayName [] // array declaration
arrayName at Int // access array indices

struct structName {} // struct declaration
structName.propertyName // access stored property
structName.funcName // use internal function

function functionName(arg: Type) -> Type {} // function declaration
```

#### Special commands
```
read "fileName.extension" // returns the contents of a file
```

#### Control flow
```
if Bool then Statement // single line if statement

if Bool {} // if statement

while Bool {} // while loop

for variable in Collection {} // for each loop

for variable from Int to Int {} // indexed for loop

for variable from Int to Int do Statement // single line for loop
```

#### Literals
```
“Hello there!” // string literal

7 // number literal

True // boolean literal

[3 6 1 0 2 28] // array literal

(name: ”John” age: 45) // struct literal
```

#### Conditional Operators
```
is // comparison operator

or // or operator

and // and operator
```
