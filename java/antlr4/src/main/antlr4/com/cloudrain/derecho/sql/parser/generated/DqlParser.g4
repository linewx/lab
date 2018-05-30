// (C) Copyright 2003-2010 Hewlett-Packard Development Company, L.P.
// DQLParser grammar

parser grammar DqlParser;

options {
    language = Java;
    tokenVocab=DqlLexer;
}

@header {
// (C) Copyright 2003-2010 Hewlett-Packard Development Company, L.P.
// Generated from DqlParser.g4 - DQL grammar


}

@members {

}

/*------------------------------------------------------------------
 * Basic select parser rules
 *------------------------------------------------------------------*/

// Root rules
dql
	: ( select ) EOF
	;

dqlExpression
	: expr EOF
	;

dqlCondition
	: condition EOF
	;

// Other rules

select
	: subquery orderByClause?
	;

subquery
	: subquery2 (setOperator subquery2)*
	;

setOperator
	: UNION ALL     #setOperation
	| UNION         #setOperation
	| INTERSECT     #setOperation
	| EXCEPT        #setOperation
	;

subquery2
	: nativeSql
	| selectClause fromClause whereClause? (timeSeriesClause | (groupByClause? havingClause? windowClause?))
	| LPAREN subquery RPAREN
	;

nativeSql
	: NATIVE nativeSqlIdList? NATIVE_SELECT
	;

nativeSqlIdList
	: LPAREN nativeSqlId (COMMA nativeSqlId)* RPAREN
	;

nativeSqlId
	: ID       // -> ^(NATIVE_REF $colname); - should be decided
	;
	
// Select clause
selectClause
	: SELECT DISTINCT? ( TOP NUMBER )? ( OFFSET NUMBER )? selectList    //-> ^(SELECT_NODE DISTINCT? (TOP NUMBER)? select_list)
	|
	;

selectList
	: selectItem ( COMMA selectItem )*
	;

selectItem
	: expr ( AS? alias )?  #asAlias //-> ^(SELECT_ITEM ^(EXPR expr) AS? ^(ALIAS alias)?)
	| ( alias DOT )? MULT   #asAlias //-> ^(SELECT_ITEM ( ^(ALIAS alias) DOT )? MULT)
	;
	
alias
	: ID
	| QUOTED_ID
	;    

// From clause
fromClause
	: FROM fromClauseList  //-> ^(FROM_NODE from_clause_list)
	;

fromClauseList
	: (objectOrSubqueryRef | nativeSql) fromClauseItem2*
	;

fromClauseItem2
	: ( COMMA (objectOrSubqueryRef | nativeSql))  #commaObjOrQuery
	| ( joinDirection? joinType? JOIN objectOrSubqueryRef joinCondition?)  #join //-> ^(JOIN ^(JOIN_DIRECTION join_direction)? ^(JOIN_TYPE join_type)? object_or_subquery_ref join_condition?)
	; 	

joinDirection
  : FULL
	| LEFT
	| RIGHT
	;
	
joinType
	: OUTER
	| INNER
	| CROSS
	;	

objectOrSubqueryRef
	: objectRef
	| subqueryRef
	;

objectRef
	: objectName AS? alias?    //-> ^(OBJECT_REF ^(OBJECT_NAME object_name) ^(ALIAS alias)?)
	;

subqueryRef
	: (LPAREN select RPAREN) AS? alias  //-> ^(SUBQUERY select ^(ALIAS alias))
	;
	
	
objectName
	: ID 
	| QUOTED_ID
	;

joinCondition
	: (ON condition)
	;

fieldRef
	: fieldRef2    //-> ^(FIELD_REF field_ref2)
	;
	
fieldRef2
	: (ID|QUOTED_ID) (DOT (ID|QUOTED_ID))*
	;

	
// Where, group by, having, order by clauses
whereClause
	: WHERE condition   //-> ^(WHERE_NODE condition)
	;
  
groupByClause
	: GROUP BY expressionList  //-> ^(GROUP_NODE expressionlist)
	;
	
havingClause
	: HAVING condition      //-> ^(HAVING_NODE condition)
	;
  
orderByClause
	: ORDER BY orderByList     //-> ^(ORDER_NODE order_by_list)
	;

orderByList
	: orderByItem (COMMA orderByItem)*
	;
  
orderByItem
	: expr orderByDirection?   //-> ^(ORDER_ITEM ^(ORDER_ITEM_COLUMN expr) ^(ORDER_ITEM_DIRECTION order_by_direction)? )   -should be decided
	;

orderByDirection
	: ASC | DESC
	;  

windowClause
    : WINDOW windowClauseItem (COMMA windowClauseItem)*
    ;

windowClauseItem
    : ID AS LPAREN windowDefinition RPAREN
    ;

timeSeriesClause
    : TIMESERIES timeSeriesAlias AS timeSeriesLengthTimeExpr OVER LPAREN partitionExpression? ORDER BY orderByItem RPAREN
    ;

timeSeriesAlias
    : ID
    ;

timeSeriesLengthTimeExpr
    : expr  // incorrect
    ;

//expression
expr
	: term ( ( PLUS | MINUS | CONCAT )  term )*
	;
	
term   
	: factor ( ( MULT | DIV | MOD ) factor )*
	;
	
factor 
	: (LPAREN select RPAREN) #factorSelectNode //-> ^(SELECT_NODE select)
	| (LPAREN expr RPAREN)   #factorParenExpr
	| plusMinusSign expr     #factorUnarySign  //-> ^(UNARY_SIGN plus_minus_sign expr)
	| caseExpression         #factorCaseExpr
	| NUMBER                 #factorNumber
	| STRING                 #factorString
	| NULL                   #factorNull
	| functionCall           #factorFuncCall
	| variableRef            #factorVarRef
	| fieldRef               #factorFieldRef
	| nativeSql              #factorNativeSQL
    | castExpression         #factorCastExpr
	;

plusMinusSign
	: PLUS 
	| MINUS
	;
		
caseExpression
	: CASE expr? cases END
	;

cases
	: caseExpressionItem+ caseExpressionElse?
	;

caseExpressionItem
	: WHEN (expr|condition) THEN expr
	;

caseExpressionElse
	: ELSE expr
	;	

functionCall
	: functionExpression windowFunctionExpression?
	;

functionExpression
	: ID LPAREN DISTINCT? (MULT? | expressionList?) RPAREN   //-> ^(FUNCTION_NODE ID DISTINCT? MULT? expressionlist?)
    ;

windowFunctionExpression
    : OVER ((LPAREN windowDefinition RPAREN)| windowName)
    ;

// *** Analytics Rules

windowDefinition
    : (windowName | partitionExpression)? orderByClause? frameClause?
    ;

windowName
    : ID
    ;

partitionExpression
    : PARTITION BY expressionList
    ;

frameClause
    : (rangeRows frameStart)      #frameStartOnly
    | (rangeRows BETWEEN frameStart AND frameEnd) #frameStartEnd
    ;

rangeRows
    : (ROWS | RANGE)
    ;

frameStart
    : (UNBOUNDED PRECEDING)
    | (NUMBER PRECEDING)
    | (CURRENT ROW)
    | (NUMBER FOLLOWING)
    ;

frameEnd
    : (NUMBER PRECEDING)
    | (CURRENT ROW)
    | (NUMBER FOLLOWING)
    | (UNBOUNDED FOLLOWING)
    ;

//conditions
condition
	: conditionAnd (OR conditionAnd)*
	;
	
conditionAnd
	: simpleCondition (AND simpleCondition)*
	;
	
simpleCondition
	: (LPAREN condition RPAREN)
	| (NOT simpleCondition)
	| existsCondition
	| likeCondition
	| nullCondition
	| inCondition
	| simpleComparisonCondition
	| betweenCondition
	| nativeSql  
	;

simpleComparisonCondition
	: expr comparisonOp expr
	;
	
comparisonOp
	: EQUALS | NOTEQUAL | LESS | GREATER | LESSOREQUALS | GREATEROREQUALS | OVERLAPS
	; 

likeCondition
	: e1=expr NOT? LIKE e2=expr (ESCAPE STRING)? //-> ^(LIKE NOT? $e1 $e2 ^(ESCAPE STRING)?)  - should be decided
	;
	
betweenCondition
    : e=expr NOT? BETWEEN bound1=expr AND bound2=expr // -> ^(BETWEEN NOT? $e $bound1 $bound2)  - should be decided
  ;

nullCondition
	: expr IS NOT? NULL  // -> ^(IS_NULL_EXPR expr NOT?)
	; 

inCondition
	: expr NOT? IN inConditionParam   // -> ^(IN_EXPR expr in_condition_param NOT?)
	;

inConditionParam
	: LPAREN (subquery | expressionList) RPAREN
	;

expressionList
	: expr (COMMA expr)*
	;
    	
existsCondition
	:  EXISTS LPAREN subquery RPAREN   //-> ^(EXISTS_EXPR subquery)
	;
  	
variableRef
	: VARIABLE | QUESTION_MARK;


castExpression
  : CAST LPAREN expr AS dataTypeExpression RPAREN    // -> ^(CAST_NODE expr data_type_expression)
  ;

dataTypeExpression
  : idList (LPAREN precision_length=NUMBER (COMMA scale=NUMBER)? RPAREN)?     //-> ^(DATA_TYPE_NODE id_list $precision_length? $scale?)
  ;
  
idList
  : ID (ID)*
  ;