############################################################################
# 
# A simple (and incomplete) demonstration of one you might test basic
# I-type and R-type instructions.
#
# When constructing a complete test suite, be sure to
# 
# (1) Test all operations
# (2) Test operations with several different inputs. 
# (3) Choose inputs carefully.  For example, suppose you switched
#     control wires so that addi instructions actually executed as ori
#     instructions.  The line addi "$3, $3, 0", will work correctly in
#     spite of the mistake.
#
###########################################################################

#Phase 1
#instructions: add, lui, or, add, beq, j, lw, sw, and halt
addi $s0, $s0, 3     	# $16 = 3
addi $s1, $s0, 5     	# $17 = 8
add  $s2, $s1, $s1   	# $18 = 16

loop:
addi $s0, $s0, -1	# decrement $s0 3 times
add $s4, $s4, -4  	# $s4 = $s4 - 4
lui  $s5, 0xFFFF
beq  $s0, $0, end
j loop

end:
ori $s6, $s5, 8
ori $s7, $s0, 16







