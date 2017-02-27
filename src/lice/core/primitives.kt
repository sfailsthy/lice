/**
 * Created by ice1000 on 2017/2/25.
 *
 * @author ice1000
 */
@file:Suppress("NOTHING_TO_INLINE")
@file:JvmName("Standard")
@file:JvmMultifileClass

package lice.core

import lice.compiler.model.Node
import lice.compiler.model.ValueNode
import lice.compiler.util.InterpretException
import lice.compiler.util.SymbolList


inline fun SymbolList.addNumberFunctions() {
	defineFunction("int->double", { ln, ls ->
		ValueNode((ls[0].eval().o as Int).toDouble(), ln)
	})
	defineFunction("+", { ln, list ->
		ValueNode(list.fold(0) { sum, value ->
			val res = value.eval()
			when (res.o) {
				is Int -> res.o + sum
				else -> InterpretException.typeMisMatch("Int", res, ln)
			}
		}, ln)
	})
	defineFunction("-", { ln, ls ->
		when (ls.size) {
			0 -> ValueNode(0, ln)
			1 -> ValueNode(ls[0].eval(), ln)
			else -> {
				var res = ls[0].eval().o as Int
				for (i in 1..ls.size - 1)
					res -= ls[i].eval().o as Int
				ValueNode(res, ln)
			}
		}
	})
	defineFunction("/", { ln, ls ->
		when (ls.size) {
			0 -> ValueNode(1, ln)
			1 -> ValueNode(ls[0].eval(), ln)
			else -> {
				var res = ls[0].eval().o as Int
				for (i in 1..ls.size - 1)
					res /= ls[i].eval().o as Int
				ValueNode(res, ln)
			}
		}
	})
	defineFunction("%", { ln, ls ->
		when (ls.size) {
			0 -> ValueNode(0, ln)
			1 -> ValueNode(ls[0].eval(), ln)
			else -> {
				var res = ls[0].eval().o as Int
				@Suppress("DEPRECATION")
				for (i in 1..ls.size - 1)
					res = res.mod(ls[i].eval().o as Int)
				ValueNode(res, ln)
			}
		}
	})
	defineFunction("*", { ln, ls ->
		ValueNode(ls.fold(1) { sum, value ->
			val res = value.eval()
			when (res.o) {
				is Int -> res.o * sum
				else -> InterpretException.typeMisMatch("Int", res, ln)
			}
		}, ln)
	})
	defineFunction("==", { ln, list ->
		val ls = list.map(Node::eval)
		ValueNode((1..ls.size - 1).all {
			ls[it].o == ls[it - 1].o
		}, ln)
	})
	defineFunction("!=", { ln, list ->
		val ls = list.map(Node::eval)
		ValueNode((1..ls.size - 1).all {
			ls[it].o != ls[it - 1].o
		}, ln)
	})
	defineFunction("<", { ln, list ->
		val ls = list.map(Node::eval)
		ValueNode((1..ls.size - 1).all {
			(ls[it - 1].o as Int) < (ls[it].o as Int)
		}, ln)
	})
	defineFunction(">", { ln, list ->
		val ls = list.map(Node::eval)
		ValueNode((1..ls.size - 1).all {
			ls[it - 1].o as Int > (ls[it].o as Int)
		}, ln)
	})
	defineFunction(">=", { ln, list ->
		val ls = list.map(Node::eval)
		ValueNode((1..ls.size - 1).all {
			(ls[it - 1].o as Int) >= (ls[it].o as Int)
		}, ln)
	})
	defineFunction("<=", { ln, ls ->
		val list = ls.map(Node::eval)
		ValueNode((1..list.size - 1).all {
			(list[it - 1].o as Int) <= (list[it].o as Int)
		}, ln)
	})
	defineFunction("&", { ln, ls ->
		val list = ls.map(Node::eval)
		ValueNode((1..list.size - 1).fold(list[0].o as Int) { last, self ->
			last and list[self].o as Int
		}, ln)
	})
	defineFunction("|", { ln, ls ->
		val list = ls.map(Node::eval)
		ValueNode((1..list.size - 1).fold(list[0].o as Int) { last, self ->
			last or list[self].o as Int
		}, ln)
	})
	defineFunction("^", { ln, ls ->
		val list = ls.map(Node::eval)
		ValueNode((1..list.size - 1).fold(list[0].o as Int) { last, self ->
			last xor list[self].o as Int
		}, ln)
	})
}

inline fun SymbolList.addBoolFunctions() {
	defineFunction("&&", { ln, ls ->
		ValueNode(ls.fold(true) { sum, value ->
			val o = value.eval()
			when {
				o.o is Boolean -> o.o && sum
				else -> InterpretException.typeMisMatch("Boolean", o, ln)
			}
		}, ln)
	})
	defineFunction("||", { ln, ls ->
		ValueNode(ls.fold(false) { sum, value ->
			val o = value.eval()
			when {
				o.o is Boolean -> o.o || sum
				else -> InterpretException.typeMisMatch("Boolean", o, ln)
			}
		}, ln)
	})
	defineFunction("!", { ln, ls ->
		ValueNode(!(ls[0].eval().o as Boolean), ln)
	})
}


