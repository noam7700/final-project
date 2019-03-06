def rotate_left ( word , n , word_size =64):
	mask = 2** word_size - 1
	return (( word << n ) & mask ) | (( word >> ( word_size - n ) & mask ))
def L ( word ):
	return ( rotate_left ( word , 15) ^ rotate_left ( word , 32) ^ word )
def apply_sbox ( word , sbox ):
	# apply the sbox to every nibble
	word_new = 0
	for i in range (16): # 16 nibbles
	nibble = ( word >> ( i *4)) & 0 xF # retrieve the ith nibble
	# insert the permuted nibble in the correct position
	word_new |= sbox [ nibble ] << i *4
	return word_new
def round_function ( word , key ):
	# we first define the S-box , now sbox [0] = 2 , sbox [1] = 4 , etc.
	sbox = [0 x2 , 0 x4 , 0 x5 , 0 x6 , 0 x1 , 0 xA , 0 xF , 0 x3 ,
	0 xB , 0 xE , 0 x0 , 0 x7 , 0 x9 , 0 x8 , 0 xC , 0 xD ]
	# xor the key into the state
	word ^= key
	# apply the sbox to every nibble of the word
	word = apply_sbox ( word , sbox )
	# apply the linear layer to the state
	word = L ( word )
	# return the new word and the key for the next round
	return word , L ( key )^0 x3
def encrypt ( word , key , rounds =20):
	# Apply the round function <rounds > times
	for r in range ( rounds ):
	word , key = round_function ( word , key )
	return word
	
def main():
	k_limit = 1<<20
	key = 0
	P =
	C =
	while key<k_limit && encrypt(P,key)!=C:
		key++
	if encrypt(P,key) != C:
		print("key not found.")
	else print("key = " + key)
		