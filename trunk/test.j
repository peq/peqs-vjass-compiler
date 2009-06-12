struct Point

	
	real x
	real y
	real z


	method foo takes real x returns real
		return x + 4
	endmethod
	
	
	
endstruct

function main takes nothing returns nothing
	 local Point p = null
	 set p.x = 4
	 set p.y = 3
endfunction


