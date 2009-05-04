globals
integer a = 4// mainscope
real b = 7.0// mainscope
integer array peter// mainscope
integer lib2a = (lib1a+1)// lib2
integer lib1a = (lib2a+1)// lib1
real a = 3.141590118408203// lib1
integer t// lib1
endglobals
function funcB takes nothing returns nothing
call funcB()
endfunction
function funcD takes nothing returns nothing
call DoNothing()
endfunction
function funcC takes nothing returns nothing
call funcD()
call funcB()
endfunction
function funcA takes nothing returns nothing
call funcB()
call funcC()
endfunction
function main takes nothing returns nothing
local integer x = (y+4)
local real i = (i+2)
local real y = 4
local integer i1 = 8
local integer i2 = 5
set y = ((y+1)*3)
set y = 46
set peter[258] = (7+R2I(y))
call ExecuteFunc("funcA")
endfunction
