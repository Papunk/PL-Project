# PL-Project

### Synopsis

Our (presently unnamed) programming language sets out to provide powerful tools for data analysis and visualization at a high level of abstraction. The language will be implemented in Java and will thus have access to Java's robust support for creating and managing data structure while maintaining a simple syntax that draws inspiration from modern scripting languages.

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
var variableName value // variable declaration (type is inferred upon first assignment)
variableName = value // variable assignment

struct structName () // empty struct declaration (similar to a dictionary or class)
structName.propertyName // access stored properties for structs

array arrayName [] // array declaration
arrayName at Int // access array indices

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
