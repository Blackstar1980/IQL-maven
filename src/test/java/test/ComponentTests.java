package test;

import org.junit.Test;

public class ComponentTests {
	@Test public void tab01() {
		TestHelper.arrgumentException("""
					'Dialog Title' Single('dialog description')
					'First Tab:' Tab{
					name 'Name:' String('John')
					}
					""",
					"There must be at least 2 tabs");
	}
	
	@Test public void tab02() {
		TestHelper.checkParseError("""
				'Dialog Title' Single('dialog description')
				'First Tab:' Tab{}
				""");
	}
	
	@Test public void tab03() {
		TestHelper.checkParseError("""
			'Dialog Title' Single('dialog description')
			'First Tab:' Taab{
			name 'Name:' String('John')
			}
			""");
	}
	
	@Test public void tab04() {
		TestHelper.arrgumentException("""
				'Dialog Title' Single('dialog description')
				'First Tab:' Tab{
				name 'Name:' String('John')
				}
				""",
				"""
				There must be at least 2 tabs\
				""");
	}
	
	@Test public void tab05() {
		TestHelper.checkAst("""
				'Dialog Title' Single('dialog description')
				'First Tab:' Tab{
				name 'Name:' String('John')
				}
				'Second Tab:' Tab{
					'new group' Group {
						age 'Age' Integer
				}
				surname 'Surname:' String('Doe')
				}
				""",
				"""
				Query[dialog=Single[title=Dialog Title, \
				description=dialog description, constraints=[]], \
				containers=[Tab [title=First Tab:, \
				containers=[String [name=name, prompt=Name:, defVal=John, constraints=[]]]], \
				Tab [title=Second Tab:, containers=[Group [title=new group, \
				components=[Integer [name=age, prompt=Age, defVal=null, \
				constraints=[]]]], String [name=surname, prompt=Surname:, \
				defVal=Doe, constraints=[]]]]]]\
				""");
	}
	
	@Test public void group01() {
		TestHelper.checkAst("""
				'Dialog Title' Single('dialog description')
				'First Group:' Group{
					name 'Name:' String('John')
				}
				""",
				"""
				Query[dialog=Single[title=Dialog Title, \
				description=dialog description, constraints=[]], \
				containers=[Group [title=First Group:, \
				components=[String [name=name, prompt=Name:, \
				defVal=John, constraints=[]]]]]]\
				""");
	}
	
	@Test public void group02() {
		TestHelper.checkParseError("""
				'Dialog Title' Single('dialog description')
				'First Group:' Group{}
				}
				""");
	}
	
	@Test public void group03() {
		TestHelper.checkParseError("""
				'Dialog Title' Single('dialog description')
				'First Group:' GroupY{
					name 'Name:' String('John')
				}
				""");
	}
		
	@Test public void comp01() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				name 'Name:' String('John')
				name 'Second Name:' String('Doe')
				""",
				"Components names must be unique. 'name', is appear more then once.");
	}
	
	@Test public void comp02() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				'first group' Group{
				name 'Name:' String('John')
				}
				'second group' Group{
				name 'Second Name:' String('Doe')
				}
				""",
				"Components names must be unique. 'name', is appear more then once.");
	}
	
	@Test public void comp03() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				'first Tab' Tab{
				name 'Name:' String('John')
				}
				'second Tab' Tab{
				name 'Second Name:' String('Doe')
				}
				""",
				"Components names must be unique. 'name', is appear more then once.");
	}
	
	@Test public void comp04() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			name 'Name:' String('John') {max=1 max=6}
			""",
			"'MAX' constraint repeat more then once");
	}
	
	@Test public void comp05() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			name 'Name:' String('John') {min=1 max=6 inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[String [name=name, prompt=Name:, defVal=John, \
			constraints=[MinCon[value=1.0], MaxCon[value=6.0], Inline]]]]\
			""");
	}
	
	@Test public void comp06() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean('true')
			dogs 'Have dogs?' Boolean('false')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, prompt=Have cats?, \
			defVal=true, constraints=[]], Boolean [name=dogs, \
			prompt=Have dogs?, defVal=false, constraints=[]]]]\
			""");
	}
	
	@Test public void comp07() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean
			dogs 'Have dogs?' Boolean
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, prompt=Have cats?, \
			defVal=, constraints=[]], Boolean [name=dogs, \
			prompt=Have dogs?, defVal=, constraints=[]]]]\
			""");
	}
	
	@Test public void comp08() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			dogs 'Have dogs?' Boolean('yes')
			""",
			"Boolean default value must be only 'true' or 'false'");
	}
	
	@Test public void comp09() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' String('My default value')
			{max=4 min=2  placeholder='my holder'  inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[String [name=cats, prompt=Have cats?, defVal=My default value, \
			constraints=[MaxCon[value=4.0], MinCon[value=2.0], \
			HolderCon[value=my holder], Inline]]]]\
			""");
	}
	
	@Test public void comp10() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' TextArea('My default value')
			{max=4 min=2  placeholder='my holder' inline optional}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[TextArea [name=cats, prompt=Have cats?, defVal=My default value, \
			constraints=[MaxCon[value=4.0], MinCon[value=2.0], \
			HolderCon[value=my holder], Inline, Optional]]]]\
			""");
	}
	
	@Test public void comp11() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			password 'Password:' Password('My default value')
			{max=8 min=2 placeholder='my holder' inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Password [name=password, prompt=Password:, defVal=My default value, \
			constraints=[MaxCon[value=8.0], MinCon[value=2.0], \
			HolderCon[value=my holder], Inline]]]]\
			""");
	}

	@Test public void comp12() { 
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			phone 'Phone:' Integer('988643')
			{max=8 min=2 placeholder='87654' inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Integer [name=phone, prompt=Phone:, defVal=988643, \
			constraints=[MaxCon[value=8.0], MinCon[value=2.0], \
			HolderCon[value=87654], Inline]]]]\
			""");
	}
	
	@Test public void comp13() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Decimal('1.45')
			{max=8 min=2 placeholder='enter your height' inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Decimal [name=height, prompt=Height:, defVal=1.45, \
			constraints=[MaxCon[value=8.0], MinCon[value=2.0], \
			HolderCon[value=enter your height], Inline]]]]\
			""");
	}
	
	@Test public void comp14() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['-5, 5']
			{majorTicks=3 minorTicks=1 inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=height, prompt=Height:, minVal=-5, \
			maxVal=5, defVal=-5, \
			constraints=[MajorTicksCon[value=3], \
			MinorTicksCon[value=1], Inline]]]]\
			""");
	}
	
	@Test public void comp15() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['0, 5']('1')
			{majorTicks=3 minorTicks=1 inline}
			""","""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Slider [name=height, prompt=Height:, \
			minVal=0, maxVal=5, defVal=1, \
			constraints=[MajorTicksCon[value=3], \
			MinorTicksCon[value=1], Inline]]]]\
			""");
	}
	
	@Test public void comp16() {
		TestHelper.numberException("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['0.1, 5']('1')
			{majorTicks=3 minorTicks=1 inline}
			""",
			"Invalid Slider default values");
	}
	
	@Test public void comp17() {
		TestHelper.checkParseError("""
			'Single Dialog' Single('single my description')
			height 'Height:' Slider['1, 5'] ('1')
			{majorTicks=3 minorTicks=1.3 inline}
			""");
	}
	
	@Test public void comp18() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' SingleOpt['Red|Blue|Green']('Red')
			{inlineList}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[SingleOpt [name=height, prompt=Height:, options=[Red, Blue, Green], \
			defValue=Red, constraints=[InlineList]]]]\
			""");
	}
	
	@Test public void comp19() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' MultiOpt['Red|Blue|Green']('Red|Blue')
			{ inlineList}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[MultiOpt [name=height, prompt=Height:, \
			options=[Red, Blue, Green], defValues=[Red, Blue], constraints=[InlineList]]]]\
			""");
	}
	
	@Test public void comp20() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' SingleOpt['Red|Blue|Green']('Red')
			{inlineList}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[SingleOpt [name=height, prompt=Height:, options=[Red, Blue, Green], \
			defValue=Red, constraints=[InlineList]]]]\
			""");
	}
	
	@Test public void comp21() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			height 'Height:' SingleOpt['Red|Blue|Green']('Green|Blue')
			{inlineList}
			""",
			"'Green|Blue' is not a valid option");
	}
	
	
	@Test public void eascape1() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single \\') my description')
			name 'Name:' String('John') {min=1 max=6 inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single ') my description, constraints=[]], \
			containers=[String [name=name, prompt=Name:, defVal=John, \
			constraints=[MinCon[value=1.0], MaxCon[value=6.0], Inline]]]]\
			""");
	}
	
	@Test public void eascape2() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single \\') my description')
			options 'Options' MultiOpt['yes \\|no | Yes | no ]']('yes \\|no | no ]')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single ') my description, constraints=[]], \
			containers=[MultiOpt [name=options, prompt=Options, \
			options=[yes |no, Yes, no], defValues=[yes |no, no ]], constraints=[]]]]\
			""");
	}
	
	@Test public void eascape3() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single \\\\[ []{}()"\n\\\'\\| my description')
			options 'Options' MultiOpt['yes \\|no | Yes | no ]']('yes \\|no | no ]')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single \\[ []{}()"
			'| my description, constraints=[]], \
			containers=[MultiOpt [name=options, prompt=Options, \
			options=[yes |no, Yes, no], defValues=[yes |no, no ]], constraints=[]]]]\
			""");
	}
	
	@Test public void eascape4() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single \\\\[ []{}()"\n\\\'\\| my description')
			options 'Options' MultiOpt['(yes) \\|no | {\\\'no\\\'}']('(yes) \\|no | {\\\'no\\\'}')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single \\[ []{}()"
			'| my description, constraints=[]], \
			containers=[MultiOpt [name=options, prompt=Options, \
			options=[(yes) |no, {'no'}], defValues=[(yes) |no, {'no'}], constraints=[]]]]\
			""");
	}
	
	@Test public void eascape5() {
		TestHelper.checkAst("""
			'User Details' Single('Provide your detail')
			name 'Name' Decimal{regex='[0-9]*\\\\.[0-9]{2}'}
			""",
			"""
			Query[dialog=Single[title=User Details, \
			description=Provide your detail, constraints=[]], \
			containers=[Decimal [name=name, prompt=Name, defVal=null, \
			constraints=[RegexCon[value=[0-9]*\\.[0-9]{2}]]]]]\
			""");
	}
	
	
	
	// if you write either \\\' or \\' they are interpreted as \'
	// if you write either \' or ' they are interpreted as '
	// 
	
	
	
}
