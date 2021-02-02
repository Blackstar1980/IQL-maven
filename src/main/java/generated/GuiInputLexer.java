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
		ApproveCon=8, CancelCon=9, MinCon=10, MaxCon=11, RegexCon=12, OptionalCon=13, 
		DisplayCon=14, HolderCon=15, SelectedCon=16, MajorTicksCon=17, MinorTicksCon=18, 
		NameWord=19, DefaultValue=20, QuotedCharText=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "LowerCaseLetter", "UpperCaseLetter", "Bool", "Digit", 
			"Integer", "Decimal", "MultiOpt", "SingleOpt", "Slider", "CharText", 
			"Whitespace", "DialogId", "GroupId", "TabId", "CompId", "ApproveCon", 
			"CancelCon", "MinCon", "MaxCon", "RegexCon", "OptionalCon", "DisplayCon", 
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
			"ApproveCon", "CancelCon", "MinCon", "MaxCon", "RegexCon", "OptionalCon", 
			"DisplayCon", "HolderCon", "SelectedCon", "MajorTicksCon", "MinorTicksCon", 
			"NameWord", "DefaultValue", "QuotedCharText"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\27\u0227\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\5\6S\n\6\3\7\3\7\3\b\5\bX\n\b\3\b\6\b[\n\b\r\b\16\b\\\3\t\3\t\3\t\6\t"+
		"b\n\t\r\t\16\tc\5\tf\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\7\r\u00a6\n\r\f\r\16\r\u00a9\13\r\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\5\17\u00c1\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\5\22\u00fb\n\22\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\7\23\u0106\n\23\f\23\16\23\u0109\13\23\3\23\3\23\7\23"+
		"\u010d\n\23\f\23\16\23\u0110\13\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\7\24\u011c\n\24\f\24\16\24\u011f\13\24\3\24\3\24\7\24"+
		"\u0123\n\24\f\24\16\24\u0126\13\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25"+
		"\7\25\u012f\n\25\f\25\16\25\u0132\13\25\3\25\3\25\7\25\u0136\n\25\f\25"+
		"\16\25\u0139\13\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\7\26\u0142\n\26"+
		"\f\26\16\26\u0145\13\26\3\26\3\26\7\26\u0149\n\26\f\26\16\26\u014c\13"+
		"\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u0157\n\27\f\27"+
		"\16\27\u015a\13\27\3\27\3\27\7\27\u015e\n\27\f\27\16\27\u0161\13\27\3"+
		"\27\3\27\3\30\7\30\u0166\n\30\f\30\16\30\u0169\13\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30\u0175\n\30\f\30\16\30\u0178\13"+
		"\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u01af"+
		"\n\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\7\32\u01be\n\32\f\32\16\32\u01c1\13\32\3\32\3\32\7\32\u01c5\n\32\f\32"+
		"\16\32\u01c8\13\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"+
		"\33\3\33\7\33\u01d6\n\33\f\33\16\33\u01d9\13\33\3\33\3\33\7\33\u01dd\n"+
		"\33\f\33\16\33\u01e0\13\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\7\34\u01f0\n\34\f\34\16\34\u01f3\13\34\3\34"+
		"\3\34\7\34\u01f7\n\34\f\34\16\34\u01fa\13\34\3\34\3\34\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\7\35\u020a\n\35\f\35\16"+
		"\35\u020d\13\35\3\35\3\35\7\35\u0211\n\35\f\35\16\35\u0214\13\35\3\35"+
		"\3\35\3\36\3\36\3\36\7\36\u021b\n\36\f\36\16\36\u021e\13\36\3\37\3\37"+
		"\3\37\3\37\3 \3 \3 \3 \2\2!\3\3\5\4\7\2\t\2\13\2\r\2\17\2\21\2\23\2\25"+
		"\2\27\2\31\2\33\5\35\6\37\7!\b#\t%\n\'\13)\f+\r-\16/\17\61\20\63\21\65"+
		"\22\67\239\24;\25=\26?\27\3\2\b\3\2c|\3\2C\\\3\2\62;\b\2##\'(,-//>@~~"+
		"\13\2\"\"%&..\60\60<=AB^^`b\u0080\u0080\5\2\13\f\17\17\"\"\2\u0255\2\3"+
		"\3\2\2\2\2\5\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\3A\3\2\2\2\5C\3\2\2\2\7E\3\2\2\2\t"+
		"G\3\2\2\2\13R\3\2\2\2\rT\3\2\2\2\17W\3\2\2\2\21^\3\2\2\2\23g\3\2\2\2\25"+
		"t\3\2\2\2\27\u0082\3\2\2\2\31\u00a7\3\2\2\2\33\u00aa\3\2\2\2\35\u00c0"+
		"\3\2\2\2\37\u00c2\3\2\2\2!\u00c8\3\2\2\2#\u00fa\3\2\2\2%\u00fc\3\2\2\2"+
		"\'\u0113\3\2\2\2)\u0129\3\2\2\2+\u013c\3\2\2\2-\u014f\3\2\2\2/\u0167\3"+
		"\2\2\2\61\u01ae\3\2\2\2\63\u01b0\3\2\2\2\65\u01cb\3\2\2\2\67\u01e3\3\2"+
		"\2\29\u01fd\3\2\2\2;\u0217\3\2\2\2=\u021f\3\2\2\2?\u0223\3\2\2\2AB\7}"+
		"\2\2B\4\3\2\2\2CD\7\177\2\2D\6\3\2\2\2EF\t\2\2\2F\b\3\2\2\2GH\t\3\2\2"+
		"H\n\3\2\2\2IJ\7v\2\2JK\7t\2\2KL\7w\2\2LS\7g\2\2MN\7h\2\2NO\7c\2\2OP\7"+
		"n\2\2PQ\7u\2\2QS\7g\2\2RI\3\2\2\2RM\3\2\2\2S\f\3\2\2\2TU\t\4\2\2U\16\3"+
		"\2\2\2VX\7/\2\2WV\3\2\2\2WX\3\2\2\2XZ\3\2\2\2Y[\5\r\7\2ZY\3\2\2\2[\\\3"+
		"\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]\20\3\2\2\2^e\5\17\b\2_a\7\60\2\2`b\5\r\7"+
		"\2a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2\2df\3\2\2\2e_\3\2\2\2ef\3\2\2"+
		"\2f\22\3\2\2\2gh\7O\2\2hi\7w\2\2ij\7n\2\2jk\7v\2\2kl\7k\2\2lm\7Q\2\2m"+
		"n\7r\2\2no\7v\2\2op\7]\2\2pq\3\2\2\2qr\5? \2rs\7_\2\2s\24\3\2\2\2tu\7"+
		"U\2\2uv\7k\2\2vw\7p\2\2wx\7i\2\2xy\7n\2\2yz\7g\2\2z{\7Q\2\2{|\7r\2\2|"+
		"}\7v\2\2}~\7]\2\2~\177\3\2\2\2\177\u0080\5? \2\u0080\u0081\7_\2\2\u0081"+
		"\26\3\2\2\2\u0082\u0083\7U\2\2\u0083\u0084\7n\2\2\u0084\u0085\7k\2\2\u0085"+
		"\u0086\7f\2\2\u0086\u0087\7g\2\2\u0087\u0088\7t\2\2\u0088\u0089\7]\2\2"+
		"\u0089\u008a\3\2\2\2\u008a\u008b\5? \2\u008b\u008c\7_\2\2\u008c\30\3\2"+
		"\2\2\u008d\u00a6\5\7\4\2\u008e\u00a6\5\t\5\2\u008f\u00a6\5\r\7\2\u0090"+
		"\u00a6\t\5\2\2\u0091\u0092\7^\2\2\u0092\u00a6\7]\2\2\u0093\u0094\7^\2"+
		"\2\u0094\u00a6\7_\2\2\u0095\u00a6\t\6\2\2\u0096\u0097\7^\2\2\u0097\u00a6"+
		"\7$\2\2\u0098\u0099\7^\2\2\u0099\u00a6\7^\2\2\u009a\u00a6\7\f\2\2\u009b"+
		"\u009c\7^\2\2\u009c\u00a6\7)\2\2\u009d\u009e\7^\2\2\u009e\u00a6\7+\2\2"+
		"\u009f\u00a0\7^\2\2\u00a0\u00a6\7*\2\2\u00a1\u00a2\7^\2\2\u00a2\u00a6"+
		"\7}\2\2\u00a3\u00a4\7^\2\2\u00a4\u00a6\7\177\2\2\u00a5\u008d\3\2\2\2\u00a5"+
		"\u008e\3\2\2\2\u00a5\u008f\3\2\2\2\u00a5\u0090\3\2\2\2\u00a5\u0091\3\2"+
		"\2\2\u00a5\u0093\3\2\2\2\u00a5\u0095\3\2\2\2\u00a5\u0096\3\2\2\2\u00a5"+
		"\u0098\3\2\2\2\u00a5\u009a\3\2\2\2\u00a5\u009b\3\2\2\2\u00a5\u009d\3\2"+
		"\2\2\u00a5\u009f\3\2\2\2\u00a5\u00a1\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6"+
		"\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\32\3\2\2"+
		"\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab\t\7\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad"+
		"\b\16\2\2\u00ad\34\3\2\2\2\u00ae\u00af\7U\2\2\u00af\u00b0\7k\2\2\u00b0"+
		"\u00b1\7p\2\2\u00b1\u00b2\7i\2\2\u00b2\u00b3\7n\2\2\u00b3\u00c1\7g\2\2"+
		"\u00b4\u00b5\7V\2\2\u00b5\u00b6\7c\2\2\u00b6\u00b7\7d\2\2\u00b7\u00b8"+
		"\7w\2\2\u00b8\u00b9\7n\2\2\u00b9\u00ba\7c\2\2\u00ba\u00c1\7t\2\2\u00bb"+
		"\u00bc\7R\2\2\u00bc\u00bd\7c\2\2\u00bd\u00be\7i\2\2\u00be\u00bf\7g\2\2"+
		"\u00bf\u00c1\7u\2\2\u00c0\u00ae\3\2\2\2\u00c0\u00b4\3\2\2\2\u00c0\u00bb"+
		"\3\2\2\2\u00c1\36\3\2\2\2\u00c2\u00c3\7I\2\2\u00c3\u00c4\7t\2\2\u00c4"+
		"\u00c5\7q\2\2\u00c5\u00c6\7w\2\2\u00c6\u00c7\7r\2\2\u00c7 \3\2\2\2\u00c8"+
		"\u00c9\7V\2\2\u00c9\u00ca\7c\2\2\u00ca\u00cb\7d\2\2\u00cb\"\3\2\2\2\u00cc"+
		"\u00cd\7U\2\2\u00cd\u00ce\7v\2\2\u00ce\u00cf\7t\2\2\u00cf\u00d0\7k\2\2"+
		"\u00d0\u00d1\7p\2\2\u00d1\u00fb\7i\2\2\u00d2\u00d3\7K\2\2\u00d3\u00d4"+
		"\7p\2\2\u00d4\u00d5\7v\2\2\u00d5\u00d6\7g\2\2\u00d6\u00d7\7i\2\2\u00d7"+
		"\u00d8\7g\2\2\u00d8\u00fb\7t\2\2\u00d9\u00da\7F\2\2\u00da\u00db\7g\2\2"+
		"\u00db\u00dc\7e\2\2\u00dc\u00dd\7k\2\2\u00dd\u00de\7o\2\2\u00de\u00df"+
		"\7c\2\2\u00df\u00fb\7n\2\2\u00e0\u00e1\7D\2\2\u00e1\u00e2\7q\2\2\u00e2"+
		"\u00e3\7q\2\2\u00e3\u00e4\7n\2\2\u00e4\u00e5\7g\2\2\u00e5\u00e6\7c\2\2"+
		"\u00e6\u00fb\7p\2\2\u00e7\u00fb\5\25\13\2\u00e8\u00fb\5\23\n\2\u00e9\u00ea"+
		"\7R\2\2\u00ea\u00eb\7c\2\2\u00eb\u00ec\7u\2\2\u00ec\u00ed\7u\2\2\u00ed"+
		"\u00ee\7y\2\2\u00ee\u00ef\7q\2\2\u00ef\u00f0\7t\2\2\u00f0\u00fb\7f\2\2"+
		"\u00f1\u00fb\5\27\f\2\u00f2\u00f3\7V\2\2\u00f3\u00f4\7g\2\2\u00f4\u00f5"+
		"\7z\2\2\u00f5\u00f6\7v\2\2\u00f6\u00f7\7C\2\2\u00f7\u00f8\7t\2\2\u00f8"+
		"\u00f9\7g\2\2\u00f9\u00fb\7c\2\2\u00fa\u00cc\3\2\2\2\u00fa\u00d2\3\2\2"+
		"\2\u00fa\u00d9\3\2\2\2\u00fa\u00e0\3\2\2\2\u00fa\u00e7\3\2\2\2\u00fa\u00e8"+
		"\3\2\2\2\u00fa\u00e9\3\2\2\2\u00fa\u00f1\3\2\2\2\u00fa\u00f2\3\2\2\2\u00fb"+
		"$\3\2\2\2\u00fc\u00fd\7c\2\2\u00fd\u00fe\7r\2\2\u00fe\u00ff\7r\2\2\u00ff"+
		"\u0100\7t\2\2\u0100\u0101\7q\2\2\u0101\u0102\7x\2\2\u0102\u0103\7g\2\2"+
		"\u0103\u0107\3\2\2\2\u0104\u0106\5\33\16\2\u0105\u0104\3\2\2\2\u0106\u0109"+
		"\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u010a\3\2\2\2\u0109"+
		"\u0107\3\2\2\2\u010a\u010e\7?\2\2\u010b\u010d\5\33\16\2\u010c\u010b\3"+
		"\2\2\2\u010d\u0110\3\2\2\2\u010e\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f"+
		"\u0111\3\2\2\2\u0110\u010e\3\2\2\2\u0111\u0112\5? \2\u0112&\3\2\2\2\u0113"+
		"\u0114\7e\2\2\u0114\u0115\7c\2\2\u0115\u0116\7p\2\2\u0116\u0117\7e\2\2"+
		"\u0117\u0118\7g\2\2\u0118\u0119\7n\2\2\u0119\u011d\3\2\2\2\u011a\u011c"+
		"\5\33\16\2\u011b\u011a\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2\2\2"+
		"\u011d\u011e\3\2\2\2\u011e\u0120\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0124"+
		"\7?\2\2\u0121\u0123\5\33\16\2\u0122\u0121\3\2\2\2\u0123\u0126\3\2\2\2"+
		"\u0124\u0122\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0127\3\2\2\2\u0126\u0124"+
		"\3\2\2\2\u0127\u0128\5? \2\u0128(\3\2\2\2\u0129\u012a\7o\2\2\u012a\u012b"+
		"\7k\2\2\u012b\u012c\7p\2\2\u012c\u0130\3\2\2\2\u012d\u012f\5\33\16\2\u012e"+
		"\u012d\3\2\2\2\u012f\u0132\3\2\2\2\u0130\u012e\3\2\2\2\u0130\u0131\3\2"+
		"\2\2\u0131\u0133\3\2\2\2\u0132\u0130\3\2\2\2\u0133\u0137\7?\2\2\u0134"+
		"\u0136\5\33\16\2\u0135\u0134\3\2\2\2\u0136\u0139\3\2\2\2\u0137\u0135\3"+
		"\2\2\2\u0137\u0138\3\2\2\2\u0138\u013a\3\2\2\2\u0139\u0137\3\2\2\2\u013a"+
		"\u013b\5\21\t\2\u013b*\3\2\2\2\u013c\u013d\7o\2\2\u013d\u013e\7c\2\2\u013e"+
		"\u013f\7z\2\2\u013f\u0143\3\2\2\2\u0140\u0142\5\33\16\2\u0141\u0140\3"+
		"\2\2\2\u0142\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144"+
		"\u0146\3\2\2\2\u0145\u0143\3\2\2\2\u0146\u014a\7?\2\2\u0147\u0149\5\33"+
		"\16\2\u0148\u0147\3\2\2\2\u0149\u014c\3\2\2\2\u014a\u0148\3\2\2\2\u014a"+
		"\u014b\3\2\2\2\u014b\u014d\3\2\2\2\u014c\u014a\3\2\2\2\u014d\u014e\5\21"+
		"\t\2\u014e,\3\2\2\2\u014f\u0150\7t\2\2\u0150\u0151\7g\2\2\u0151\u0152"+
		"\7i\2\2\u0152\u0153\7g\2\2\u0153\u0154\7z\2\2\u0154\u0158\3\2\2\2\u0155"+
		"\u0157\5\33\16\2\u0156\u0155\3\2\2\2\u0157\u015a\3\2\2\2\u0158\u0156\3"+
		"\2\2\2\u0158\u0159\3\2\2\2\u0159\u015b\3\2\2\2\u015a\u0158\3\2\2\2\u015b"+
		"\u015f\7?\2\2\u015c\u015e\5\33\16\2\u015d\u015c\3\2\2\2\u015e\u0161\3"+
		"\2\2\2\u015f\u015d\3\2\2\2\u015f\u0160\3\2\2\2\u0160\u0162\3\2\2\2\u0161"+
		"\u015f\3\2\2\2\u0162\u0163\5? \2\u0163.\3\2\2\2\u0164\u0166\5\33\16\2"+
		"\u0165\u0164\3\2\2\2\u0166\u0169\3\2\2\2\u0167\u0165\3\2\2\2\u0167\u0168"+
		"\3\2\2\2\u0168\u016a\3\2\2\2\u0169\u0167\3\2\2\2\u016a\u016b\7q\2\2\u016b"+
		"\u016c\7r\2\2\u016c\u016d\7v\2\2\u016d\u016e\7k\2\2\u016e\u016f\7q\2\2"+
		"\u016f\u0170\7p\2\2\u0170\u0171\7c\2\2\u0171\u0172\7n\2\2\u0172\u0176"+
		"\3\2\2\2\u0173\u0175\5\33\16\2\u0174\u0173\3\2\2\2\u0175\u0178\3\2\2\2"+
		"\u0176\u0174\3\2\2\2\u0176\u0177\3\2\2\2\u0177\60\3\2\2\2\u0178\u0176"+
		"\3\2\2\2\u0179\u017a\7k\2\2\u017a\u017b\7p\2\2\u017b\u017c\7n\2\2\u017c"+
		"\u017d\7k\2\2\u017d\u017e\7p\2\2\u017e\u01af\7g\2\2\u017f\u0180\7d\2\2"+
		"\u0180\u0181\7n\2\2\u0181\u0182\7q\2\2\u0182\u0183\7e\2\2\u0183\u01af"+
		"\7m\2\2\u0184\u0185\7k\2\2\u0185\u0186\7p\2\2\u0186\u0187\7n\2\2\u0187"+
		"\u0188\7k\2\2\u0188\u0189\7p\2\2\u0189\u018a\7g\2\2\u018a\u018b\7N\2\2"+
		"\u018b\u018c\7k\2\2\u018c\u018d\7u\2\2\u018d\u01af\7v\2\2\u018e\u018f"+
		"\7d\2\2\u018f\u0190\7n\2\2\u0190\u0191\7q\2\2\u0191\u0192\7e\2\2\u0192"+
		"\u0193\7m\2\2\u0193\u0194\7N\2\2\u0194\u0195\7k\2\2\u0195\u0196\7u\2\2"+
		"\u0196\u01af\7v\2\2\u0197\u0198\7d\2\2\u0198\u0199\7n\2\2\u0199\u019a"+
		"\7q\2\2\u019a\u019b\7e\2\2\u019b\u019c\7m\2\2\u019c\u019d\7T\2\2\u019d"+
		"\u019e\7c\2\2\u019e\u019f\7f\2\2\u019f\u01a0\7k\2\2\u01a0\u01af\7q\2\2"+
		"\u01a1\u01a2\7d\2\2\u01a2\u01a3\7n\2\2\u01a3\u01a4\7q\2\2\u01a4\u01a5"+
		"\7e\2\2\u01a5\u01a6\7m\2\2\u01a6\u01a7\7E\2\2\u01a7\u01a8\7j\2\2\u01a8"+
		"\u01a9\7g\2\2\u01a9\u01aa\7e\2\2\u01aa\u01ab\7m\2\2\u01ab\u01ac\7d\2\2"+
		"\u01ac\u01ad\7q\2\2\u01ad\u01af\7z\2\2\u01ae\u0179\3\2\2\2\u01ae\u017f"+
		"\3\2\2\2\u01ae\u0184\3\2\2\2\u01ae\u018e\3\2\2\2\u01ae\u0197\3\2\2\2\u01ae"+
		"\u01a1\3\2\2\2\u01af\62\3\2\2\2\u01b0\u01b1\7r\2\2\u01b1\u01b2\7n\2\2"+
		"\u01b2\u01b3\7c\2\2\u01b3\u01b4\7e\2\2\u01b4\u01b5\7g\2\2\u01b5\u01b6"+
		"\7j\2\2\u01b6\u01b7\7q\2\2\u01b7\u01b8\7n\2\2\u01b8\u01b9\7f\2\2\u01b9"+
		"\u01ba\7g\2\2\u01ba\u01bb\7t\2\2\u01bb\u01bf\3\2\2\2\u01bc\u01be\5\33"+
		"\16\2\u01bd\u01bc\3\2\2\2\u01be\u01c1\3\2\2\2\u01bf\u01bd\3\2\2\2\u01bf"+
		"\u01c0\3\2\2\2\u01c0\u01c2\3\2\2\2\u01c1\u01bf\3\2\2\2\u01c2\u01c6\7?"+
		"\2\2\u01c3\u01c5\5\33\16\2\u01c4\u01c3\3\2\2\2\u01c5\u01c8\3\2\2\2\u01c6"+
		"\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7\u01c9\3\2\2\2\u01c8\u01c6\3\2"+
		"\2\2\u01c9\u01ca\5? \2\u01ca\64\3\2\2\2\u01cb\u01cc\7u\2\2\u01cc\u01cd"+
		"\7g\2\2\u01cd\u01ce\7n\2\2\u01ce\u01cf\7g\2\2\u01cf\u01d0\7e\2\2\u01d0"+
		"\u01d1\7v\2\2\u01d1\u01d2\7g\2\2\u01d2\u01d3\7f\2\2\u01d3\u01d7\3\2\2"+
		"\2\u01d4\u01d6\5\33\16\2\u01d5\u01d4\3\2\2\2\u01d6\u01d9\3\2\2\2\u01d7"+
		"\u01d5\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01da\3\2\2\2\u01d9\u01d7\3\2"+
		"\2\2\u01da\u01de\7?\2\2\u01db\u01dd\5\33\16\2\u01dc\u01db\3\2\2\2\u01dd"+
		"\u01e0\3\2\2\2\u01de\u01dc\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e1\3\2"+
		"\2\2\u01e0\u01de\3\2\2\2\u01e1\u01e2\5? \2\u01e2\66\3\2\2\2\u01e3\u01e4"+
		"\7o\2\2\u01e4\u01e5\7c\2\2\u01e5\u01e6\7l\2\2\u01e6\u01e7\7q\2\2\u01e7"+
		"\u01e8\7t\2\2\u01e8\u01e9\7V\2\2\u01e9\u01ea\7k\2\2\u01ea\u01eb\7e\2\2"+
		"\u01eb\u01ec\7m\2\2\u01ec\u01ed\7u\2\2\u01ed\u01f1\3\2\2\2\u01ee\u01f0"+
		"\5\33\16\2\u01ef\u01ee\3\2\2\2\u01f0\u01f3\3\2\2\2\u01f1\u01ef\3\2\2\2"+
		"\u01f1\u01f2\3\2\2\2\u01f2\u01f4\3\2\2\2\u01f3\u01f1\3\2\2\2\u01f4\u01f8"+
		"\7?\2\2\u01f5\u01f7\5\33\16\2\u01f6\u01f5\3\2\2\2\u01f7\u01fa\3\2\2\2"+
		"\u01f8\u01f6\3\2\2\2\u01f8\u01f9\3\2\2\2\u01f9\u01fb\3\2\2\2\u01fa\u01f8"+
		"\3\2\2\2\u01fb\u01fc\5\17\b\2\u01fc8\3\2\2\2\u01fd\u01fe\7o\2\2\u01fe"+
		"\u01ff\7k\2\2\u01ff\u0200\7p\2\2\u0200\u0201\7q\2\2\u0201\u0202\7t\2\2"+
		"\u0202\u0203\7V\2\2\u0203\u0204\7k\2\2\u0204\u0205\7e\2\2\u0205\u0206"+
		"\7m\2\2\u0206\u0207\7u\2\2\u0207\u020b\3\2\2\2\u0208\u020a\5\33\16\2\u0209"+
		"\u0208\3\2\2\2\u020a\u020d\3\2\2\2\u020b\u0209\3\2\2\2\u020b\u020c\3\2"+
		"\2\2\u020c\u020e\3\2\2\2\u020d\u020b\3\2\2\2\u020e\u0212\7?\2\2\u020f"+
		"\u0211\5\33\16\2\u0210\u020f\3\2\2\2\u0211\u0214\3\2\2\2\u0212\u0210\3"+
		"\2\2\2\u0212\u0213\3\2\2\2\u0213\u0215\3\2\2\2\u0214\u0212\3\2\2\2\u0215"+
		"\u0216\5\17\b\2\u0216:\3\2\2\2\u0217\u021c\5\7\4\2\u0218\u021b\5\7\4\2"+
		"\u0219\u021b\5\t\5\2\u021a\u0218\3\2\2\2\u021a\u0219\3\2\2\2\u021b\u021e"+
		"\3\2\2\2\u021c\u021a\3\2\2\2\u021c\u021d\3\2\2\2\u021d<\3\2\2\2\u021e"+
		"\u021c\3\2\2\2\u021f\u0220\7*\2\2\u0220\u0221\5? \2\u0221\u0222\7+\2\2"+
		"\u0222>\3\2\2\2\u0223\u0224\7)\2\2\u0224\u0225\5\31\r\2\u0225\u0226\7"+
		")\2\2\u0226@\3\2\2\2#\2RW\\ce\u00a5\u00a7\u00c0\u00fa\u0107\u010e\u011d"+
		"\u0124\u0130\u0137\u0143\u014a\u0158\u015f\u0167\u0176\u01ae\u01bf\u01c6"+
		"\u01d7\u01de\u01f1\u01f8\u020b\u0212\u021a\u021c\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}