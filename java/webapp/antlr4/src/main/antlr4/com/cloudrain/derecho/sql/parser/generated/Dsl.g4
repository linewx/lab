grammar Dsl;

expression
    :     logicalExpression EOF;

logicalExpression
    :    NULL     #value
    |    BOOLEAN  #value
    |    STRING   #value
    |    DOUBLE  #value
    |    INTEGER #value
    |    SOURCE '://' logicalExpression '/' logicalExpression #identifier
    |    IDENT '(' ( logicalExpression (',' logicalExpression)* )? ')'     #function
    |    IDENT(dataSourceExpression)* #dataSource
    |    LPAREN logicalExpression RPAREN  #primaryExpression
    |    NOT logicalExpression   #unaryExpression
    |    logicalExpression ( MULT | DIV | MOD ) logicalExpression  #multiplicativeExpression
    |    logicalExpression  (PLUS | MINUS) logicalExpression   #additiveExpression
    |    logicalExpression (LT | LTEQ | GT | GTEQ) logicalExpression #relationalExpression
    |    logicalExpression   (EQUALS | NOTEQUALS) logicalExpression  #equalityExpression
    |    logicalExpression   AND logicalExpression   #booleanAndExpression
    |    logicalExpression   OR logicalExpression    #booleanOrExpression
    ;

dataSourceExpression : ('.'IDENT)(CONDITION)?('('  (logicalExpression (',' logicalExpression)*)? ')' )?;



OR    :     '||' | 'or';
AND   :     '&&' | 'and';
EQUALS
      :    '=' | '==';
NOTEQUALS
      :    '!=' | '<>';
LT    :    '<';
LTEQ  :    '<=';
GT    :    '>';
GTEQ  :    '>=';
PLUS  :    '+';
MINUS :    '-';
MULT  :    '*';
DIV   :    '/';
MOD   :    '%';
NOT   :    '!' | 'not';
DOT   :    '.';
RPAREN:    ')';
LPAREN:    '(';
SOURCE: 'ems'|'rms'|'external';

INTEGER
    :    '-'? ('0'..'9')+
    ;
DOUBLE
    :    '-'? ('0'..'9')+ '.' ('0'..'9')+
    ;

CONDITION :'[' .+ ']'
;

ESCAPED_QUOTE : '\\\''
;

STRING
     :'\''( ESCAPED_QUOTE | ~ '\''  )* '\''
     ;


BOOLEAN
     :    'true'
     |    'false'
     ;

NULL
     :    'null'
     ;

IDENT
     :    ('a'..'z' | 'A'..'Z' | '_' ) ('a'..'z' | 'A'..'Z' | '_' | '0'..'9'| '-')*
     ;


 WS : [ \t\n\r]+ -> skip ;