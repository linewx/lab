lexer grammar DqlLexer;

options {
    language=Java;
}

@lexer::header {

}

@lexer::members {

}

/* Keywords */

SELECT : ('S'|'s')('E'|'e')('L'|'l')('E'|'e')('C'|'c')('T'|'t') ;
FROM : ('F'|'f')('R'|'r')('O'|'o')('M'|'m') ;
JOIN : ('J'|'j')('O'|'o')('I'|'i')('N'|'n') ;
BY : ('B'|'b')('Y'|'y') ;
ON : ('O'|'o')('N'|'n') ;
IN : ('I'|'i')('N'|'n') ;
EXISTS : ('E'|'e')('X'|'x')('I'|'i')('S'|'s')('T'|'t')('S'|'s') ;
ALL : ('A'|'a')('L'|'l')('L'|'l') ;
WHERE : ('W'|'w')('H'|'h')('E'|'e')('R'|'r')('E'|'e') ;
GROUP : ('G'|'g')('R'|'r')('O'|'o')('U'|'u')('P'|'p') ;
ORDER : ('O'|'o')('R'|'r')('D'|'d')('E'|'e')('R'|'r') ;
ASC : ('A'|'a')('S'|'s')('C'|'c') ;
DESC : ('D'|'d')('E'|'e')('S'|'s')('C'|'c') ;
FULL : ('F'|'f')('U'|'u')('L'|'l')('L'|'l') ;
LEFT : ('L'|'l')('E'|'e')('F'|'f')('T'|'t') ;
RIGHT : ('R'|'r')('I'|'i')('G'|'g')('H'|'h')('T'|'t') ;
OUTER : ('O'|'o')('U'|'u')('T'|'t')('E'|'e')('R'|'r') ;
INNER : ('I'|'i')('N'|'n')('N'|'n')('E'|'e')('R'|'r') ;
CROSS : ('C'|'c')('R'|'r')('O'|'o')('S'|'s')('S'|'s') ;
DISTINCT : ('D'|'d')('I'|'i')('S'|'s')('T'|'t')('I'|'i')('N'|'n')('C'|'c')('T'|'t') ;
TOP : ('T'|'t')('O'|'o')('P'|'p') ;
OFFSET : ('O'|'o')('F'|'f')('F'|'f')('S'|'s')('E'|'e')('T'|'t') ;
AS : ('A'|'a')('S'|'s') ;
HAVING : ('H'|'h')('A'|'a')('V'|'v')('I'|'i')('N'|'n')('G'|'g') ;
IS : ('I'|'i')('S'|'s') ;
NULL : ('N'|'n')('U'|'u')('L'|'l')('L'|'l') ;
BETWEEN : ('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n') ;
NATIVE : ('N'|'n')('A'|'a')('T'|'t')('I'|'i')('V'|'v')('E'|'e') ;
UNION : ('U'|'u')('N'|'n')('I'|'i')('O'|'o')('N'|'n') ;
INTERSECT : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('S'|'s')('E'|'e')('C'|'c')('T'|'t') ;
EXCEPT 	: ('E'|'e')('X'|'x')('C'|'c')('E'|'e')('P'|'p')('T'|'t');
AND : ('A'|'a')('N'|'n')('D'|'d') ;
OR : ('O'|'o')('R'|'r') ;
NOT : ('N'|'n')('O'|'o')('T'|'t') ;
LIKE : ('L'|'l')('I'|'i')('K'|'k')('E'|'e') ;
ESCAPE 	: ('E'|'e')('S'|'s')('C'|'c')('A'|'a')('P'|'p')('E'|'e');
CASE 	: ('C'|'c')('A'|'a')('S'|'s')('E'|'e');
END 	: ('E'|'e')('N'|'n')('D'|'d');
WHEN	: ('W'|'w')('H'|'h')('E'|'e')('N'|'n');
THEN	: ('T'|'t')('H'|'h')('E'|'e')('N'|'n');
ELSE 	: ('E'|'e')('L'|'l')('S'|'s')('E'|'e');
CAST  : ('C'|'c')('A'|'a')('S'|'s')('T'|'t');
OVER    : ('O'|'o')('V'|'v')('E'|'e')('R'|'r');
PARTITION   : ('P'|'p')('A'|'a')('R'|'r')('T'|'t')('I'|'i')('T'|'t')('I'|'i')('O'|'o')('N'|'n');
WINDOW  : ('W'|'w')('I'|'i')('N'|'n')('D'|'d')('O'|'o')('W'|'w');
TIMESERIES  : ('T'|'t')('I'|'i')('M'|'m')('E'|'e')('S'|'s')('E'|'e')('R'|'r')('I'|'i')('E'|'e')('S'|'s');

/* Analytics Frame tokens */
ROWS        :   ('R'|'r')('O'|'o')('W'|'w')('S'|'s');
RANGE       :   ('R'|'r')('A'|'a')('N'|'n')('G'|'g')('E'|'e');
UNBOUNDED   :   ('U'|'u')('N'|'n')('B'|'b')('O'|'o')('U'|'u')('N'|'n')('D'|'d')('E'|'e')('D'|'d');
PRECEDING   :   ('P'|'p')('R'|'r')('E'|'e')('C'|'c')('E'|'e')('D'|'d')('I'|'i')('N'|'n')('G'|'g');
FOLLOWING   :   ('F'|'f')('O'|'o')('L'|'l')('L'|'l')('O'|'o')('W'|'w')('I'|'i')('N'|'n')('G'|'g');
CURRENT     :   ('C'|'c')('U'|'u')('R'|'r')('R'|'r')('E'|'e')('N'|'n')('T'|'t');
ROW         :   ('R'|'r')('O'|'o')('W'|'w');

/* Variable tokens (lexer configuration) */

NATIVE_SELECT
	: '{' (~'}')* '}'
	;

STRING
	: ('N'|'n')?  '\'' (~'\'')* '\'' ( '\'' (~'\'')* '\'' )*
	;

NUMBER
	: (N? DOT)? N DOT?
	| N DOT? N? ('E'|'e') MINUS? N
	;

VARIABLE
	: ':' ID
	;

WHITESPACE
	: ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+    -> channel(HIDDEN)
	;

fragment N
	: DIGIT+ ;

fragment DIGIT
	: '0'..'9' ;

fragment ASCII_CHAR
	: 'a'..'z' | 'A'..'Z' | '_' | DOLLAR;

fragment UNICODE_CHAR
  : '\u0024' /* Dolar Sign */
  | '\u0041'..'\u005a'
  | '\u005f' /* Lower line */
  | '\u0061'..'\u007a'
  | '\u00c0'..'\u00d6'
  | '\u00d8'..'\u00f6'
  | '\u00f8'..'\u00ff'
  | '\u0100'..'\u1fff'
  | '\u3040'..'\u318f'
  | '\u3300'..'\u337f'
  | '\u3400'..'\u3d2d'
  | '\u4e00'..'\u9fff'
  | '\uf900'..'\ufaff'
  ;

QUOTED_ID
	: '"' ~('"')* '"'
	;

ID
  /*: ASCII_CHAR (ASCII_CHAR | DIGIT)* */ /* ASCII RULE */
	: UNICODE_CHAR (UNICODE_CHAR | DIGIT)*
	;

SL_COMMENT
	: '--' ~('\n'|'\r')* ('\r'? '\n')? -> channel(HIDDEN)
	;

ML_COMMENT
	: '/*' .*? '*/' -> channel(HIDDEN)
	;

PLUS
    :  '+'
     ;
MINUS
    : '-'
    ;
MULT
    : '*'
    ;
EQUALS
    : '='
    ;
NOTEQUAL
    : '<>'
    ;
LESS
    : '<'
    ;
GREATER
    : '>'
    ;
LESSOREQUALS
    : '<='
    ;
GREATEROREQUALS
    : '>='
    ;
CONCAT
    : '||'
    ;
DIV
    : '/'
    ;
MOD
    : '%'
    ;
DOT
    : '.'
    ;
DOLLAR
    : '$'
    ;
COMMA
    : ','
    ;
COLON
    : ':'
    ;
SEMICOLON
    : ';'
    ;
QUOTE
    : '\''
    ;
RPAREN
    : ')'
    ;
LPAREN
    : '('
    ;
LCPAREN
    : '{'
    ;
RCPAREN
    : '}'
    ;
QUESTION_MARK
    : '?'
    ;
OVERLAPS
    : '&&'
    ;
