grammar GuiInput;
@header {package generated;}

compCon: '{' (MaxCon | MinCon | RegexCon | OptionalCon | HolderCon | SelectedCon | MajorTicksCon | MinorTicksCon | DisplayCon)* '}';
component: NameWord QuotedCharText CompId DefaultValue? compCon?;
group: QuotedCharText GroupId '{' component+ '}';
groupOrcomp: group | component;
tab: QuotedCharText TabId '{'groupOrcomp+ '}';
dialogCon: '{' (MaxCon | MinCon)* '}';
dialog: QuotedCharText DialogId DefaultValue dialogCon?;
query: dialog groupOrcomp+ EOF | dialog tab+ EOF;

fragment LowerCaseLetter: [a-z];
fragment UpperCaseLetter: [A-Z];
fragment Bool: 'true' | 'false';
fragment Digit: [0-9];
fragment Integer: '-'?Digit+;
fragment Decimal: Integer ('.'Digit+)?;
fragment MultiOpt: 'MultiOpt[' CharText ']';
fragment SingleOpt: 'SingleOpt[' CharText ']';
fragment Slider: 'Slider[' CharText ']';
fragment CharText: (LowerCaseLetter | UpperCaseLetter | Digit 
  | '<' | '>' | '&' | '|' | '*' | '+' | '-' | '=' | '!' | '%' | '[' | ']'
  | '?' | ';' | ':' | ',' | '.' | ' ' | '~' | '@' | '#' | '$' 
  | '`' | '^' | '_' | '"' | '\\' | '\n' )*;
Whitespace: [ \t\r\n]-> channel(HIDDEN);
DialogId: 'Single' | 'Multi' | 'Pages';
//work 'Working Field' MultiOpt('Computers|Building|Teaching'){selected='Building|Teaching'}
//to:
//work 'Working Field' MultiOpt[omputers|Building|Teaching]('Building|Teaching')
//MultiOpt: 'MultiOpt[' QuotedCharText ']';
GroupId: 'Group';
TabId: 'Tab';
CompId: 'String' | 'Integer' | 'Decimal' 
		| 'Boolean' | SingleOpt | MultiOpt 
		| 'Password' | Slider | 'TextArea';
//Displays: 'inline' | 'block' | 'inlineRadio' 
//		| 'inlineList' | 'blockRadio' | 'blockList' 
//		| 'inlineCheckbox' | 'blockCheckbox';
MinCon: 'min' Whitespace* '=' Whitespace* Decimal;
MaxCon: 'max' Whitespace* '=' Whitespace* Decimal;
RegexCon: 'regex' Whitespace* '=' Whitespace* QuotedCharText;
//RequiredCon: 'required' Whitespace* '=' Whitespace* Bool;
OptionalCon: Whitespace* 'optional' Whitespace*;
//DisplayCon: 'display' Whitespace* '=' Whitespace* Displays;
DisplayCon: 'inline' | 'block' | 'inlineList' | 'blockList' | 'blockRadio' | 'blockCheckbox';
HolderCon: 'placeholder' Whitespace* '=' Whitespace* QuotedCharText;
SelectedCon: 'selected' Whitespace* '=' Whitespace* QuotedCharText;
MajorTicksCon: 'majorTicks' Whitespace* '=' Whitespace* Integer;
MinorTicksCon: 'minorTicks' Whitespace* '=' Whitespace* Integer;


NameWord: LowerCaseLetter (LowerCaseLetter | UpperCaseLetter)*;
DefaultValue: '(' CharText ')';
QuotedCharText: '\'' CharText '\'';


//////////////////////////////////////////

//grammar GuiInput;
//@header {package generated;}
//
//component: NameWord QuotedCharText CompId DefaultValue? dialogCon?;
//dialogCon: '{' DialogCon '}';
//compCon: '{' CompCon '}';
//dialog: QuotedCharText DialogId DefaultValue dialogCon?;
//query: dialog component EOF;
//
//fragment LowerCaseLetter: [a-z];
//fragment UpperCaseLetter: [A-Z];
//fragment Bool: 'true' | 'false';
//fragment Digit: [0-9];
//fragment Integer: '-'?Digit+;
//fragment Decimal: Integer ('.'Digit+)?;
//fragment CharText: (LowerCaseLetter | UpperCaseLetter | Digit | '[' | ']' 
//  | '<' | '>' | '&' | '|' | '*' | '+' | '-' | '=' | '!' | '%' 
//  | '?' | ';' | ':' | ',' | '.' | ' ' | '~' | '@' | '#' | '$' 
//  | '`' | '^' | '_' | '"' | '\\' )*;
//MinCon: 'min' Whitespace* '=' Whitespace* Integer;
//MaxCon: 'max' Whitespace* '=' Whitespace* Integer;
//RegexCon: 'regex' Whitespace* '=' Whitespace* Digit;
//RequiredCon: 'required' Whitespace* '=' Whitespace* Bool;
//HolderCon: 'holder' Whitespace* '=' Whitespace* QuotedCharText;
//SelectedCon: 'selected' Whitespace* '=' Whitespace* QuotedCharText;
//MajorTicksCon: 'majorTicks' Whitespace* '=' Whitespace* Decimal;
//MinorTicksCon: 'minorTicks' Whitespace* '=' Whitespace* Decimal;
//StyleCon: 'style' Whitespace* '=' Whitespace* Styles;
//
//Whitespace: [ \t\r\n]-> channel(HIDDEN);
//DialogId: 'Single' | 'Multi' | 'Pages';
//CompId: 'String' | 'Integer' | 'Decimal' 
//		| 'Boolean' | 'SingleOpt' | 'MultiOpt' 
//		| 'Password' | 'Slider' | 'TextArea';
//Styles: 'inline' | 'block' | 'inlineRadio' 
//		| 'inlineList' | 'blockRadio' | 'blockList' 
//		| 'inlineCheckbox' | 'blockCheckbox';
//		
//DialogCon: '{' MaxCon? MinCon?'}';
//CompCon: '{' MinCon? MaxCon? RegexCon? RequiredCon? HolderCon? SelectedCon? MajorTicksCon? MinorTicksCon? StyleCon? '}';
//NameWord: LowerCaseLetter (LowerCaseLetter | UpperCaseLetter)*;
//DefaultValue: '(' QuotedCharText ')';
//QuotedCharText: '\'' CharText '\'';




//////////////////////////////////////////


//fragment SingleKw: 'Single';
//fragment MultiKw: 'Multi';
//fragment PagesKw: 'Pages';
//fragment NewLine: ('r'? 'n' | 'r')+;
//fragment Lowercase: 'a'..'z';
//fragment Uppercase: 'A'..'Z';
//fragment Minus: '-';
//fragment ImageExtension: '.JPEG' | '.PNG' | '.GIF' | '.BMP' | '.jpeg' | '.png' | '.gif' | '.bmp';
//fragment Digit: [0-9];
//fragment CharTitle: Lowercase | Uppercase | Digit | '[' | ']' 
//  | '<' | '>' | '&' | '|' | '*' | '+' | Minus | '=' | '!' | '%' 
//  | '?' | ';' | ':' | ',' | '.' | ' ' | '~' | '@' | '#' | '$' 
//  | '`' | '^' | '_' | '"' | '\\' ;
//fragment LegalIdChar: Lowercase | Uppercase | '_' | '$';
//
///*  ------
//	Parser
//    ------  */
//
//styleCon: Style '=' ('inline' | 'block' | 'inlineRadio' 
//			| 'inlineList' | 'blockRadio' | 'blockList' 
//			| 'inlineCheckbox' | 'blockCheckbox');
////holderCon: Holder '=' CharTitle*;
//majorTicksCon: MajorTicks '=' PositiveInt;
//minorTicksCon: MinorTicks '=' PositiveInt;
//requiredCon: Required '=' ('true' | 'false');
//minCon: Min '=' PositiveInt;
//maxCon: Max '=' PositiveInt;
//dialogCon: '{' minCon? maxCon? '}';
//dialog: QuoteTitle DialogType '(' Text ')' dialogCon?;
////dialog: QuoteTitle DialogType '(' QuoteTitle ')';
//compCon: '{' minCon?  maxCon? styleCon? majorTicksCon? minorTicksCon? requiredCon? '}';
//comp: CharId '\''CompTitle'\'' CompId CompDefVal? compCon?; 
//group: QuoteTitle Group '{' comp+ '}';
//tab: QuoteTitle Tab '{' (group+ comp* | group* comp+) '}';
//query: dialog comp+ EOF;
//
//
///*  -----
//	Lexer
//	-----  */
////WS : [ \t\r\n]+ -> skip ;
//Whitespace: (' ')-> channel(HIDDEN);
//DialogType: SingleKw | MultiKw | PagesKw ;
//CompId: 'String' | 'Integer' | 'Decimal' | 'Boolean' | 'SingleOpt' | 'MultiOpt' | 'Password' | 'Slider' | 'TextArea';
//Style: 'style';
////Holder: 'holder';
//MajorTicks: 'majorTicks';
//MinorTicks: 'minorTicks';
//Required: 'required';
//Min: 'min';
//Max: 'max';
//Tab: 'Tab';
//Group: 'Group';
//
//PositiveInt: Digit+;
//Number: Minus? Digit+ ('.' Digit+)? ;
//Integer: Minus? PositiveInt;
//CharId: Lowercase LegalIdChar* ;
//PathChars: (LegalIdChar | Minus | '..')+ ;
//CompTitle: Title ImagePath? Title? | Title? ImagePath Title? | Title? ImagePath? Title;
//ImagePath: 'IMG[' ('/')?(PathChars '/')* LegalIdChar ImageExtension ('/')? ']';
//Title: CharTitle+;
//QuoteTitle: '\''CharTitle+'\'';
//DialogTitle: '\''CharTitle+'\'';
//Text: '\'' (CharTitle | NewLine)+ '\'';
//
//// name 'Name: IMG[../folder/img.jpg]\n More text'     String      (some default text)     {min=3, max=15, required} 
////comp: CompName (Title | Icon | Title Icon | Icon Title) CompId compDefVal? compCon?; 
//
//CompDefVal: '(' QuoteTitle ')';

