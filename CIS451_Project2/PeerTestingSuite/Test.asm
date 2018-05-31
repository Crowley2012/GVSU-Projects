.data
val1: 0x4

.text
TOP:
	li $5, 0x5
	addi $16, $16, 3     
	addi $17, $16, 5     
	add  $18, $16, $17   
	slt $19, $17, $18    
	slt $20, $18, $17    
	lui $21, 0xFFFF
	sltu $22, $21, $18
	slt $23, $21, $18
	jal LOOP
	
LOOP:
	subi $5, $5, 1
	beq $5, 0, End
	sw $ra, val1
	lw $24, val1
	jr $24
End:
	