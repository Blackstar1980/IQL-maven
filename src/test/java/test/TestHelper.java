package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import parser.Parser;

public class TestHelper {
	public static void arrgumentException(String input, String expected) {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Parser.parse(input);
	    });
		assertEquals(expected, exception.getMessage());
	}
	
	public static void numberException(String input, String expected) {
		Exception exception = assertThrows(NumberFormatException.class, () -> {
			Parser.parse(input);
	    });
		assertEquals(expected, exception.getMessage());
	}
	
	public static void checkParseError(String input) {
		Error error = assertThrows(Error.class, () -> {
			Parser.parse(input);
	    });
		assertTrue(error.getMessage().contains("Parsing error"));
	}
	
	public static void checkAst(String input, String expected) {
		var ast = Parser.parse(input);
		assertEquals(expected, ast.toString());
	}
}
