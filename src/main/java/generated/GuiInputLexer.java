// Generated from src/main/resources/GuiInput.g4 by ANTLR 4.9.1
package generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GuiInputLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Whitespace=3, DialogId=4, GroupId=5, TabId=6, CompId=7, 
		MinCon=8, MaxCon=9, RegexCon=10, OptionalCon=11, DisplayCon=12, HolderCon=13, 
		SelectedCon=14, MajorTicksCon=15, MinorTicksCon=16, NameWord=17, DefaultValue=18, 
		QuotedCharText=19;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "LowerCaseLetter", "UpperCaseLetter", "Bool", "Digit", 
			"Integer", "Decimal", "CharText", "Whitespace", "DialogId", "GroupId", 
			"TabId", "CompId", "MinCon", "MaxCon", "RegexCon", "OptionalCon", "DisplayCon", 
			"HolderCon", "SelectedCon", "MajorTicksCon", "MinorTicksCon", "NameWord", 
			"DefaultValue", "QuotedCharText"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", null, null, "'Group'", "'Tab'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "Whitespace", "DialogId", "GroupId", "TabId", "CompId", 
			"MinCon", "MaxCon", "RegexCon", "OptionalCon", "DisplayCon", "HolderCon", 
			"SelectedCon", "MajorTicksCon", "MinorTicksCon", "NameWord", "DefaultValue", 
			"QuotedCharText"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GuiInputLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GuiInput.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\25\u01c3\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\5\6I\n\6\3\7\3\7\3\b\5\bN\n\b\3\b\6\bQ\n\b\r"+
		"\b\16\bR\3\t\3\t\3\t\6\tX\n\t\r\t\16\tY\5\t\\\n\t\3\n\3\n\3\n\3\n\7\n"+
		"b\n\n\f\n\16\ne\13\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f{\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5"+
		"\17\u00c9\n\17\3\20\3\20\3\20\3\20\3\20\7\20\u00d0\n\20\f\20\16\20\u00d3"+
		"\13\20\3\20\3\20\7\20\u00d7\n\20\f\20\16\20\u00da\13\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\21\7\21\u00e3\n\21\f\21\16\21\u00e6\13\21\3\21\3\21"+
		"\7\21\u00ea\n\21\f\21\16\21\u00ed\13\21\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\7\22\u00f8\n\22\f\22\16\22\u00fb\13\22\3\22\3\22\7\22"+
		"\u00ff\n\22\f\22\16\22\u0102\13\22\3\22\3\22\3\23\7\23\u0107\n\23\f\23"+
		"\16\23\u010a\13\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\7"+
		"\23\u0116\n\23\f\23\16\23\u0119\13\23\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\5\24\u0150\n\24\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\7\25\u015a\n\25\f\25\16\25\u015d\13\25\3\25\3\25\7\25\u0161"+
		"\n\25\f\25\16\25\u0164\13\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\3\26\7\26\u0172\n\26\f\26\16\26\u0175\13\26\3\26\3\26\7"+
		"\26\u0179\n\26\f\26\16\26\u017c\13\26\3\26\3\26\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u018c\n\27\f\27\16\27\u018f"+
		"\13\27\3\27\3\27\7\27\u0193\n\27\f\27\16\27\u0196\13\27\3\27\3\27\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30\u01a6\n\30"+
		"\f\30\16\30\u01a9\13\30\3\30\3\30\7\30\u01ad\n\30\f\30\16\30\u01b0\13"+
		"\30\3\30\3\30\3\31\3\31\3\31\7\31\u01b7\n\31\f\31\16\31\u01ba\13\31\3"+
		"\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\2\2\34\3\3\5\4\7\2\t\2\13\2\r\2"+
		"\17\2\21\2\23\2\25\5\27\6\31\7\33\b\35\t\37\n!\13#\f%\r\'\16)\17+\20-"+
		"\21/\22\61\23\63\24\65\25\3\2\7\3\2c|\3\2C\\\3\2\62;\t\2\f\f\"(,\60<B"+
		"]b~~\u0080\u0080\5\2\13\f\17\17\"\"\2\u01e5\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2"+
		"\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\3\67"+
		"\3\2\2\2\59\3\2\2\2\7;\3\2\2\2\t=\3\2\2\2\13H\3\2\2\2\rJ\3\2\2\2\17M\3"+
		"\2\2\2\21T\3\2\2\2\23c\3\2\2\2\25f\3\2\2\2\27z\3\2\2\2\31|\3\2\2\2\33"+
		"\u0082\3\2\2\2\35\u00c8\3\2\2\2\37\u00ca\3\2\2\2!\u00dd\3\2\2\2#\u00f0"+
		"\3\2\2\2%\u0108\3\2\2\2\'\u014f\3\2\2\2)\u0151\3\2\2\2+\u0167\3\2\2\2"+
		"-\u017f\3\2\2\2/\u0199\3\2\2\2\61\u01b3\3\2\2\2\63\u01bb\3\2\2\2\65\u01bf"+
		"\3\2\2\2\678\7}\2\28\4\3\2\2\29:\7\177\2\2:\6\3\2\2\2;<\t\2\2\2<\b\3\2"+
		"\2\2=>\t\3\2\2>\n\3\2\2\2?@\7v\2\2@A\7t\2\2AB\7w\2\2BI\7g\2\2CD\7h\2\2"+
		"DE\7c\2\2EF\7n\2\2FG\7u\2\2GI\7g\2\2H?\3\2\2\2HC\3\2\2\2I\f\3\2\2\2JK"+
		"\t\4\2\2K\16\3\2\2\2LN\7/\2\2ML\3\2\2\2MN\3\2\2\2NP\3\2\2\2OQ\5\r\7\2"+
		"PO\3\2\2\2QR\3\2\2\2RP\3\2\2\2RS\3\2\2\2S\20\3\2\2\2T[\5\17\b\2UW\7\60"+
		"\2\2VX\5\r\7\2WV\3\2\2\2XY\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z\\\3\2\2\2[U\3"+
		"\2\2\2[\\\3\2\2\2\\\22\3\2\2\2]b\5\7\4\2^b\5\t\5\2_b\5\r\7\2`b\t\5\2\2"+
		"a]\3\2\2\2a^\3\2\2\2a_\3\2\2\2a`\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2"+
		"d\24\3\2\2\2ec\3\2\2\2fg\t\6\2\2gh\3\2\2\2hi\b\13\2\2i\26\3\2\2\2jk\7"+
		"U\2\2kl\7k\2\2lm\7p\2\2mn\7i\2\2no\7n\2\2o{\7g\2\2pq\7O\2\2qr\7w\2\2r"+
		"s\7n\2\2st\7v\2\2t{\7k\2\2uv\7R\2\2vw\7c\2\2wx\7i\2\2xy\7g\2\2y{\7u\2"+
		"\2zj\3\2\2\2zp\3\2\2\2zu\3\2\2\2{\30\3\2\2\2|}\7I\2\2}~\7t\2\2~\177\7"+
		"q\2\2\177\u0080\7w\2\2\u0080\u0081\7r\2\2\u0081\32\3\2\2\2\u0082\u0083"+
		"\7V\2\2\u0083\u0084\7c\2\2\u0084\u0085\7d\2\2\u0085\34\3\2\2\2\u0086\u0087"+
		"\7U\2\2\u0087\u0088\7v\2\2\u0088\u0089\7t\2\2\u0089\u008a\7k\2\2\u008a"+
		"\u008b\7p\2\2\u008b\u00c9\7i\2\2\u008c\u008d\7K\2\2\u008d\u008e\7p\2\2"+
		"\u008e\u008f\7v\2\2\u008f\u0090\7g\2\2\u0090\u0091\7i\2\2\u0091\u0092"+
		"\7g\2\2\u0092\u00c9\7t\2\2\u0093\u0094\7F\2\2\u0094\u0095\7g\2\2\u0095"+
		"\u0096\7e\2\2\u0096\u0097\7k\2\2\u0097\u0098\7o\2\2\u0098\u0099\7c\2\2"+
		"\u0099\u00c9\7n\2\2\u009a\u009b\7D\2\2\u009b\u009c\7q\2\2\u009c\u009d"+
		"\7q\2\2\u009d\u009e\7n\2\2\u009e\u009f\7g\2\2\u009f\u00a0\7c\2\2\u00a0"+
		"\u00c9\7p\2\2\u00a1\u00a2\7U\2\2\u00a2\u00a3\7k\2\2\u00a3\u00a4\7p\2\2"+
		"\u00a4\u00a5\7i\2\2\u00a5\u00a6\7n\2\2\u00a6\u00a7\7g\2\2\u00a7\u00a8"+
		"\7Q\2\2\u00a8\u00a9\7r\2\2\u00a9\u00c9\7v\2\2\u00aa\u00ab\7O\2\2\u00ab"+
		"\u00ac\7w\2\2\u00ac\u00ad\7n\2\2\u00ad\u00ae\7v\2\2\u00ae\u00af\7k\2\2"+
		"\u00af\u00b0\7Q\2\2\u00b0\u00b1\7r\2\2\u00b1\u00c9\7v\2\2\u00b2\u00b3"+
		"\7R\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7u\2\2\u00b5\u00b6\7u\2\2\u00b6"+
		"\u00b7\7y\2\2\u00b7\u00b8\7q\2\2\u00b8\u00b9\7t\2\2\u00b9\u00c9\7f\2\2"+
		"\u00ba\u00bb\7U\2\2\u00bb\u00bc\7n\2\2\u00bc\u00bd\7k\2\2\u00bd\u00be"+
		"\7f\2\2\u00be\u00bf\7g\2\2\u00bf\u00c9\7t\2\2\u00c0\u00c1\7V\2\2\u00c1"+
		"\u00c2\7g\2\2\u00c2\u00c3\7z\2\2\u00c3\u00c4\7v\2\2\u00c4\u00c5\7C\2\2"+
		"\u00c5\u00c6\7t\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c9\7c\2\2\u00c8\u0086"+
		"\3\2\2\2\u00c8\u008c\3\2\2\2\u00c8\u0093\3\2\2\2\u00c8\u009a\3\2\2\2\u00c8"+
		"\u00a1\3\2\2\2\u00c8\u00aa\3\2\2\2\u00c8\u00b2\3\2\2\2\u00c8\u00ba\3\2"+
		"\2\2\u00c8\u00c0\3\2\2\2\u00c9\36\3\2\2\2\u00ca\u00cb\7o\2\2\u00cb\u00cc"+
		"\7k\2\2\u00cc\u00cd\7p\2\2\u00cd\u00d1\3\2\2\2\u00ce\u00d0\5\25\13\2\u00cf"+
		"\u00ce\3\2\2\2\u00d0\u00d3\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d2\3\2"+
		"\2\2\u00d2\u00d4\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d4\u00d8\7?\2\2\u00d5"+
		"\u00d7\5\25\13\2\u00d6\u00d5\3\2\2\2\u00d7\u00da\3\2\2\2\u00d8\u00d6\3"+
		"\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00db\3\2\2\2\u00da\u00d8\3\2\2\2\u00db"+
		"\u00dc\5\21\t\2\u00dc \3\2\2\2\u00dd\u00de\7o\2\2\u00de\u00df\7c\2\2\u00df"+
		"\u00e0\7z\2\2\u00e0\u00e4\3\2\2\2\u00e1\u00e3\5\25\13\2\u00e2\u00e1\3"+
		"\2\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5"+
		"\u00e7\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00eb\7?\2\2\u00e8\u00ea\5\25"+
		"\13\2\u00e9\u00e8\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb"+
		"\u00ec\3\2\2\2\u00ec\u00ee\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00ef\5\21"+
		"\t\2\u00ef\"\3\2\2\2\u00f0\u00f1\7t\2\2\u00f1\u00f2\7g\2\2\u00f2\u00f3"+
		"\7i\2\2\u00f3\u00f4\7g\2\2\u00f4\u00f5\7z\2\2\u00f5\u00f9\3\2\2\2\u00f6"+
		"\u00f8\5\25\13\2\u00f7\u00f6\3\2\2\2\u00f8\u00fb\3\2\2\2\u00f9\u00f7\3"+
		"\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fc\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fc"+
		"\u0100\7?\2\2\u00fd\u00ff\5\25\13\2\u00fe\u00fd\3\2\2\2\u00ff\u0102\3"+
		"\2\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u0103\3\2\2\2\u0102"+
		"\u0100\3\2\2\2\u0103\u0104\5\65\33\2\u0104$\3\2\2\2\u0105\u0107\5\25\13"+
		"\2\u0106\u0105\3\2\2\2\u0107\u010a\3\2\2\2\u0108\u0106\3\2\2\2\u0108\u0109"+
		"\3\2\2\2\u0109\u010b\3\2\2\2\u010a\u0108\3\2\2\2\u010b\u010c\7q\2\2\u010c"+
		"\u010d\7r\2\2\u010d\u010e\7v\2\2\u010e\u010f\7k\2\2\u010f\u0110\7q\2\2"+
		"\u0110\u0111\7p\2\2\u0111\u0112\7c\2\2\u0112\u0113\7n\2\2\u0113\u0117"+
		"\3\2\2\2\u0114\u0116\5\25\13\2\u0115\u0114\3\2\2\2\u0116\u0119\3\2\2\2"+
		"\u0117\u0115\3\2\2\2\u0117\u0118\3\2\2\2\u0118&\3\2\2\2\u0119\u0117\3"+
		"\2\2\2\u011a\u011b\7k\2\2\u011b\u011c\7p\2\2\u011c\u011d\7n\2\2\u011d"+
		"\u011e\7k\2\2\u011e\u011f\7p\2\2\u011f\u0150\7g\2\2\u0120\u0121\7d\2\2"+
		"\u0121\u0122\7n\2\2\u0122\u0123\7q\2\2\u0123\u0124\7e\2\2\u0124\u0150"+
		"\7m\2\2\u0125\u0126\7k\2\2\u0126\u0127\7p\2\2\u0127\u0128\7n\2\2\u0128"+
		"\u0129\7k\2\2\u0129\u012a\7p\2\2\u012a\u012b\7g\2\2\u012b\u012c\7N\2\2"+
		"\u012c\u012d\7k\2\2\u012d\u012e\7u\2\2\u012e\u0150\7v\2\2\u012f\u0130"+
		"\7d\2\2\u0130\u0131\7n\2\2\u0131\u0132\7q\2\2\u0132\u0133\7e\2\2\u0133"+
		"\u0134\7m\2\2\u0134\u0135\7N\2\2\u0135\u0136\7k\2\2\u0136\u0137\7u\2\2"+
		"\u0137\u0150\7v\2\2\u0138\u0139\7d\2\2\u0139\u013a\7n\2\2\u013a\u013b"+
		"\7q\2\2\u013b\u013c\7e\2\2\u013c\u013d\7m\2\2\u013d\u013e\7T\2\2\u013e"+
		"\u013f\7c\2\2\u013f\u0140\7f\2\2\u0140\u0141\7k\2\2\u0141\u0150\7q\2\2"+
		"\u0142\u0143\7d\2\2\u0143\u0144\7n\2\2\u0144\u0145\7q\2\2\u0145\u0146"+
		"\7e\2\2\u0146\u0147\7m\2\2\u0147\u0148\7E\2\2\u0148\u0149\7j\2\2\u0149"+
		"\u014a\7g\2\2\u014a\u014b\7e\2\2\u014b\u014c\7m\2\2\u014c\u014d\7d\2\2"+
		"\u014d\u014e\7q\2\2\u014e\u0150\7z\2\2\u014f\u011a\3\2\2\2\u014f\u0120"+
		"\3\2\2\2\u014f\u0125\3\2\2\2\u014f\u012f\3\2\2\2\u014f\u0138\3\2\2\2\u014f"+
		"\u0142\3\2\2\2\u0150(\3\2\2\2\u0151\u0152\7j\2\2\u0152\u0153\7q\2\2\u0153"+
		"\u0154\7n\2\2\u0154\u0155\7f\2\2\u0155\u0156\7g\2\2\u0156\u0157\7t\2\2"+
		"\u0157\u015b\3\2\2\2\u0158\u015a\5\25\13\2\u0159\u0158\3\2\2\2\u015a\u015d"+
		"\3\2\2\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\u015e\3\2\2\2\u015d"+
		"\u015b\3\2\2\2\u015e\u0162\7?\2\2\u015f\u0161\5\25\13\2\u0160\u015f\3"+
		"\2\2\2\u0161\u0164\3\2\2\2\u0162\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163"+
		"\u0165\3\2\2\2\u0164\u0162\3\2\2\2\u0165\u0166\5\65\33\2\u0166*\3\2\2"+
		"\2\u0167\u0168\7u\2\2\u0168\u0169\7g\2\2\u0169\u016a\7n\2\2\u016a\u016b"+
		"\7g\2\2\u016b\u016c\7e\2\2\u016c\u016d\7v\2\2\u016d\u016e\7g\2\2\u016e"+
		"\u016f\7f\2\2\u016f\u0173\3\2\2\2\u0170\u0172\5\25\13\2\u0171\u0170\3"+
		"\2\2\2\u0172\u0175\3\2\2\2\u0173\u0171\3\2\2\2\u0173\u0174\3\2\2\2\u0174"+
		"\u0176\3\2\2\2\u0175\u0173\3\2\2\2\u0176\u017a\7?\2\2\u0177\u0179\5\25"+
		"\13\2\u0178\u0177\3\2\2\2\u0179\u017c\3\2\2\2\u017a\u0178\3\2\2\2\u017a"+
		"\u017b\3\2\2\2\u017b\u017d\3\2\2\2\u017c\u017a\3\2\2\2\u017d\u017e\5\65"+
		"\33\2\u017e,\3\2\2\2\u017f\u0180\7o\2\2\u0180\u0181\7c\2\2\u0181\u0182"+
		"\7l\2\2\u0182\u0183\7q\2\2\u0183\u0184\7t\2\2\u0184\u0185\7V\2\2\u0185"+
		"\u0186\7k\2\2\u0186\u0187\7e\2\2\u0187\u0188\7m\2\2\u0188\u0189\7u\2\2"+
		"\u0189\u018d\3\2\2\2\u018a\u018c\5\25\13\2\u018b\u018a\3\2\2\2\u018c\u018f"+
		"\3\2\2\2\u018d\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u0190\3\2\2\2\u018f"+
		"\u018d\3\2\2\2\u0190\u0194\7?\2\2\u0191\u0193\5\25\13\2\u0192\u0191\3"+
		"\2\2\2\u0193\u0196\3\2\2\2\u0194\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195"+
		"\u0197\3\2\2\2\u0196\u0194\3\2\2\2\u0197\u0198\5\17\b\2\u0198.\3\2\2\2"+
		"\u0199\u019a\7o\2\2\u019a\u019b\7k\2\2\u019b\u019c\7p\2\2\u019c\u019d"+
		"\7q\2\2\u019d\u019e\7t\2\2\u019e\u019f\7V\2\2\u019f\u01a0\7k\2\2\u01a0"+
		"\u01a1\7e\2\2\u01a1\u01a2\7m\2\2\u01a2\u01a3\7u\2\2\u01a3\u01a7\3\2\2"+
		"\2\u01a4\u01a6\5\25\13\2\u01a5\u01a4\3\2\2\2\u01a6\u01a9\3\2\2\2\u01a7"+
		"\u01a5\3\2\2\2\u01a7\u01a8\3\2\2\2\u01a8\u01aa\3\2\2\2\u01a9\u01a7\3\2"+
		"\2\2\u01aa\u01ae\7?\2\2\u01ab\u01ad\5\25\13\2\u01ac\u01ab\3\2\2\2\u01ad"+
		"\u01b0\3\2\2\2\u01ae\u01ac\3\2\2\2\u01ae\u01af\3\2\2\2\u01af\u01b1\3\2"+
		"\2\2\u01b0\u01ae\3\2\2\2\u01b1\u01b2\5\17\b\2\u01b2\60\3\2\2\2\u01b3\u01b8"+
		"\5\7\4\2\u01b4\u01b7\5\7\4\2\u01b5\u01b7\5\t\5\2\u01b6\u01b4\3\2\2\2\u01b6"+
		"\u01b5\3\2\2\2\u01b7\u01ba\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b8\u01b9\3\2"+
		"\2\2\u01b9\62\3\2\2\2\u01ba\u01b8\3\2\2\2\u01bb\u01bc\7*\2\2\u01bc\u01bd"+
		"\5\65\33\2\u01bd\u01be\7+\2\2\u01be\64\3\2\2\2\u01bf\u01c0\7)\2\2\u01c0"+
		"\u01c1\5\23\n\2\u01c1\u01c2\7)\2\2\u01c2\66\3\2\2\2\37\2HMRY[acz\u00c8"+
		"\u00d1\u00d8\u00e4\u00eb\u00f9\u0100\u0108\u0117\u014f\u015b\u0162\u0173"+
		"\u017a\u018d\u0194\u01a7\u01ae\u01b6\u01b8\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}