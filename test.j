globals
	integer a = 4
	real b = 2. + 5.
	integer array peter[50][10]
endglobals


function main takes nothing returns nothing
	local integer x = y + 4
	local real i = i + 2
	local real y = 4
	local integer i1 = 8
	local integer i2 = 5
	set y = ((y + 1)*3)
	set y = (4+(3*5)+10-(4+7))+(5*4+4)+1-5*5/6+7
	set peter[8][5] = 7 + R2I(y)
endfunction



function foo takes nothing returns nothing
	local integer i = 0
	loop 
		set i = i + 1
		exitwhen i > 5
	endloop
	
	if i > 5 then
		call DoNothing()
	else
		call DoNothing()
	endif
	
endfunction

	

library lib1 requires lib2
globals
	integer lib1a = lib2a + 1 
	real a = bj_PI
	constant integer t
endglobals
endlibrary	
	
library lib2 
globals
	integer lib2a = lib1a + 1 
endglobals
endlibrary
	

library test initializer funcA requires xecast, xepreload



	function funcA takes nothing returns nothing
		call funcB()
		call funcC()
	endfunction

	function funcB takes nothing returns nothing
		call funcB()
	endfunction

	function funcC takes nothing returns nothing
		call funcD()
		call funcB()
	endfunction

	function funcD takes nothing returns nothing
		call DoNothing()
	endfunction


endlibrary

