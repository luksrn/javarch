package com.github.javarch.support

import spock.lang.Specification;
import spock.lang.Unroll;


class IntegerHelperSpock extends Specification {
	
	@Unroll('Dado a string #string o resultado eh: #expect')
	def "Validação do conversor de inteiros"(){
		
		when:
			def conversao = IntegerHelper.toInteger(string)
		then:
			conversao?.is(expect)			
		where:
			string	|	expect
			"0"		|		0
			"1"		|		1
			"-1"	| 		-1
			null	|		0
			""		|		0
			"10"	|		10
	}
	
	@Unroll('Dado a string #string o resultado eh #expect dado um valor padrao #defaultValue')
	def "Validação de conversor com valores padrão"(){
		
		when:
			def conversao = IntegerHelper.toInteger(string, defaultValue)
		then:
			conversao?.is(expect)
		where:
			string	|	expect	|	defaultValue
			""		|	0		|	0
			""		|	10		|	10
			null	|	10		|	10
			"10"	|	10		|	0
			"-1"	|	-1		|	0
			
	}
}
