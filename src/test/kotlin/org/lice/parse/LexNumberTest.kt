package org.lice.parse

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.lice.util.ParseException

class LexNumberTest {

	@Rule
	@JvmField
	var thrown: ExpectedException = ExpectedException.none()

	@Test
	fun testLexHexNumber() {
		val srcCode = "0x74CD161"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.HexNumber, l.currentToken().type)
		assertEquals("0x74CD161", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexBinNumber() {
		val srcCode = "0b101101"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.BinNumber, l.currentToken().type)
		assertEquals("0b101101", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexDecNumber() {
		val srcCode = "074183"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.DecNumber, l.currentToken().type)
		assertEquals("074183", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexOctNumber() {
		val srcCode = "0o1234567"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.OctNumber, l.currentToken().type)
		assertEquals("0o1234567", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexFloatNumber() {
		val srcCode = "3.1415927"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.FloatNumber, l.currentToken().type)
		assertEquals("3.1415927", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexDoubleNumber() {
		val srcCode = "3.141592654"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.DoubleNumber, l.currentToken().type)
		assertEquals("3.141592654", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexDoubleNumber2() {
		val srcCode = "74183D"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.DoubleNumber, l.currentToken().type)
		assertEquals("74183", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexLongInteger() {
		val srcCode = "123L"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.LongInteger, l.currentToken().type)
		assertEquals("123", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexShortInteger() {
		val srcCode = "123S"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.ShortInteger, l.currentToken().type)
		assertEquals("123", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexByte() {
		val srcCode = "123B"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.Byte, l.currentToken().type)
		assertEquals("123", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexBigInt() {
		val srcCode = "123N"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.BigInt, l.currentToken().type)
		assertEquals("123", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexBigDec1() {
		val srcCode = "123M"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.BigDec, l.currentToken().type)
		assertEquals("123", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test
	fun testLexBigDec2() {
		val srcCode = "123.456012M"
		val l = Lexer(srcCode)
		assertEquals(Token.TokenType.BigDec, l.currentToken().type)
		assertEquals("123.456012", l.currentToken().strValue)
		assertEquals(Token.TokenType.EOI, l.peekOneToken().type)
	}

	@Test(timeout = 200)
	fun testLexFloatingNumberFailed() {
		thrown.expect(ParseException::class.java)
		val srcCode = "0xAB.CDEF"
		val l = Lexer(srcCode)
	}

	@Test(timeout = 200)
	fun testLexFloatingNumberFailed2() {
		thrown.expect(ParseException::class.java)
		val srcCode = "0o32F"
		val l = Lexer(srcCode)
	}
}
